package net.beamlight.netty3.simple;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleEncoder extends OneToOneEncoder {
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        
        byte[] data = (byte[]) msg;
        ChannelBuffer buffer = ChannelBuffers.buffer(4 + data.length);
        
        buffer.writeInt(data.length);
        buffer.writeBytes(data);
        
        return buffer;
    }
    
}
