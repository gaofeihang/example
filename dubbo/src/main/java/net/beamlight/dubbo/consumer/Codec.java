package net.beamlight.dubbo.consumer;

import net.beamlight.commons.util.ByteArrayUtils;

import java.util.Random;

/**
 * Created by gaofeihang on 2017/12/5.
 */
public class Codec {

    public static void main(String[] args) {
        try {
            String a = "你好";
            byte[] b = a.getBytes("Unicode");
            byte[] c = new byte[4];
            for (int i = 0; i < c.length; i++) {
                c[i] = b[i + 2];
            }
            System.out.println(ByteArrayUtils.hexDump(c));
            System.out.println(new String(c, "Unicode"));
        } catch (Exception e) {
        }

        new Random().nextInt();
    }

}
