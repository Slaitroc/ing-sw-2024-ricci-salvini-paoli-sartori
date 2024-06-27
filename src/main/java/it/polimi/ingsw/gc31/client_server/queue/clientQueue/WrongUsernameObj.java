package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * WrongUsernameObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the username he chose is not valid.
 */
public class WrongUsernameObj extends ClientQueueObject {

    /**
     * Username the player had chose.
     */
    String username;

    /**
     * Notify that the username he chose is not valid.
     * @param username username the player had chose.
     */
    public WrongUsernameObj(String username) {
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
        if (username != null) {
            ui.show_wrongUsername(username);
        } else {
            ui.show_unableToReconnect();
        }
    }

}
