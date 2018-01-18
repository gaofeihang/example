package net.beanlight.jedis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.beamlight.commons.util.ThreadUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created on May 19, 2016
 * @author gaofeihang
 */
public class PubSubTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(PubSubTest.class);
    
    @Test
    public void testPubSub() {
        final Jedis jedis = new Jedis("localhost", 6379);
        
        final JedisPubSub pubSub = new JedisPubSubAdapter();
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(pubSub, "channel-A", "channel-B", "channel-C");
            }
        });
        t.start();
        
        ThreadUtils.sleep(3000);
        pubSub.unsubscribe();
        
        ThreadUtils.sleep(3000);
        pubSub.unsubscribe();
    }
    
    @Test
    public void testUnsubscribe() {
        Jedis jedis = new Jedis("localhost", 6379);
        final JedisPubSub pubSub = new JedisPubSubAdapter();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(pubSub, "channel-A");
            }
        });
        t.start();
        ThreadUtils.sleep(1000);
        pubSub.unsubscribe();
        LOG.info("{}", pubSub.isSubscribed());
        pubSub.unsubscribe();
        ThreadUtils.sleep(1000);
        jedis.rpush("test-list", "test-member");
    }

}
