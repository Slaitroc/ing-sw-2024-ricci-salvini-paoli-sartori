package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.client_server.tcp.SocketClientHandler;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the methods a {@link it.polimi.ingsw.gc31.controller.GameController} can execute. Extends the {@link Remote} interface.
 * A {@link it.polimi.ingsw.gc31.controller.GameController} manages and contains all the logic of a specific match.
 *
 * @see IController
 * @see it.polimi.ingsw.gc31.controller.Controller
 */
public interface IGameController extends Remote {
    /**
     * Use this for test purposes only
     *
     * @return the GameModel
     * @throws RemoteException if an error occurs during the remote method call.
     * @deprecated
     */
    GameModel getModel() throws RemoteException;

    // void checkReady() throws RemoteException, IllegalStateOperationException;

    // public void setReadyStatus(boolean ready, String username) throws
    // RemoteException, IllegalStateOperationException;

    /**
     * Adds a {@link ServerQueueObject} to the List of object that needs to be executed of the {@link it.polimi.ingsw.gc31.controller.GameController}.
     * Can be invoked by many {@link it.polimi.ingsw.gc31.client_server.rmi.RmiClient} methods, by the {@link SocketClientHandler} tcpClient_reader method.
     * It can be also directly invoked by the {@link it.polimi.ingsw.gc31.controller.Controller#joinGame(String, int)} method
     * and also by the {@link it.polimi.ingsw.gc31.controller.GameController#disconnectPlayer(String)} and
     * {@link it.polimi.ingsw.gc31.controller.GameController#timerLastPlayerConnected(String)} methods.
     *
     * @param obj               is the new {@link ServerQueueObject} to add to the List.
     * @throws RemoteException  if an error occurs during the remote method call.
     */
    void sendCommand(ServerQueueObject obj) throws RemoteException;

}
