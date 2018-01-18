package net.beamlight.netty4.server.simple;

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
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        RemotingStats.recordRead();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("handler exception: ", cause);
        ctx.close();
    }
}
