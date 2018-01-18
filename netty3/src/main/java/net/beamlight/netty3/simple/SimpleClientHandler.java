package net.beamlight.netty3.simple;

import net.beamlight.remoting.stat.RemotingStats;

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
public class SimpleClientHandler extends SimpleChannelHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleClientHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        
        RemotingStats.recordRead();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        LOG.error("handler exception: ", e.getCause());
        e.getChannel().close();
    }
}
