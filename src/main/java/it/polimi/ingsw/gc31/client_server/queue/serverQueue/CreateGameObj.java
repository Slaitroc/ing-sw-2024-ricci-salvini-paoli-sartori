package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of creating a game
 */
public class CreateGameObj extends ServerQueueObject {
    /**
     * Is the username of the client that wants to create a game.
     */
    private final String username;
    /**
     * Is the number of player of the game.
     */
    private final int maxNumPlayer;

    /**
     * This is the constructor of the class.
     *
     * @param username          is the username of the client that wants to create a game.
     * @param maxNumberPlayer   is the number of players for the game.
     */
    public CreateGameObj(String username, int maxNumberPlayer) {
        this.username = username;
        this.maxNumPlayer = maxNumberPlayer;
    }

    /**
     * This method is executed if the object should be executed by the {@link GameController} but should be
     * executed by the {@link Controller}.
     *
     * @param gameController is the reference to the {@link GameController} that should execute the object.
     */
    @Override
    public void execute(GameController gameController) {
    }

    /**
     * This method is executed by the {@link Controller} when it is polled from the queue.
     * Invokes the {@link Controller#createGame(String, int)}.
     *
     * @param controller is the reference to the {@link Controller} associated with the client.
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.createGame(username, maxNumPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     * executed by the {@link Controller}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {
    }

}
