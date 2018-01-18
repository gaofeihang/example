package net.beamlight.mina.server;

import java.net.InetSocketAddress;

import net.beamlight.mina.codec.BeamPacketCodecFactory;
import net.beamlight.remoting.template.AbstractBeamServer;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleBeamServer extends AbstractBeamServer {
    
    public SimpleBeamServer(int port) {
        super(port);
    }

    public void start() {
        
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new BeamPacketCodecFactory()));
        acceptor.setHandler(new BeamServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(1024 * 1024);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        
        try {
            acceptor.bind(new InetSocketAddress(port));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOG.warn("Mina beam server started! ");
    }
    
    @Override
    public void stop() {
    }

}
