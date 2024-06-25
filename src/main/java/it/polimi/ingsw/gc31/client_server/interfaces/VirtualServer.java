package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    void RMIserverWrite(String text) throws RemoteException;

    void sendCommand(ServerQueueObject obj) throws RemoteException;

    void setVirtualClient(VirtualClient client) throws RemoteException;

    String getClientIP() throws RemoteException;

    void generateToken(VirtualClient client) throws RemoteException;
}
