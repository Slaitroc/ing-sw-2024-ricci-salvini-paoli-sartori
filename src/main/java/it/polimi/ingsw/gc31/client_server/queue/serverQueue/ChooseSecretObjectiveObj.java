package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represent the action of choosing the secret objective
 */
public class ChooseSecretObjectiveObj extends ServerQueueObject {
    /**
     * This value indicates which secret objective is chosen by the player.
     */
    private final int choice;
    /**
     * Is the username of the player choosing the secret objective.
     */
    private final String username;

    /**
     * This is the constructor of the class.
     *
     * @param username  is the username of the player choosing the secret objective.
     * @param index     indicates which {@link it.polimi.ingsw.gc31.model.card.ObjectiveCard} needs to be chosen.
     */
    public ChooseSecretObjectiveObj(String username, int index) {
        this.username = username;
        this.choice = index;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#chooseSecretObjective(String, Integer)}.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.chooseSecretObjective(username, choice);
    }

    /**
     * This method is executed if the object should be executed by the {@link Controller} but should be
     * executed by the {@link GameController}.
     *
     * @param controller is the reference to the {@link Controller} that should execute the object.
     */
    @Override
    public void execute(Controller controller) {
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