package net.beamlight.netty4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.beamlight.netty4.codec.BeamPacketDecoder;
import net.beamlight.netty4.codec.BeamPacketEncoder;
import net.beamlight.remoting.template.AbstractBeamServer;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleBeamServer extends AbstractBeamServer {
    
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    
    public SimpleBeamServer(int port) {
        super(port);
    }


    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(4);
        
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new BeamPacketEncoder());
                ch.pipeline().addLast(new BeamPacketDecoder());
                ch.pipeline().addLast(new BeamServerHandler());
            }
        })
            .option(ChannelOption.SO_BACKLOG, 128)
            .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            bootstrap.bind(port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOG.warn("Netty 4 beam server started! ");
    }

    @Override
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
