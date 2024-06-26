package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IController extends Remote {

    /**
     * Send a generic command from the client to the Controller on Server
     *
     * @param obj Object command specific for each command
     * @throws RemoteException If there is a generic connection problem with the server
     */
    void sendCommand(ServerQueueObject obj) throws RemoteException;

    //TODO
    VirtualClient getRightConnection(int token) throws RemoteException;

    //TODO
    void updateHeartBeat(VirtualClient client) throws RemoteException;
}
