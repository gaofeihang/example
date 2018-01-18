package net.beamlight.netty4.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.beamlight.commons.frame.BeamResponse;
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
public class BeamServerHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(BeamServerHandler.class);
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        
        RemotingStats.recordRead();
        final BeamPacket packet = (BeamPacket) msg;
        
        if (Protocol.PACKET_REQUEST == packet.getType()) {
            BeamPacket respPacket = RemotingHandler.buildResponsePacket(packet, new BeamResponse());
            ChannelFuture future = ctx.write(respPacket);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        RemotingStats.recordWrite();
                    }
                }
            });
            ctx.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("handler exception! ", cause);
        ctx.close();
    }
    
}
