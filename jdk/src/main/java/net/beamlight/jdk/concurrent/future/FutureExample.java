package net.beamlight.jdk.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created on Feb 3, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class FutureExample {
    
    private ExecutorService executorService = Executors.newCachedThreadPool();
    
    public FutureExample() {
        Future<String> future = executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                int i = 0;
                while (i < 10) {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                return String.valueOf(i);
            }
        });
        
        try {
            String result = future.get(3, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        executorService.shutdownNow();
    }
    
    public static void main(String[] args) {
        new FutureExample();
    }

}
