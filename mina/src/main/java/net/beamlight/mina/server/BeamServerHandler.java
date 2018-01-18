package net.beamlight.mina.server;

import net.beamlight.commons.frame.BeamResponse;
import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.Protocol;
import net.beamlight.remoting.handler.RemotingHandler;
import net.beamlight.remoting.stat.RemotingStats;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
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
public class BeamServerHandler extends IoHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(BeamServerHandler.class);
    
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        
        RemotingStats.recordRead();
        final BeamPacket packet = (BeamPacket) message;
        
        if (Protocol.PACKET_REQUEST == packet.getType()) {
            BeamPacket respPacket = RemotingHandler.buildResponsePacket(packet, new BeamResponse());
            WriteFuture writeFuture = session.write(respPacket);
            writeFuture.addListener(new IoFutureListener<IoFuture>() {
                @Override
                public void operationComplete(IoFuture future) {
                    if (future.isDone()) {
                        RemotingStats.recordWrite();
                    }
                }
            });
        }
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("handler exception: ", cause);
    }

}
