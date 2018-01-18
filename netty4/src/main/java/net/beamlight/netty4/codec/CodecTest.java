package net.beamlight.netty4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class CodecTest {
    
    public CodecTest() {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(4);
        
        buf.writeBytes(new byte[] { 0, 0, '\r', '\n'});
//        System.out.println(ByteArrayUtils.prettyPrint(buf.array()));
        System.out.println(buf.readInt());
    }
    
    public static void main(String[] args) {
        new CodecTest();
    }

}
