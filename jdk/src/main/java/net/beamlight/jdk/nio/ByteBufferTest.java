package net.beamlight.jdk.nio;

import java.nio.ByteBuffer;

import net.beamlight.commons.util.ByteArrayUtils;

import org.junit.Test;

/**
 * Created on Aug 25, 2015
 * @author gaofeihang
 */
public class ByteBufferTest {
    
    @Test
    public void testByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        System.out.println(buffer);
        byte[] src = new byte[64];
        buffer.put(src);
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);
        src = new byte[60];
        buffer.put(src);
        System.out.println(buffer);
        ByteBuffer copy = buffer.duplicate();
        System.out.println(copy);
        buffer.put((byte) 'a');
        System.out.println(ByteArrayUtils.prettyPrint(copy.array()));
    }

}
