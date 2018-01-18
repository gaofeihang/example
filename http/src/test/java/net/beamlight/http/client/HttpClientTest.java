package net.beamlight.http.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Nov 23, 2016 
 * @author gaofeihang
 */
public class HttpClientTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientTest.class);
    
    @Test
    public void testHttpGet() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://baidu.com");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int n = 0;
            byte[] b = new byte[1024];
            while ((n = is.read(b)) > 0) {
                if (n <= 0) {
                    break;
                }
                baos.write(b, 0, n);
            }
            LOG.info(baos.toString("UTF-8"));
            EntityUtils.consume(entity);
        } catch (Exception e) {
            LOG.error("http get error", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e2) {
                    LOG.error("close response error", e2);
                }
            }
        }
        LOG.info("OK");
    }

}
