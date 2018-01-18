package net.beamlight.netty3.simple;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.beamlight.remoting.stat.RemotingStats;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleClient {
    
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    private String host;
    private int port;
    
    private ClientBootstrap bootstrap;
    private ChannelFuture channelFuture;
    
    public SimpleClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void open() {
        
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), 
                Executors.newCachedThreadPool());

        bootstrap = new ClientBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                
                ChannelPipeline pipeline = Channels.pipeline();
                
                pipeline.addLast("encoder", new SimpleEncoder());
                pipeline.addLast("decoder", new SimpleDecoder());
                pipeline.addLast("handler", new SimpleClientHandler());
                
                return pipeline;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
        channelFuture.awaitUninterruptibly();
        
        LOG.warn("Netty 3 simple client started!");
    }
    
    public void write(byte[] data) {
        Channel channel = channelFuture.getChannel();
        ChannelFuture future = channel.write(data);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                RemotingStats.recordWrite();
            }
        });
    }
    
    public void close() {
        channelFuture.getChannel().close();
        bootstrap.releaseExternalResources();
    }

}
