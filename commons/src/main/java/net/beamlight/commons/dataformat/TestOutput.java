package net.beamlight.commons.dataformat;

import net.beamlight.commons.util.JsonUtils;
import org.bson.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by gaofeihang on 2017/3/6.
 */
public class TestOutput {

    private static final Logger LOG = LoggerFactory.getLogger(TestOutput.class);

    @Test
    public void testOutput() {
        Document doc = new Document("event-key", "slow_redis");
        doc.append("time", 1488797580);
        doc.append("count", 67);
        doc.append("cluster", "onestore_user_profile_proxy");
        doc.append("proxy", new Document("onestore-proxy-porile-011.m6:8190", 67));
        doc.append("cmd", new Document("mget", 67));
        doc.append("client", new Document("moa-datastore-001~002.m6", 52).append("gc-parser-003.m6", 15));
        doc.append("redis", new Document("redis_cluster_profie_a0_0:6379", 67));

        LOG.info(JsonUtils.prettyPrint(doc));
    }

}
