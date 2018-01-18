package net.beamlight.netty4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.beamlight.remoting.BeamPacket;

/**
 * Created on Mar 12, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamPacketEncoder extends MessageToByteEncoder<BeamPacket> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, BeamPacket msg, ByteBuf out) throws Exception {
        
        BeamPacket packet = (BeamPacket) msg;
        out.writeBytes(packet.toByteArray());
    }

}
