package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the methods that can be executed by either a {@link it.polimi.ingsw.gc31.client_server.rmi.RmiClient}
 * and a {@link it.polimi.ingsw.gc31.client_server.tcp.SocketClientHandler}. The methods defined in this interface
 * regard the user.
 * Extends the Rmi {@link Remote} interface, used by the {@link it.polimi.ingsw.gc31.client_server.rmi.RmiClient}.
 *
 * @see it.polimi.ingsw.gc31.client_server.tcp.SocketClientHandler
 * @see it.polimi.ingsw.gc31.client_server.rmi.RmiClient
 * @see Remote
 */
public interface VirtualClient extends Remote {
    /**
     * Return the boolean value indicating if the user is ready or not.
     *
     * @return                  true if the player is ready, false otherwise.
     * @throws RemoteException  if an error occurs during the remote method call.
     */
    public boolean isReady() throws RemoteException;

    /**
     * This method sets the value of the gameID of the player to a value received as a parameter. The gameID
     * identifies the game which is played by the user.
     *
     * @param gameID            is the identifier of the game.
     * @throws RemoteException  if an error occurs during the remote method call.
     */
    public void setGameID(int gameID) throws RemoteException;

    /**
     * This method adds to the {@link VirtualClient} the {@link ClientQueueObject} received as a parameter.
     * This method is invoked mainly by the {@link it.polimi.ingsw.gc31.controller.Controller},
     * {@link it.polimi.ingsw.gc31.controller.GameController} and the
     * {@link it.polimi.ingsw.gc31.client_server.tcp.SocketClientHandler}, can also be invoked
     * by the {@link it.polimi.ingsw.gc31.client_server.listeners.Listener} abstract class and by
     * the {@link it.polimi.ingsw.gc31.model.gameModel.GameModel}, specifically in the
     * {@link it.polimi.ingsw.gc31.model.gameModel.EndGameModelState#endGame(GameModel, String)} method.
     * See also the private methods addQueueObject and executor of {@link it.polimi.ingsw.gc31.client_server.tcp.TCPClient} and {@link it.polimi.ingsw.gc31.client_server.rmi.RmiClient}.
     *
     * @param obj               is the {@link ClientQueueObject} that the client should execute.
     * @throws RemoteException  if an error occurs during the remote method call.
     *
     * @see it.polimi.ingsw.gc31.client_server.listeners.ListenerType
     * @see it.polimi.ingsw.gc31.model.gameModel.GameModelState
     */
    public void sendCommand(ClientQueueObject obj) throws RemoteException;

    /**
     * This method should set the reference of the {@link it.polimi.ingsw.gc31.controller.Controller} attribute
     * of the {@link VirtualClient} to the value received as a parameter. It can be invoked by the {@link it.polimi.ingsw.gc31.controller.Controller} via
     * the {@link it.polimi.ingsw.gc31.controller.Controller#connect(VirtualClient, String, Integer, Integer)} method and
     * usernameValidation private method.
     *
     * @param controller        is the reference of the {@link it.polimi.ingsw.gc31.controller.Controller} to be set to the {@link VirtualClient}.
     * @throws RemoteException  if an error occurs during the remote method call.
     */
    public void setController(IController controller) throws RemoteException;

    /**
     * This method should set the reference of the {@link it.polimi.ingsw.gc31.controller.GameController} attribute
     * of the {@link VirtualClient} to the value received as a parameter. It can be invoked by the {@link it.polimi.ingsw.gc31.controller.Controller} via
     * the {@link it.polimi.ingsw.gc31.controller.Controller#createGame(String, int)} method and
     * by the {@link it.polimi.ingsw.gc31.controller.GameController} via the {@link it.polimi.ingsw.gc31.controller.GameController#joinGame(String, VirtualClient)} and
     * {@link it.polimi.ingsw.gc31.controller.GameController#reJoinGame(String, VirtualClient)} methods.
     *
     * @param gameController    is the reference of the {@link it.polimi.ingsw.gc31.controller.GameController} to be set in the {@link VirtualClient}.
     * @throws RemoteException  if an error occurs during the remote method call.
     */
    public void setGameController(IGameController gameController) throws RemoteException;
}
