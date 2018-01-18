package net.beamlight.netty5.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import net.beamlight.remoting.BeamPacket;

/**
 * Created on Feb 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamPacketEncoder extends MessageToMessageEncoder<BeamPacket> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, BeamPacket msg, List<Object> out) throws Exception {
        
        BeamPacket packet = (BeamPacket) msg;
        
        ByteBuf buf = ctx.alloc().buffer(packet.getPacketLength());
        buf.writeBytes(packet.toByteArray());
        out.add(buf);
    }

}
