package it.polimi.ingsw.gc31.client_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    public boolean clientConnection(VirtualClient client, String nick) throws RemoteException;

    public void printMessageOnServer(String message) throws RemoteException;
}
