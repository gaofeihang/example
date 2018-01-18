package net.beamlight.jdk.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created on Mar 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class CustomClassLoader extends ClassLoader {
    
    public static final String CUSTOM_PACKAGE = "net.beamlight.jdk.lang.classloader";
    public static final String PACKAGE_ROOT = "file:///Users/gaofeihang/workspace/example/example-jdk/target/classes/";
    
    @SuppressWarnings("rawtypes")
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        if (name.startsWith(CUSTOM_PACKAGE)) {
            System.out.println("find class");

            Class clazz = null;
            byte[] classData = getClassData(name);
            if (classData == null) {
                throw new ClassNotFoundException();
            }
            clazz = defineClass(name, classData, 0, classData.length);
            return clazz;
        }
        return super.findClass(name);
    }
    
    private byte[] getClassData(String name) {
        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024*4];
            int len = -1;
            is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = is.read(buff)) != -1) {
                baos.write(buff,0,len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
               try {
                  is.close();
               } catch(IOException e) {
                  e.printStackTrace();
               }
            }
        }
        return null;
    }
    
    private String classNameToPath(String name) {
        return PACKAGE_ROOT + "/" + name.replace(".", "/") + ".class";
    }
    
    @SuppressWarnings({ "rawtypes", "restriction" })
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith(CUSTOM_PACKAGE)) {
            boolean resolve = true;
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();

                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();
                        c = findClass(name);

                        // this is the defining class loader; record the stats
                        sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }
        return super.loadClass(name);
    }

}
