package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of selecting a {@link it.polimi.ingsw.gc31.model.card.Card}.
 */
public class SelectCardObj extends ServerQueueObject {
    /**
     * Is the username of the player that wants to select a {@link it.polimi.ingsw.gc31.model.card.Card}.
     */
    private String username;
    /**
     * Is a value that indicates the position in the hand of the card to be selected.
     */
    private int index;

    /**
     * This method is the constructor of the class.
     *
     * @param username  is the username of the player that wants to select a card.
     * @param index     is the value that represents the position of the card to be selected in the hand.
     */
    public SelectCardObj(String username, int index) {
        this.username = username;
        this.index = index;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#selectCard(String, int)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.selectCard(username, index);
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

}
