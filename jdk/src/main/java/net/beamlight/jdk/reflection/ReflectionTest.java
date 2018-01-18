package net.beamlight.jdk.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Aug 28, 2015
 * @author gaofeihang
 */
public class ReflectionTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(ReflectionTest.class);
    
    @Test
    public void testMethodNormal() {
        testMethodWith("helloDouble", new Double(1));
    }
    
    @Test
    public void testMethodIllegal() {
        testMethodWith("helloDouble", new Integer(1));
    }
    
    @Test
    public void testPrimitiveMethodNormal() {
        testMethodWith("helloPrimitiveDouble", new Double(1));
    }
    
    private void testMethodWith(String methodName, Object... args) {
        TestManager manager = new TestManager();
        Class<?> clazz = manager.getClass();
        
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            
            try {
                LOG.info(dumpParameterTypes(method.getParameterTypes()));
                LOG.info(dumpParameterTypes(method.getGenericParameterTypes()));
                method.invoke(manager, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private String dumpParameterTypes(Type[] parameterTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Type type : parameterTypes) {
            sb.append(type).append(",");
            sb.append(type instanceof Class).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

}
