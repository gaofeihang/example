package net.beamlight.mina.codec;

import net.beamlight.remoting.BeamPacket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created on Feb 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamPacketDecoder extends CumulativeProtocolDecoder {
    
    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        
        if (in.remaining() < BeamPacket.HEADER_LENGTH) {
            return false;
        }
        
        int start = in.position();
        
        in.getShort();
        long id = in.getLong();
        byte cmd = in.get();
        byte codec = in.get();
        int length = in.getInt();
        
        if (in.remaining() < length) {
            in.position(start);
            return false;
        }
        
        byte[] data = new byte[length];
        in.get(data);
        
        BeamPacket packet = new BeamPacket(id, cmd, codec, data);
        out.write(packet);
        
        return true;
    }

}
