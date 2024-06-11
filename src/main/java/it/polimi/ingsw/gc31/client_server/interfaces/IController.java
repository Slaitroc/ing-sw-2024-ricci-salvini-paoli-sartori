package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

public interface IController extends Remote {
    void sendCommand(ServerQueueObject obj) throws RemoteException;
}
