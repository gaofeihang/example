package net.beamlight.netty4.server.simple;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.beamlight.remoting.stat.RemotingStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleServerHandler.class);
    
    private AtomicInteger counter = new AtomicInteger(0);
    private boolean echo;
    
    public SimpleServerHandler(boolean echo) {
        this.echo = echo;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        RemotingStats.recordRead();
        
        if (!echo) {
            return;
        }
        
        ChannelFuture future = ctx.channel().write(msg);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    RemotingStats.recordWrite();
                }
            }
        });
        
        if (counter.compareAndSet(2000, 0)) {
            ctx.flush();
        } else {
            counter.incrementAndGet();
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("handler exception: ", cause);
        ctx.close();
    }
}
