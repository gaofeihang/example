package net.beamlight.netty5.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.Protocol;
import net.beamlight.remoting.handler.RemotingHandler;
import net.beamlight.remoting.stat.RemotingStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamClientHandler extends ChannelHandlerAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(BeamClientHandler.class);
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        RemotingStats.recordRead();
        BeamPacket packet = (BeamPacket) msg;
        
        if (Protocol.PACKET_RESPONSE == packet.getType()) {
            RemotingHandler.handleResponse(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("handler exception! ", cause);
        ctx.close();
    }
    
}
