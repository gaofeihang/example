package net.beamlight.netty3.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.beamlight.netty3.codec.BeamPacketDecoder;
import net.beamlight.netty3.codec.BeamPacketEncoder;
import net.beamlight.remoting.template.AbstractBeamServer;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleBeamServer extends AbstractBeamServer {

    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup;
    
    public SimpleBeamServer(int port) {
        super(port);
    }

    public void start() {
        
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool());

        bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                
                ChannelPipeline pipeline = Channels.pipeline();
                
                pipeline.addLast("encoder", new BeamPacketEncoder());
                pipeline.addLast("decoder", new BeamPacketDecoder());
                pipeline.addLast("handler", new BeamServerHandler());
                
                return pipeline;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        Channel serverChannel = bootstrap.bind(new InetSocketAddress(port));
        channelGroup = new DefaultChannelGroup();
        channelGroup.add(serverChannel);
        
        LOG.warn("Netty 3 beam server started! ");
    }
    
    @Override
    public void stop() {
        ChannelGroupFuture channelGroupFuture = channelGroup.close();
        channelGroupFuture.awaitUninterruptibly();
        if (!channelGroupFuture.isCompleteSuccess()) {
            throw new RuntimeException("Close channels failed!");
        }
        bootstrap.releaseExternalResources();
    }

}
