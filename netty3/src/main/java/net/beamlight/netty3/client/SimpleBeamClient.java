package net.beamlight.netty3.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.beamlight.netty3.codec.BeamPacketDecoder;
import net.beamlight.netty3.codec.BeamPacketEncoder;
import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.exception.RemotingException;
import net.beamlight.remoting.stat.RemotingStats;
import net.beamlight.remoting.template.AbstractBeamClient;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleBeamClient extends AbstractBeamClient {
    
    private ClientBootstrap bootstrap;
    private ChannelFuture channelFuture;
    
    public SimpleBeamClient(String host, int port) {
        super(host, port);
    }
    
    @Override
    public void open() throws RemotingException {
        
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool());

        bootstrap = new ClientBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                
                ChannelPipeline pipeline = Channels.pipeline();
                
                pipeline.addLast("encoder", new BeamPacketEncoder());
                pipeline.addLast("decoder", new BeamPacketDecoder());
                pipeline.addLast("handler", new BeamClientHandler());
                
                return pipeline;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
        channelFuture.awaitUninterruptibly();
        
        LOG.warn("Netty 3 beam client started!");
    }
    
    @Override
    public void close() {
        channelFuture.getChannel().close();
        bootstrap.releaseExternalResources();
    }
    
    @Override
    protected void doWrite(BeamPacket packet) {
        Channel channel = channelFuture.getChannel();
        ChannelFuture future = channel.write(packet);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                RemotingStats.recordWrite();
            }
        });
    }

}
