package net.beamlight.jdk.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created on Apr 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class HelloServer {

    public static void main(String[] args) {
        try {
            HelloService helloService = new HelloServiceImpl();
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/hello", helloService);
            System.out.println("Hello server started!"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
