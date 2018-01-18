package net.beamlight.jdk.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created on Apr 7, 2015
 * 
 * @author gaofeihang
 * @since 1.0.0
 */
public interface HelloService extends Remote {

    String hello() throws RemoteException;

    String hello(String name) throws RemoteException;

}
