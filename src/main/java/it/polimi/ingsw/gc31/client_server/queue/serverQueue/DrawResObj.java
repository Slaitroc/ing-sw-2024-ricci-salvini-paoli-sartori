package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of drawing a {@link it.polimi.ingsw.gc31.model.card.ResourceCard}.
 */
public class DrawResObj extends ServerQueueObject {
    /**
     * Is the username of the client that wants to draw a {@link it.polimi.ingsw.gc31.model.card.ResourceCard}.
     */
    private final String username;
    /**
     * Is the value that indicates which {@link it.polimi.ingsw.gc31.model.card.ResourceCard} to draw.
     */
    private final int index;

    /**
     * This is the constructor of the class.
     *
     * @param username  is the username of the client that wants to draw the {@link it.polimi.ingsw.gc31.model.card.ResourceCard}.
     * @param index     is the int that indicates which {@link it.polimi.ingsw.gc31.model.card.ResourceCard} to draw.
     */
    public DrawResObj(String username, int index) {
        this.username = username;
        this.index = index;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#drawResource(String, int)}.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.drawResource(username, index);
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
     *
     */
    @Override
    public void execute(RmiServer server) {
    }

}
