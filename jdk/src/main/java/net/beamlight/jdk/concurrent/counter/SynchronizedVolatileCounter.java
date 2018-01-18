package net.beamlight.jdk.concurrent.counter;

/**
 * Created on Mar 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class SynchronizedVolatileCounter {
    
    private volatile long count;
    
    public synchronized void inc() {
        count++;
    }
    
    public long getCount() {
        return count;
    }

}
