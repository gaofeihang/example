package net.beamlight.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.util.SafeEncoder;

import java.util.Collections;
import java.util.List;

/**
 * Create on Nov 29, 2016
 * @author gaofeihang
 */
public class ZkClient {

    private static final Logger LOG = LoggerFactory.getLogger(ZkClient.class);
    
    private CuratorFramework client;
    
    public ZkClient(String connStr) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(5000)
                .connectString(connStr)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }
    
    public List<String> list(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (Exception e) {
            LOG.error("get children error: {}", path, e);
        }
        return Collections.emptyList();
    }
    
    public void create(String path, String data) {
        try {
            client.create().creatingParentsIfNeeded().forPath(path, SafeEncoder.encode(data));
        } catch (Exception e) {
            LOG.error("create node error: {}", path, e);
        }
    }

    public String createSequential(String path, String data) {
        try {
            return client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, SafeEncoder.encode(data));
        } catch (Exception e) {
            LOG.error("create node error: {}", path, e);
        }
        return "";
    }
    
    public void delete(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            LOG.error("delete node error: {}", path, e);
        }
    }
    
    public String get(String path) {
        try {
            return SafeEncoder.encode(client.getData().forPath(path));
        } catch (Exception e) {
            LOG.error("get data error: {}", path, e);
        }
        return null;
    }
    
    public void set(String path, String data) {
        try {
            client.setData().forPath(path, SafeEncoder.encode(data));
        } catch (Exception e) {
            LOG.error("set data error: {}", path, e);
        }
    }

}
