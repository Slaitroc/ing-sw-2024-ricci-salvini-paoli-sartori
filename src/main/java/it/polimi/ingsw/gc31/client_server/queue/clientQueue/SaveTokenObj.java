package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.io.Serializable;

import it.polimi.ingsw.gc31.view.UI;

/**
 * SaveToken is a class that extends ClientQueueObject
 * It is sent to the client to save the token the Server generated for the
 * connection.
 * The token is temporary if it is the token of first connection, before the
 * username is set.
 */
public class SaveTokenObj extends ClientQueueObject implements Serializable {

    /**
     * The token to be saved.
     */
    int token;

    /**
     * Boolean variable that tell if the token sent is temporary or not.
     */
    boolean temporary;

    /**
     * Save in the client the token the Server generated for the connection.
     * 
     * @param token     token to be saved.
     * @param temporary if the token is temporary or not.
     */
    public SaveTokenObj(int token, boolean temporary) {
        this.token = token;
        this.temporary = temporary;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI o save the token the Server generated
     * for the connection.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.receiveToken(token, temporary);
    }
}
