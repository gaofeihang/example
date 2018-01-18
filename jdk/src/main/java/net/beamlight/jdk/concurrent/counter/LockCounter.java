package net.beamlight.jdk.concurrent.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on Mar 4, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class LockCounter {
    
    private long count;
    private Lock lock = new ReentrantLock();
    
    public void inc() {
        lock.lock();
        count++;
        lock.unlock();
    }
    
    public long getCount() {
        return count;
    }
    
    public long getLockedCount() {
        lock.lock();
        long current = count;
        lock.unlock();
        return current;
    }

}
