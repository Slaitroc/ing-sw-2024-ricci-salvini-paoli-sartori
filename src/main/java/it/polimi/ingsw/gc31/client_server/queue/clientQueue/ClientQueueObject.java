package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.io.Serializable;

/**
 * Abstract class that represents a generic object to be sent from the server to the client.
 * The object contains a method call that is executed by the client.
 * <p>
 * If an attribute is not automatically serialized it is used {@link it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater}.
 */
public abstract class ClientQueueObject implements Serializable {

    /**
     * Executes the method call of the given UI object.
     * This method is meant to be called by the server to send a UI object to the client for execution.
     *
     * @param ui the UI object to execute the method call on
     */
    public abstract void execute(UI ui);

}
