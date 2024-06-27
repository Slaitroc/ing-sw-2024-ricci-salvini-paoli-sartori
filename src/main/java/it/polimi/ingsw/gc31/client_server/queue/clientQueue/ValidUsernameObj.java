package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * ValidUsernameObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the username he chose is valid.
 */
public class ValidUsernameObj extends ClientQueueObject {

    /**
     * Username the player had chose.
     */
    String username;

    /**
     * Notify that the username he chose is valid.
     * @param username username the player had chose.
     */
    public ValidUsernameObj(String username) {
        this.username = username;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI that the username he chose is valid.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_validUsername(username);
    }

}
