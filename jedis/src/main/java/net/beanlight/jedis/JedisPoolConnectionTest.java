package net.beanlight.jedis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.beamlight.commons.util.JsonUtils;
import net.beamlight.commons.util.ThreadUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * Created on Aug 11, 2016
 * @author gaofeihang
 */
public class JedisPoolConnectionTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(JedisPoolConnectionTest.class);
    
    @Test
    public void testGet() {
        JedisPoolConfig CONFIG_MEDIUM = new JedisPoolConfig();
        LOG.info(JsonUtils.prettyPrint(CONFIG_MEDIUM));
        
        JedisPool jedisPool = new JedisPool(CONFIG_MEDIUM, "localhost", 6300);
        while (true) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                String val = jedis.get("test-key");
                LOG.info("get val: {}, {}", jedis, val);
            } catch (Exception e) {
                LOG.error("jedis get error: {}", jedis);
            } finally {
                if (jedis != null) {
                    jedisPool.returnResource(jedis);
                }
            }
            ThreadUtils.sleep(1000);
        }
    }
    
    @Test
    public void testSubscribe() {
        JedisPoolConfig CONFIG_MEDIUM = new JedisPoolConfig();
        LOG.info(JsonUtils.prettyPrint(CONFIG_MEDIUM));
        
        JedisPool jedisPool = new JedisPool(CONFIG_MEDIUM, "localhost", 6379);
        while (true) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
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
                if (jedis != null) {
                    jedisPool.returnResource(jedis);
                }
            }
            ThreadUtils.sleep(1000);
        }
    }

}
