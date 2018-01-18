package net.beamlight.jdk.performance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created on Mar 12, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class ThreadSwitchPerformance {
    
    private int threadNum = 3;
    private long totalLoop = Integer.MAX_VALUE * 20L;
    private long singleLoop = totalLoop / threadNum;
    
    private ExecutorService executorService;
    
    private CountDownLatch latch = new CountDownLatch(threadNum);
    private AtomicLong agregateTime = new AtomicLong();
    
    public ThreadSwitchPerformance() {
        executorService = Executors.newCachedThreadPool();
    }
    
    public void start() {
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    long startTime = System.currentTimeMillis();
                    System.out.println(threadName + " started at " + startTime);
                    
                    finiteLoop();
                    long timeCost = System.currentTimeMillis() - startTime;
                    agregateTime.addAndGet(timeCost);
                    System.out.println(threadName + " Loop done: " + timeCost);
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        long timeCost = System.currentTimeMillis() - startTime;
        long agregateTimeCost = agregateTime.get();
        
        System.out.println("Thread Num: " + threadNum 
                + ", Agregate Time Cost: " + agregateTimeCost
                + ", Average Time Cost: " + agregateTimeCost / threadNum
                + ", Total Time Cost: " + timeCost);
        
        executorService.shutdownNow();
    }
    
    private void finiteLoop() {
        long value = 0;
        while (value < singleLoop) {
            value = value + 1;
        }
    }
    
    public static void main(String[] args) {
        new ThreadSwitchPerformance().start();
    }

}
