package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void RMIserverWrite(String text) throws RemoteException;

    public void sendCommand(ServerQueueObject obj) throws RemoteException;

    public void setVirtualClient(VirtualClient client) throws RemoteException;

    public String getClientIP() throws RemoteException;

    public int generateToken(VirtualClient client) throws RemoteException;

    public boolean connect(VirtualClient client, String username, Integer tempToken, Integer token)
            throws RemoteException;
}
