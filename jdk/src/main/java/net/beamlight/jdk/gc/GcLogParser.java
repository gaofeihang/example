package net.beamlight.jdk.gc;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Sep 13, 2016 
 * @author gaofeihang
 */
public class GcLogParser {
    
    private static final Logger LOG = LoggerFactory.getLogger(GcLogParser.class);
    
    private String path;
    
    public GcLogParser(String path) {
        this.path = path;
    }
    
    public void start() {
        List<String> lines = null;
        try {
            lines = IOUtils.readLines(new FileInputStream(path), Charset.defaultCharset());
        } catch (Exception e) {
            LOG.error("read file error: {}", path, e);
        }
        
        if (lines == null) {
            return;
        }
        
        AtomicLong sumCounter = new AtomicLong();
        Map<String, AtomicLong> counters = new TreeMap<String, AtomicLong>();
        
        for (String line : lines) {
            if (!line.contains("ParNew")) {
                continue;
            }
            
            String real = StringUtils.substringAfterLast(line, "real=");
            real = StringUtils.substringBefore(real, " ");
            
            AtomicLong counter = counters.get(real);
            if (counter == null) {
                counter = new AtomicLong();
                counters.put(real, counter);
            }
            counter.incrementAndGet();
            sumCounter.incrementAndGet();
        }
        
        StringBuilder sb = new StringBuilder();
        long sum = sumCounter.get();
        for (Entry<String, AtomicLong> entry : counters.entrySet()) {
            String real = entry.getKey();
            AtomicLong counter = entry.getValue();
            long count = counter.get();
            long ratio = count * 100 / sum;
            
            sb.append(real).append("\t").append(count).append("\t").append(ratio).append("%\n");
        }
        
        LOG.info("\n{}", sb.toString());
    }
    
    public static void main(String[] args) {
        new GcLogParser("/Users/gaofeihang/Desktop/after.log").start();
    }

}
