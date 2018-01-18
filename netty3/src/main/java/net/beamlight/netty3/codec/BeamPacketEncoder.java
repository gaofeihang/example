package net.beamlight.netty3.codec;

import net.beamlight.remoting.BeamPacket;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created on Feb 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamPacketEncoder extends OneToOneEncoder {
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        
        BeamPacket packet = (BeamPacket) msg;
        ChannelBuffer buffer = ChannelBuffers.buffer(packet.getPacketLength());
        
        buffer.writeBytes(packet.toByteArray());
        
        return buffer;
    }
    
}
