package net.beamlight.curator;

import net.beamlight.commons.util.ThreadUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkClientTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(ZkClientTest.class);
    
    private ZkClient client = new ZkClient("local_zk:2181");
    
    @Test
    public void testList() {
        LOG.info("{}", client.list("/"));
    }

    @Test
    public void testCreate() {
        client.create("/zk_test/seq", "");
    }

    @Test
    public void testCreateSequential() {
        String path = client.createSequential("/zk_test/seq/node_", "");
        String seqStr = StringUtils.substringAfterLast(path, "_");
        long seq = NumberUtils.toLong(seqStr);
        LOG.info("{}", seq);
        ThreadUtils.sleep(5000);
    }

    @Test
    public void testMultiCreateSequential() {
        for (int i = 0; i < 5; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        LOG.info("{}", client.createSequential("/zk_test/seq/node_", ""));
                        ThreadUtils.sleep(RandomUtils.nextInt(0, 5000));
                    }
                }
            }).start();
        }
        ThreadUtils.sleep(1000 * 60);
    }

}
