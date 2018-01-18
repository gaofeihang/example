package net.beamlight.mina.codec;

import net.beamlight.remoting.BeamPacket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Created on Feb 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamPacketEncoder implements ProtocolEncoder {
    
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        
        BeamPacket packet = (BeamPacket) message;
        IoBuffer buffer = IoBuffer.allocate(
                packet.getPacketLength(), false);
        
        buffer.put(packet.toByteArray());
        buffer.flip();
        
        out.write(buffer);
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // TODO Auto-generated method stub
    }
    
}
