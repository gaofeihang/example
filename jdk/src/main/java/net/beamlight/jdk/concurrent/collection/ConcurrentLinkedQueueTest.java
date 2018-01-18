package net.beamlight.jdk.concurrent.collection;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Aug 9, 2016
 * @author gaofeihang
 */
public class ConcurrentLinkedQueueTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentLinkedQueueTest.class);
    
    @Test
    public void testOffer() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        queue.offer(String.valueOf(System.currentTimeMillis()));
        String element = queue.poll();
        LOG.info(element);
        element = queue.poll();
        LOG.info(element);
    }

}
