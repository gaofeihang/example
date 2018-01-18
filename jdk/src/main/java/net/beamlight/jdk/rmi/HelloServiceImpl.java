package net.beamlight.jdk.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created on Apr 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService { 

    private static final long serialVersionUID = 1L;

    public HelloServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String hello() throws RemoteException {
        return hello("world");
    }

    @Override
    public String hello(String name) throws RemoteException {
        return "Hello, " + name + "!";
    } 
}
