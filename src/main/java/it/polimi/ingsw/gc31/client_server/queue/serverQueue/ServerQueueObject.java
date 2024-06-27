package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.io.Serializable;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents a generic action the server needs to perform upon a request from the client.
 */
public abstract class ServerQueueObject implements Serializable {
    /**
     * Identifies the recipient of the object. Could be {@link Controller}, {@link GameController} or {@link RmiServer}.
     */
    private String recipient;

    /**
     * Is the method that will be executed if the recipient of the object is the {@link GameController}.
     *
     * @param gameController is the reference to the {@link GameController}.
     */
    public abstract void execute(GameController gameController);

    /**
     * Is the method that will be executed if the recipient of the object is the {@link Controller}.
     *
     * @param controller is the reference to the {@link Controller}.
     */
    public abstract void execute(Controller controller);

    /**
     * Is the method that will be executed if the recipient of the object is the {@link RmiServer}.
     *
     * @param server is the reference to the {@link RmiServer}.
     */
    public abstract void execute(RmiServer server);

    /**
     * Sets the recipient of the message
     * Recipient is needed only for tcp connection
     * 
     * @param recipient identifies the recipient of the object.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets the recipient of the message
     * 
     * @return String recipient
     */
    public String getRecipient() {
        return recipient;
    }

}
