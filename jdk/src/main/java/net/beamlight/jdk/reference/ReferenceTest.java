package net.beamlight.jdk.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import net.beamlight.commons.util.ThreadUtils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Sep 2, 2015
 * @author gaofeihang
 */
public class ReferenceTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReferenceTest.class);

    class TestBean {
        String name;

        @Override
        protected void finalize() throws Throwable {
            LOG.info("finalize() is called");
        }
    }

    @Test
    public void testSoftReference() {
        TestBean bean = new TestBean();
        SoftReference<TestBean> ref = new SoftReference<TestBean>(bean);
        bean = ref.get();
        
        LOG.info("ref.get() returns: {}", bean);
        
        bean = null;
        forceGC();
        
        ThreadUtils.sleep(5000);
    }
    
    @Test
    public void testWeakReference() {
        TestBean bean = new TestBean();
        WeakReference<TestBean> ref = new WeakReference<TestBean>(bean);
        bean = ref.get();

        LOG.info("ref.get() returns: {}", bean);
        
        bean = null;
        forceGC();

        ThreadUtils.sleep(5000);
    }

    @Test
    public void testWeakReferenceWithQueue() {
        TestBean bean = new TestBean();
        ReferenceQueue<TestBean> queue = new ReferenceQueue<TestBean>();

        WeakReference<TestBean> ref = new WeakReference<TestBean>(bean, queue);
        bean = ref.get();

        LOG.info("ref.get() returns: {}", bean);
        
        bean = null;
        forceGC(queue);
    }
    
    @Test
    public void testWeakReferenceWithoutForceGC() {
        TestBean bean = new TestBean();
        ReferenceQueue<TestBean> queue = new ReferenceQueue<TestBean>();

        WeakReference<TestBean> ref = new WeakReference<TestBean>(bean, queue);
        bean = ref.get();

        LOG.info("ref.get() returns: {}", bean);
        
        int i = 0;
        while (true) {
            if (ref.get() != null) {
                i++;
                LOG.info("Object is alive for {} loops - {}", i, ref);
            } else {
                LOG.info("Object has bean collected.");
                @SuppressWarnings("unchecked")
                Reference<TestBean> ref2 = (Reference<TestBean>) queue.poll();
                LOG.info("ref from queue: {}", ref2);
                if (ref2 != null) {
                    break;
                }
            }
        }
    }

    @Test
    public void testPhantomReference() {
        TestBean bean = new TestBean();
        ReferenceQueue<TestBean> queue = new ReferenceQueue<TestBean>();

        PhantomReference<TestBean> ref = new PhantomReference<TestBean>(bean, queue);
        bean = ref.get();
        
        LOG.info("ref.get() returns: {}", bean);
        
        bean = null;
        forceGC(queue);
    }
    
    private void forceGC() {
        forceGC(null);
    }

    @SuppressWarnings("unchecked")
    private void forceGC(ReferenceQueue<TestBean> queue) {
        int size = 1024 * 1024 * 512;
        int round = 10;
        for (int i = 0; i < round; i++) {
            LOG.info("force gc allocate {}", size);
            @SuppressWarnings("unused")
            byte[] bytes = new byte[size];
            
            if (queue != null) {
                Reference<TestBean> ref = (Reference<TestBean>) queue.poll();
                LOG.info("ref from queue: {}", ref);
                if (ref != null) {
                    LOG.info("object from ref: {}", ref.get());
                    
                    ThreadUtils.sleep(1000 * 30);
                    System.exit(0);
                }
            }
            
            ThreadUtils.sleep(1000);
        }
        LOG.info("after force gc: {} * {}", size, round);
    }

}
