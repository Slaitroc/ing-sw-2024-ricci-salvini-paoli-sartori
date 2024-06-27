package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IController extends Remote {

    /**
     * Send a generic command from the client to the Controller or the GameController on Server
     *
     * @param obj Object command specific for each command
     * @throws RemoteException If a generic connection problem with the server occurs
     */
    void sendCommand(ServerQueueObject obj) throws RemoteException;

    /**
     * This method return the specific VirtualClient associated with the unique
     * token received as a parameter
     * The newConnections map contains every client connected with the server and
     * the unique token associated with it
     *
     * @param token is the token associated to the VirtualClient t
     * @return the VirtualClient that has the given token
     */
    VirtualClient getRightConnection(int token) throws RemoteException;

    /**
     * This method is invoked for every heartBeatObj received.
     * The method updates the time value kept in the clientsHeartBeat for the client
     * that sent it.
     * Furthermore, if an heartBeat arrives but the client is not in the Map a
     * specific message is written
     *
     * @param client is the client that sent the heart beat
     * @throws RemoteException if an error occurs in the rmi connection
     */
    void updateHeartBeat(VirtualClient client) throws RemoteException;
}
