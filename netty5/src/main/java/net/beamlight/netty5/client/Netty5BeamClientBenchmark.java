package net.beamlight.netty5.client;

import net.beamlight.remoting.BeamClient;
import net.beamlight.remoting.benchmark.BeamClientBenchmark;

/**
 * Created on Jan 5, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class Netty5BeamClientBenchmark {
    
    public static void main(String[] args) {
        int clientNum = 2;
        BeamClient[] clients = new BeamClient[clientNum];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new SimpleBeamClient("127.0.0.1", 8080);
        }
        new BeamClientBenchmark(clients, 10).start();
    }

}
