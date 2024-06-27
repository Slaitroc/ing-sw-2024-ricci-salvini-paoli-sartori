package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

import java.awt.*;

/**
 * This class represents the action of playing a {@link it.polimi.ingsw.gc31.model.card.Card}.
 */
public class PlayObj extends ServerQueueObject {
    /**
     * Is the username of the player who wants to play a {@link it.polimi.ingsw.gc31.model.card.Card}.
     */
    private String username;
    /**
     * Are two values that represents the coordinates where the
     * {@link it.polimi.ingsw.gc31.model.card.Card} needs to be played.
     */
    private int x, y;

    /**
     * This is the constructor of the class.
     *
     * @param username is the username of the player that wants to play the {@link it.polimi.ingsw.gc31.model.card.Card}.
     * @param x is the abscissa value.
     * @param y is the ordinate value.
     */
    public PlayObj(String username, int x, int y) {
        this.username = username;
        this.x = x;
        this.y = y;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#play(String, Point)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.play(username, new Point(x, y));
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
