package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

public interface VirtualServer extends Remote {
    void RMIserverWrite(String text) throws RemoteException;

    public void connect(VirtualClient client, String username)
            throws RemoteException;

    public void sendCommand(ServerQueueObject obj) throws RemoteException;

    public void setVirtualClient(VirtualClient client) throws RemoteException;
}
