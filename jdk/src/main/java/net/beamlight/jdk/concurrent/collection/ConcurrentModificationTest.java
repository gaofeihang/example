package net.beamlight.jdk.concurrent.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

/**
 * Created on Aug 21, 2015
 * @author gaofeihang
 */
public class ConcurrentModificationTest {
    
    @Test
    public void testHashMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < 100; i++) {
            map.put("k" + i, "v" + i);
        }
        int i = 0;
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            if (i == 10) {
                map.remove(it.next().getKey());
            }
            i++;
        }
    }
    
    @Test
    public void testHashMapIterator() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < 100; i++) {
            map.put("k" + i, "v" + i);
        }
        int i = 0;
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            if (i == 10) {
                it.remove();
            }
            i++;
        }
    }
    
    @Test
    public void testConcurrentHashMap() {
        Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        for (int i = 0; i < 100; i++) {
            map.put("k" + i, "v" + i);
        }
        int i = 0;
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            if (i == 10) {
                map.remove(it.next().getKey());
            }
            i++;
            System.out.println(map.size());
        }
    }

}
