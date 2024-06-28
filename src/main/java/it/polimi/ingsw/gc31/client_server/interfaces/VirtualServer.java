package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void RMIserverWrite(String text) throws RemoteException;

    void sendCommand(ServerQueueObject obj) throws RemoteException;

    String getClientIP() throws RemoteException;

    int generateToken(VirtualClient client) throws RemoteException;

    boolean connect(VirtualClient client, String username, Integer tempToken, Integer token)
            throws RemoteException;

    // TODO
//    public void setVirtualClient(VirtualClient client) throws RemoteException;
}
