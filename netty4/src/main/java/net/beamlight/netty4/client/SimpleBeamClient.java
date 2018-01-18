package net.beamlight.netty4.client;

import io.netty.bootstrap.Bootstrap;
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

import net.beamlight.netty4.codec.BeamPacketDecoder;
import net.beamlight.netty4.codec.BeamPacketEncoder;
import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.exception.RemotingException;
import net.beamlight.remoting.stat.RemotingStats;
import net.beamlight.remoting.template.AbstractBeamClient;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleBeamClient extends AbstractBeamClient {
    
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    
    public SimpleBeamClient(String host, int port) {
        super(host, port);
    }

    @Override
    public void open() throws RemotingException {
        workerGroup = new NioEventLoopGroup();
        
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
            .handler(new ChannelInitializer<SocketChannel>() {
                
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new BeamPacketEncoder());
                ch.pipeline().addLast(new BeamPacketDecoder());
                ch.pipeline().addLast(new BeamClientHandler());
            }
        });

        try {
            channelFuture = bootstrap.connect(new InetSocketAddress(host, port)).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOG.warn("Netty 4 beam client started!");
    }
    
    @Override
    public void close() {
        workerGroup.shutdownGracefully();
    }

    @Override
    protected void doWrite(BeamPacket packet) {
        Channel channel = channelFuture.channel();
        ChannelFuture future = channel.write(packet);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    RemotingStats.recordWrite();
                }
            }
        });
        channel.flush();
    }

}
