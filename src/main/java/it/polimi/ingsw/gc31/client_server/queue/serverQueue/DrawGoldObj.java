package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;

/**
 * This class represents the action of drawing a {@link it.polimi.ingsw.gc31.model.card.GoldCard}.
 */
public class DrawGoldObj extends ServerQueueObject {
    /**
     * Is the username of the player that wants to draw a gold card.
     */
    private final String username;
    /**
     * Is the value indicating which card the player wants to draw.
     */
    private final int select;

    /**
     * This is the constructor of the class.
     *
     * @param username  is the username of the player that wants to draw a {@link it.polimi.ingsw.gc31.model.card.GoldCard}.
     * @param select    is an int indicating which {@link it.polimi.ingsw.gc31.model.card.GoldCard} the player wants to draw.
     */
    public DrawGoldObj(String username, int select) {
        this.username = username;
        this.select = select;
    }

    /**
     * This method is executed if the object should be executed by the {@link Controller} but should be
     * executed by the {@link GameController}.
     *
     * @param controller is the reference to the {@link Controller} that should execute the object.
     */
    @Override
    public void execute(Controller controller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     * executed by the {@link GameController}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#drawGold(String, int)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    public void execute(GameController gameController) {
        gameController.drawGold(username, select);
    }

}
