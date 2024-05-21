package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.io.Serializable;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public abstract class ServerQueueObject implements Serializable {

    String recipient;

    public abstract void execute(GameController gameController);

    public abstract void execute(Controller controller);

    public abstract void execute(RmiServer server);

    /**
     * Sets the recipient of the message
     * Recipient is needed only for tcp connection
     * 
     * @param recipient
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
