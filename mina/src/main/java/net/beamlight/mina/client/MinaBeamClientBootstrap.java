package net.beamlight.mina.client;

import net.beamlight.remoting.BeamClient;
import net.beamlight.remoting.benchmark.BeamClientBootstrap;

/**
 * Created on Jan 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class MinaBeamClientBootstrap {
    
    public static void main(String[] args) {
        BeamClient client = new SimpleBeamClient("127.0.0.1", 8080);
        new BeamClientBootstrap(client, 10).start();
    }
    
}
