package net.beamlight.commons.io;

import net.beamlight.commons.util.ThreadUtils;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by gaofeihang on 17/1/11.
 */
public class TailerTest {

    private static final Logger LOG = LoggerFactory.getLogger(TailerTest.class);

    @Test
    public void testTailer() {
        String path = System.getProperty("user.home") + "/test-tail-file";
        LOG.info(path);
        Tailer tailer = new Tailer(new File(path), new TailerListener() {
            @Override
            public void init(Tailer tailer) {
                LOG.info("init");
            }

            @Override
            public void fileNotFound() {
                LOG.info("file not found");
            }

            @Override
            public void fileRotated() {
                LOG.info("rotated");
            }

            @Override
            public void handle(String line) {
                LOG.info(line);
            }

            @Override
            public void handle(Exception ex) {
                LOG.error("error", ex);
            }
        });
        new Thread(tailer).start();
        ThreadUtils.sleep(60 * 1000);
    }

}
