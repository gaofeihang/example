package net.beamlight.jdk.rmi;

import java.rmi.Naming;

/**
 * Created on Apr 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class HelloClient {

    public static void main(String[] args) {
        try {
            HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8887/hello");
            System.out.println(helloService.hello());
            System.out.println(helloService.hello("beamlight"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
