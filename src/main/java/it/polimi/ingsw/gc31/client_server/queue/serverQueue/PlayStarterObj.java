package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of playing the {@link it.polimi.ingsw.gc31.model.card.StarterCard}.
 */
public class PlayStarterObj extends ServerQueueObject {
    /**
     * Is the username of the client that wants to play the {@link it.polimi.ingsw.gc31.model.card.StarterCard}.
     */
    private String username;

    /**
     * This is the constructor of the class.
     *
     * @param username is the username of the player that wants to play the {@link it.polimi.ingsw.gc31.model.card.StarterCard}.
     */
    public PlayStarterObj(String username) {
        this.username = username;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#playStarter(String)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.playStarter(username);
    }

    /**
     * This method is executed if the object should be executed by the {@link Controller} but should be
     * executed by the {@link GameController}.
     *
     * @param controller is the reference to the {@link Controller} that should execute the object.
     */
    @Override
    public void execute(Controller controller) {
    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     * executed by the {@link GameController}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {
    }

}
