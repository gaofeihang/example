package net.beamlight.jdk.classloader;

import java.net.URL;

/**
 * Created on Mar 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class ClassLoderExample {

    @SuppressWarnings({ "restriction" })
    public void start() {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoderExample.class.getClassLoader());

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }

        try {
            CustomClassLoader customClassLoader = new CustomClassLoader();
            System.out.println(customClassLoader.getParent());
            Class<?> clazz = customClassLoader.loadClass("net.beamlight.jdk.lang.classloader.Integer");
            printClassLoaders(clazz.getClassLoader());
            System.out.println(clazz.equals(Integer.class));
            
            Thread.currentThread().setContextClassLoader(customClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
      Integer.hello();
      System.out.println(Integer.class.getClassLoader());
    }
    
    private void printClassLoaders(ClassLoader loader) {
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();
        }
        System.out.println(loader);
    }

    public static void main(String[] args) {
        new ClassLoderExample().start();
    }

}
