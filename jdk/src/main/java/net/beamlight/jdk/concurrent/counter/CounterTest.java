package net.beamlight.jdk.concurrent.counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on Mar 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class CounterTest {
    
    private int threadNum = 500;
    private int loopNum = 100000;
    private long counterMax = loopNum * threadNum;
    
    private CountDownLatch latch = new CountDownLatch(threadNum);
    private SynchronizedVolatileCounter counter = new SynchronizedVolatileCounter();
    
    public CounterTest() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(new Runnable() {
                
                @Override
                public void run() {
                    for (int j = 0; j < loopNum; j++) {
                        counter.inc();
                    }
                    latch.countDown();
                }
            });
        }
        
        executorService.execute(new Runnable() {
            
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                int i = 0;
                while (true) {
                    // count value is not always visible for this thread
                    long count = counter.getCount();
                    
                    if (System.currentTimeMillis() - start > 5000 
                            || count == counterMax) {
                        
                        System.out.println(System.currentTimeMillis() + " Another Thread i=" + i + ", count=" + count);
                        if (count == counterMax) {
                            break;
                        }
                    }
                    i++;
                }
            }
        });
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + " Counter Finished " + counter.getCount());
        executorService.shutdownNow();
    }
    
    public static void main(String[] args) {
        new CounterTest();
    }

}
