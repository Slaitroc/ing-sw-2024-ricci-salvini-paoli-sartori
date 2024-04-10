package it.polimi.ingsw.gc31.client_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RMIClient;

public interface VirtualClient extends Remote {
    public RMIClient setUsername() throws RemoteException;
}
