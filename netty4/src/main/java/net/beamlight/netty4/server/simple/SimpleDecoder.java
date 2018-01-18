package net.beamlight.netty4.server.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleDecoder extends LengthFieldBasedFrameDecoder {
    
    public SimpleDecoder() {
        super(Integer.MAX_VALUE, 0, 4);
    }
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame != null) {
            try {
                return frame.array();
            } finally {
                frame.release();
            }
        } else {
            return null;
        }
    }
    
}
