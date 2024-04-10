package it.rmi30l.basic_setup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote {
    public void printMessage(String message) throws RemoteException;

}
