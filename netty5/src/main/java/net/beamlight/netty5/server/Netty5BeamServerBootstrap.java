package net.beamlight.netty5.server;

import net.beamlight.remoting.stat.RemotingStats;

/**
 * Created on Jan 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class Netty5BeamServerBootstrap {
    
    public static void main(String[] args) {
        new SimpleBeamServer(8080).start();
        RemotingStats.start();
    }
}
