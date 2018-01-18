package net.beamlight.netty3.simple;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.beamlight.remoting.stat.RemotingStats;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class Netty3SimpleServer {
    
    private static final Logger LOG = LoggerFactory.getLogger(Netty3SimpleServer.class);
    
    private int port;
    private boolean echo;
    
    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup;
    
    public Netty3SimpleServer(int port, boolean echo) {
        this.port = port;
        this.echo = echo;
    }

    public void start() {
        
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool());

        bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                
                ChannelPipeline pipeline = Channels.pipeline();
                
                pipeline.addLast("encoder", new SimpleEncoder());
                pipeline.addLast("decoder", new SimpleDecoder());
                pipeline.addLast("handler", new SimpleServerHandler(echo));
                
                return pipeline;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        Channel serverChannel = bootstrap.bind(new InetSocketAddress(port));
        channelGroup = new DefaultChannelGroup();
        channelGroup.add(serverChannel);
        
        LOG.warn("Netty 3 simple server started! ");
    }
    
    public static void main(String[] args) {
        new Netty3SimpleServer(8080, false).start();
        RemotingStats.start();
    }

}
