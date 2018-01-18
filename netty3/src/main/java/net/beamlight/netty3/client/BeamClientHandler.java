package net.beamlight.netty3.client;

import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.Protocol;
import net.beamlight.remoting.handler.RemotingHandler;
import net.beamlight.remoting.stat.RemotingStats;

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
public class BeamClientHandler extends SimpleChannelHandler {
    
    private static Logger logger = LoggerFactory.getLogger(BeamClientHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        
        RemotingStats.recordRead();
        BeamPacket packet = (BeamPacket) e.getMessage();
        
        if (Protocol.PACKET_RESPONSE == packet.getType()) {
            RemotingHandler.handleResponse(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.error("handler exception: ", e.getCause());
        e.getChannel().close();
    }
}
