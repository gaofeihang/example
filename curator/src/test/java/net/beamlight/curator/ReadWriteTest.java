package net.beamlight.curator;

import net.beamlight.commons.util.ThreadUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gaofeihang on 17/1/18.
 */
public class ReadWriteTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReadWriteTest.class);

    private static final String testPath = "/zk-test";
    private static ZkClient zkClient = new ZkClient("local_host:2181");

    @Test
    public void testDelete() {
        zkClient.delete(testPath);
    }

    @Test
    public void testList() {
        LOG.info("ls: {}", zkClient.list(testPath));
    }

    @Test
    public void testReadWrite() {
        while (true) {
            long time = System.currentTimeMillis();
            zkClient.create(testPath + "/" + time, "");
            LOG.info("ls: {}", zkClient.list(testPath).size());
            ThreadUtils.sleep(1000);
        }
    }

}
