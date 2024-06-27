package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * ShowPlayerTurnObj is a class that extends ClientQueueObject.
 * It is sent to the client to notify that info about the turn of the player.
 */
public class ShowPlayerTurnObj extends ClientQueueObject{

    /**
     * Username of the player.
     */
    private final String username;

    /**
     * Info about the turn of the player.
     */
    private final String info;

    /**
     * Notify that info about the turn of the player.
     * @param username username of the player.
     * @param info info about the turn of the player.
     */
    public ShowPlayerTurnObj(String username, String info) {
        this.username = username;
        this.info = info;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show info about the turn of the player.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_playerTurn(username, info);
    }
}
