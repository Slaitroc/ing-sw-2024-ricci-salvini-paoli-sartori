package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of joining a game from a client.
 */
public class JoinGameObj extends ServerQueueObject {
    /**
     * Is the username of the client that wants to join a game.
     */
    private final String username;
    /**
     * Is the idGame that identifies the game the player wants to join.
     */
    private final Integer idGame;
    /**
     * Is the VirtualClient associated with the player.
     */
    private final VirtualClient client;

    /**
     * This is the constructor of the class. The value of client is initially set to null.
     *
     * @param username is the username of the client who wants to join a game.
     * @param idGame is the identifier of the game the player wants to join.
     */
    public JoinGameObj(String username, int idGame) {
        this.username = username;
        this.idGame = idGame;
        this.client = null;
    }

    /**
     * This is the constructor of the class with a specific {@link VirtualClient} received as a parameter.
     *
     * @param client is the {@link VirtualClient} associated with the player.
     * @param username  is the username of the player that wants to join the game.
     */
    public JoinGameObj(VirtualClient client, String username) {
        this.username = username;
        this.client = client;
        this.idGame = -1;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#joinGame(String, VirtualClient)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        try {
            gameController.joinGame(username, client);
        } catch (RemoteException e) {

        }
    }

    /**
     * This method is executed by the {@link Controller} when it is polled from the queue.
     * Invokes the {@link Controller#joinGame(String, int)} method.
     *
     * @param controller is the reference to the {@link Controller} associated with the client.
     *
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.joinGame(username, idGame);
        } catch (RemoteException e) {

        }
    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {
    }

}
