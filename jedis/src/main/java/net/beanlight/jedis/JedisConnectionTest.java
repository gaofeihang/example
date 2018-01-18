package net.beanlight.jedis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.beamlight.commons.util.ThreadUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created on Aug 11, 2016
 * @author gaofeihang
 */
public class JedisConnectionTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(JedisConnectionTest.class);
    
    @Test
    public void testGet() {
        Jedis jedis = new Jedis("localhost", 6379);
        while (true) {
            try {
                String val = jedis.get("test-key");
                LOG.info("get val: {}", val);
            } catch (Exception e) {
                LOG.error("jedis get error", e);
            }
            ThreadUtils.sleep(1000);
        }
    }
    
    @Test
    public void testSubscribe() {
        Jedis jedis = new Jedis("localhost", 6379);
        while (true) {
            try {
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {
                    }
                    
                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        LOG.info("subscribe success");
                    }
                    
                    @Override
                    public void onPUnsubscribe(String pattern, int subscribedChannels) {
                    }
                    
                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {
                    }
                    
                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                    }
                    
                    @Override
                    public void onMessage(String channel, String message) {
                        LOG.info("on message: {}, {}", channel, message);
                    }
                }, "test-channel");
            } catch (Exception e) {
                LOG.error("jedis subscribe error", e);
            }
            ThreadUtils.sleep(1000);
        }
    }

}
