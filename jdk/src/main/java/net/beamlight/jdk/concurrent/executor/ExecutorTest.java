package net.beamlight.jdk.concurrent.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.beamlight.commons.util.ThreadUtils;

/**
 * Created on July 20, 2016 
 * @author gaofeihang
 */
public class ExecutorTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(ExecutorTest.class);

    @Test
    public void scheduleWithException() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LOG.info("running with exception");
                throw new RuntimeException("scheduled exception");
            }
        }, 0, 1, TimeUnit.SECONDS);
        
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LOG.info("running normal");
            }
        }, 0, 1, TimeUnit.SECONDS);
        
        ThreadUtils.sleep(1000L * 3600);
    }

    @Test
    public void scheduleWithDelay() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                LOG.info("running schedules");
                ThreadUtils.sleep(5000);
                LOG.info("running schedule ends");
            }
        }, 0, 5, TimeUnit.SECONDS);

        ThreadUtils.sleep(1000L * 3600);
    }
    
}
