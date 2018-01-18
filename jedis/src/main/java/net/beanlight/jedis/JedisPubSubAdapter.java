package net.beanlight.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

/**
 * Created on Oct 20, 2016 
 * @author gaofeihang
 */
public class JedisPubSubAdapter extends JedisPubSub {
    
    private static final Logger LOG = LoggerFactory.getLogger(JedisPubSubAdapter.class);
    
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        LOG.info("unsubscribe channel: {}, {}", channel, subscribedChannels);
    }
    
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        LOG.info("subscribe channel: {}, {}", channel, subscribedChannels);
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
        LOG.info("message: {}, {}", channel, message);
    }

}
