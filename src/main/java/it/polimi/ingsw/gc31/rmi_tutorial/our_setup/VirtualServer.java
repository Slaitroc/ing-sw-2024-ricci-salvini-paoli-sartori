package it.polimi.ingsw.gc31.rmi_tutorial.our_setup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void printMessage(String message) throws RemoteException;

    // public void printMessage(RMIClient client, String message) throws
    // RemoteException;

}
