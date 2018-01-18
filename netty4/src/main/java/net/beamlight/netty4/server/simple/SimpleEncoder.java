package net.beamlight.netty4.server.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleEncoder extends MessageToByteEncoder<byte[]> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
        
        byte[] data = (byte[]) msg;
        out.writeInt(data.length);
        out.writeBytes(data);
    }
    
}
