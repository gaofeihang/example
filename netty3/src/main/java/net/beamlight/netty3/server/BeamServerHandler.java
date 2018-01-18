package net.beamlight.netty3.server;

import net.beamlight.commons.frame.BeamResponse;
import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.Protocol;
import net.beamlight.remoting.handler.RemotingHandler;
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
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamServerHandler extends SimpleChannelHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(BeamServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        
        RemotingStats.recordRead();
        final BeamPacket packet = (BeamPacket) e.getMessage();
        
        if (Protocol.PACKET_REQUEST == packet.getType()) {
            BeamPacket respPacket = RemotingHandler.buildResponsePacket(packet, new BeamResponse());
            ChannelFuture future = ctx.getChannel().write(respPacket);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        RemotingStats.recordWrite();
                    }
                }
            });
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.error("handler exception: ", e.getCause());
        e.getChannel().close();
    }
}
