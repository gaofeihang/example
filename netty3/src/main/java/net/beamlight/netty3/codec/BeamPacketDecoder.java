package net.beamlight.netty3.codec;

import net.beamlight.remoting.BeamPacket;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

/**
 * Created on Feb 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamPacketDecoder extends LengthFieldBasedFrameDecoder {
    
    public BeamPacketDecoder() {
        super(Integer.MAX_VALUE, BeamPacket.LENGTH_FIELD_OFFSET, BeamPacket.LENGTH_FIELD_LENGTH);
    }
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        
        ChannelBuffer frame = (ChannelBuffer) super.decode(ctx, channel, buffer);
        if (frame == null) {
            return null;
        }
        
        frame.readShort();
        long id = frame.readLong();
        byte cmd = frame.readByte();
        byte codec = frame.readByte();
        int length = frame.readInt();
        
        byte[] data = new byte[length];
        frame.readBytes(data);
        
        return new BeamPacket(id, cmd, codec, data);
    }
    
}
