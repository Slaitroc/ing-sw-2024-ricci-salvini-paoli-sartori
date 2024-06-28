package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * WantsReconnectObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the player can reconnect to a game and
 * the player has to choose to join or not.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class WantsReconnectObj extends ClientQueueObject {

    /**
     * Username of the player.
     */
    final String username;

    /**
     * Notify that the player can reconnect to a game and the player has to choose to join or not.
     * @param username username of the player.
     */
    public WantsReconnectObj(String username) {
        this.username = username;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the player can reconnect to a game
     * and the player has to choose to join or not.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_wantReconnect(username);
    }

}
