package net.beamlight.netty3.simple;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.beamlight.commons.util.ThreadUtils;
import net.beamlight.remoting.stat.RemotingStats;

/**
 * Created on Apr 20, 2015
 *
 * @author gaofeihang
 * @since 1.0.0
 */
public class Netty3SimpleBenchmark {
    
    private final byte[] DATA = "hello".getBytes();
    
    private int threadNum;
    private int batchSize;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    
    public Netty3SimpleBenchmark(int threadNum, int batchSize) {
        this.threadNum = threadNum;
        this.batchSize = batchSize;
    }
    
    public void start() {
        
        for (int i = 0; i < threadNum; i++) {
            final SimpleClient client = new SimpleClient("localhost", 8080);
            client.open();
            
            executorService.execute(new Runnable() {
                
                @Override
                public void run() {
                    while (true) {
                        for (int j = 0; j < batchSize; j++) {
                            client.write(DATA);
                        }
                        ThreadUtils.sleep(1);
                    }
                }
            });
        }
    }
    
    public static void main(String[] args) {
        new Netty3SimpleBenchmark(1, 500).start();
        RemotingStats.start();
    }

}
