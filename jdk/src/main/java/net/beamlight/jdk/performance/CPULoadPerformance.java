package net.beamlight.jdk.performance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on Mar 12, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class CPULoadPerformance {
    
    private int threadNum = 16;
    private ExecutorService service;
    
    public CPULoadPerformance() {
        service = Executors.newCachedThreadPool();
    }
    
    public void start() {
        for (int i = 0; i < threadNum; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    infiniteLoop();
                }
            });
        }
    }
    
    private void infiniteLoop() {
        long value = 0;
        while (true) {
            value = value + 1;
        }
    }
    
    public static void main(String[] args) {
        new CPULoadPerformance().start();
    }

}
