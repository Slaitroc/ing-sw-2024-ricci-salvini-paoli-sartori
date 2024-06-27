package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * ShowReadyStatusObj is a class that extends ClientQueueObject.
 * It is sent to the client to notify the status of a player in the lobby.
 */
public class ShowReadyStatusObj extends ClientQueueObject {

    /**
     * Username of the player.
     */
    private final String username;

    /**
     * Status of the player. If it is true the player is ready to play, otherwise not.
     */
    private final boolean status;

    /**
     * Notify the status of a player in the lobby.
     * @param username username of the player.
     * @param status status of the player.
     */
    public ShowReadyStatusObj(String username, boolean status) {
        this.username = username;
        this.status = status;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show the status of a player
     * in the lobby.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_readyStatus(username, status);
    }

}
