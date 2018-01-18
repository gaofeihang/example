package net.beamlight.mina.client;

import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.Protocol;
import net.beamlight.remoting.handler.RemotingHandler;
import net.beamlight.remoting.stat.RemotingStats;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class BeamClientHandler extends IoHandlerAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(BeamClientHandler.class);
    
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        
        RemotingStats.recordRead();
        BeamPacket packet = (BeamPacket) message;
        
        if (Protocol.PACKET_RESPONSE == packet.getType()) {
            RemotingHandler.handleResponse(packet);
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("handler exception: ", cause);
    }
    
}
