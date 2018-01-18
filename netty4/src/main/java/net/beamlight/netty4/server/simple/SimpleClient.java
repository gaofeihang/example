package net.beamlight.netty4.server.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import net.beamlight.remoting.stat.RemotingStats;

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
    
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    
    private AtomicInteger counter = new AtomicInteger(0);
    
    public SimpleClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void open() {
        
        workerGroup = new NioEventLoopGroup();
        
        ByteBufAllocator allocator = new PooledByteBufAllocator();
        
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.ALLOCATOR, allocator)
            .handler(new ChannelInitializer<SocketChannel>() {
                
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new SimpleEncoder());
                ch.pipeline().addLast(new SimpleDecoder());
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });

        try {
            channelFuture = bootstrap.connect(new InetSocketAddress(host, port)).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOG.warn("Netty 4 simple client started!");
    }
    
    public void write(byte[] data) {
        Channel channel = channelFuture.channel();
        ChannelFuture future = channel.write(data);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                RemotingStats.recordWrite();
            }
        });
        
        if (counter.compareAndSet(2000, 0)) {
            channel.flush();
        } else {
            counter.incrementAndGet();
        }
    }
    
    public void close() {
        workerGroup.shutdownGracefully();
    }

}
