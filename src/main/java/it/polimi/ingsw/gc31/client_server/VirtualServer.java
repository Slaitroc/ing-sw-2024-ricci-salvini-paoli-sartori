package it.polimi.ingsw.gc31.client_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    public void clientConnection(VirtualClient client, String nick) throws RemoteException;
}
