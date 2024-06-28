package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the methods a {@link it.polimi.ingsw.gc31.controller.Controller} can execute. Extends the {@link Remote} interface.
 * A {@link it.polimi.ingsw.gc31.controller.Controller} manages and contains all the logic above the singular matches, for the matches logic
 * is created a {@link it.polimi.ingsw.gc31.controller.GameController}, one for each game.
 *
 * @see IGameController
 * @see it.polimi.ingsw.gc31.controller.GameController
 */
public interface IController extends Remote {

    /**
     * Send a {@link ServerQueueObject} from the client to the {@link it.polimi.ingsw.gc31.controller.Controller}
     * or the {@link it.polimi.ingsw.gc31.controller.GameController} on {@link it.polimi.ingsw.gc31.Server}.
     *
     * @param obj               Object command specific for each command
     * @throws RemoteException  If a generic connection problem with the server occurs
     */
    void sendCommand(ServerQueueObject obj) throws RemoteException;

    /**
     * This method return the specific {@link VirtualClient} associated with the unique
     * token received as a parameter.
     * The newConnections map contains every {@link VirtualClient} connected with the {@link it.polimi.ingsw.gc31.Server}
     * and the unique token associated with it.
     *
     * @param   token is the token associated to the VirtualClient t
     * @return  the VirtualClient that has the given token
     */
    VirtualClient getRightConnection(int token) throws RemoteException;

    /**
     * This method is invoked for every {@link it.polimi.ingsw.gc31.client_server.queue.serverQueue.HeartBeatObj}
     * received.
     * The method updates the time value kept in the private attribute clientsHeartBeat for the {@link VirtualClient}
     * that sent it.
     * Furthermore, if an {@link it.polimi.ingsw.gc31.client_server.queue.serverQueue.HeartBeatObj}
     * arrives but the client is not in the Map a specific message is written.
     *
     * @param client            is the client that sent the heart beat
     * @throws RemoteException  if an error occurs in the rmi connection
     */
    void updateHeartBeat(VirtualClient client) throws RemoteException;
}
