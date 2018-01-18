package net.beamlight.jdk.reflection;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import net.beamlight.commons.util.JsonUtils;

import org.junit.Test;

/**
 * Created on Sep 23, 2015
 * @author gaofeihang
 */
public class JarFileTest {
    
    @Test
    public void testGetJarFile() {
        Class<?> clazz = JsonUtils.class;
        ProtectionDomain domain = clazz.getProtectionDomain();
        System.out.println(domain);
        CodeSource source = domain.getCodeSource();
        System.out.println(source.getLocation());
    }

}
