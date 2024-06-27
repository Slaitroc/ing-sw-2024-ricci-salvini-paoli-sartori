package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of setting ready in the lobby.
 */
public class ReadyStatusObj extends ServerQueueObject {
    /**
     * Is the boolean value that represents if the player is ready or not.
     */
    boolean ready;
    /**
     * Is the username of the player.
     */
    String username;

    /**
     * This is the constructor of the class.
     *
     * @param ready     is the boolean value to set to the attribute ready.
     * @param username  is the username of the player setting the value of ready.
     */
    public ReadyStatusObj(boolean ready, String username) {
        this.ready = ready;
        this.username = username;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#setReadyStatus(boolean, String)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.setReadyStatus(ready, username);
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
