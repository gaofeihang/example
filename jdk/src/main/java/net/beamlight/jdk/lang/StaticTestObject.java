package net.beamlight.jdk.lang;

/**
 * Created on Oct 15, 2015
 * @author gaofeihang
 */
@SuppressWarnings("unused")
public class StaticTestObject {
    
    static {
        String block = getBlockString();
    }
    
    private static String field = getFiledString();
    
    static {
        String block = getBlockString();
    }
    
    private static String field2 = getFiledString();
    
    public StaticTestObject() {
        String constructor = getConstructorString();
    }
    
    public static String getFiledString() {
        System.out.println("static field initialized");
        return "field";
    }
    
    public static String getBlockString() {
        System.out.println("static block initialized");
        return "block";
    }
    
    public static String getConstructorString() {
        System.out.println("constructor initialized");
        return "constructor";
    }

}
