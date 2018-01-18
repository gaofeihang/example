package net.beamlight.netty3.simple;

import net.beamlight.remoting.stat.RemotingStats;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleServerHandler extends SimpleChannelHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleServerHandler.class);
    
    private boolean echo;
    
    public SimpleServerHandler(boolean echo) {
        this.echo = echo;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        
        RemotingStats.recordRead();
        
        if (!echo) {
            return;
        }
        
        ChannelFuture future = ctx.getChannel().write(e.getMessage());
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    RemotingStats.recordWrite();
                }
            }
        });
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        LOG.error("handler exception: ", e.getCause());
        e.getChannel().close();
    }
}
