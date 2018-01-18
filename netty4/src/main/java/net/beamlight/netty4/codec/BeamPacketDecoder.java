package net.beamlight.netty4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import net.beamlight.remoting.BeamPacket;

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
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
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
        
        frame.release();
        return new BeamPacket(id, cmd, codec, data);
    }
    
}
