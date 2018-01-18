package net.beamlight.netty3.simple;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

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
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        
        ChannelBuffer frame = (ChannelBuffer) super.decode(ctx, channel, buffer);
        
        if (frame != null) {
            return frame.array();
        } else {
            return null;
        }
        
    }
    
}
