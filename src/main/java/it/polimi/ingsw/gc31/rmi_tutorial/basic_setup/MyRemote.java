package it.polimi.ingsw.gc31.rmi_tutorial.basic_setup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote {
    public void printMessage(String message, String nick) throws RemoteException;

}