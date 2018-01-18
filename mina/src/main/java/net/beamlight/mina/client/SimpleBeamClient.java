package net.beamlight.mina.client;

import java.net.InetSocketAddress;

import net.beamlight.mina.codec.BeamPacketCodecFactory;
import net.beamlight.remoting.BeamPacket;
import net.beamlight.remoting.exception.RemotingException;
import net.beamlight.remoting.stat.RemotingStats;
import net.beamlight.remoting.template.AbstractBeamClient;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * Created on Jan 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SimpleBeamClient extends AbstractBeamClient {
    
    private NioSocketConnector connector;
    private IoSession session;
    
    public SimpleBeamClient(String host, int port) {
        super(host, port);
    }

    @Override
    public void open() throws RemotingException {
        
        connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new BeamPacketCodecFactory()));
        connector.setHandler(new BeamClientHandler());
        
        try {
            ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
            future.awaitUninterruptibly();
            session = future.getSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LOG.warn("Mina beam client started!");
    }
    
    @Override
    public void close() {
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
    
    @Override
    protected void doWrite(BeamPacket packet) {
        WriteFuture writeFuture = session.write(packet);
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
