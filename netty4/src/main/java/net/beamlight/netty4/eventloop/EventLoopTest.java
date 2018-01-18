package net.beamlight.netty4.eventloop;

import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import net.beamlight.commons.util.NamedThreadFactory;
import net.beamlight.commons.util.ThreadUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gaofeihang on 2017/12/15.
 */
public class EventLoopTest {

    private static final Logger LOG = LoggerFactory.getLogger(EventLoopTest.class);

    @Test
    public void testEventExecutor() {
        EventExecutor eventExecutor = new SingleThreadEventExecutor(null, new NamedThreadFactory("simple-loop"), false) {
            @Override
            protected void run() {
                LOG.info("running event executor");
            }
        };
        eventExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LOG.info("running execute");
            }
        });
        ThreadUtils.sleep(1000 * 10);
    }

    @Test
    public void testEventLoop() {
        EventLoop eventLoop = new SingleThreadEventLoop(null, new NamedThreadFactory("simple-loop"), false) {
            @Override
            protected void run() {
                confirmShutdown();
                LOG.info("running event loop");
            }
        };
        eventLoop.execute(new Runnable() {
            @Override
            public void run() {
                LOG.info("running execute");
            }
        });
        ThreadUtils.sleep(1000 * 10);
    }

}
