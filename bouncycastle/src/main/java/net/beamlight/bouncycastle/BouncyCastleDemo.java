package net.beamlight.bouncycastle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.beamlight.commons.util.ByteArrayUtils;

public class BouncyCastleDemo {
	
	private static final Logger LOG = LoggerFactory.getLogger(BouncyCastleDemo.class);
    
    public void start() {
    	Security.addProvider(new BouncyCastleProvider());
    	
    	Key                     key;
        Cipher                  in, out;
        CipherInputStream       cIn;
        CipherOutputStream      cOut;
        ByteArrayInputStream    bIn;
        ByteArrayOutputStream   bOut;
        
        byte[] bytes = Hex.decode("000102030405060708090a0b0c0d0e0f1011121314151617");
        LOG.info("key bytes: {}, {}", bytes.length, ByteArrayUtils.hexDump(bytes));
        
        key = new SecretKeySpec(bytes, "AES");
        
        try {
        	in = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
            out = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
            
            in.init(Cipher.DECRYPT_MODE, key);
            out.init(Cipher.ENCRYPT_MODE, key);
            
            bOut = new ByteArrayOutputStream();
            cOut = new CipherOutputStream(bOut, out);
            
			byte[] input = "Hello, world!".getBytes("UTF-8");
			LOG.info("data bytes: {}, {}", input.length, ByteArrayUtils.hexDump(input));

			cOut.write(input);
			cOut.close();
            
            bytes = bOut.toByteArray();
            LOG.info("encryted bytes: {}, {}", bytes.length, ByteArrayUtils.hexDump(bytes));
            
            bIn = new ByteArrayInputStream(bytes);
            cIn = new CipherInputStream(bIn, in);
            
            bytes = new byte[input.length];
            cIn.read(bytes);
			
			LOG.info("decryted bytes: {}, {}", bytes.length, ByteArrayUtils.hexDump(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        new BouncyCastleDemo().start();
    }

}
