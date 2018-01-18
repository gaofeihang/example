package net.beamlight.netty4.server.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.beamlight.remoting.stat.RemotingStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class Netty4SimpleServer {
    
    private static final Logger LOG = LoggerFactory.getLogger(Netty4SimpleServer.class);
    
    private int port;
    private boolean echo;
    
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    
    public Netty4SimpleServer(int port, boolean echo) {
        this.port = port;
        this.echo = echo;
    }

    public void start() {
        
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        
        ByteBufAllocator allocator = new PooledByteBufAllocator();
        
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new SimpleEncoder());
                ch.pipeline().addLast(new SimpleDecoder());
                ch.pipeline().addLast(new SimpleServerHandler(echo));
            }
        })
            .option(ChannelOption.SO_BACKLOG, 128)
            .option(ChannelOption.ALLOCATOR, allocator)
            .childOption(ChannelOption.ALLOCATOR, allocator)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            bootstrap.bind(port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOG.warn("Netty 4 simple server started! ");
    }
    
    public static void main(String[] args) {
        new Netty4SimpleServer(8080, false).start();
        RemotingStats.start();
    }

}
