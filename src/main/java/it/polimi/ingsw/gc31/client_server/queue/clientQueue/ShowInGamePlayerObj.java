package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.util.LinkedHashMap;

/**
 * ShowInGamePlayerObj is a class that extends ClientQueueObject
 * It is sent to the client when the players are in the lobby to notify the list of the player
 * if they are ready or not.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowInGamePlayerObj extends ClientQueueObject {

    /**
     * Map of all the player in the game and if they are ready or not.
     */
    private final LinkedHashMap<String, Boolean> players;

    /**
     * When the players are in the lobby it notifies the list of the player
     * if they are ready or not.
     *
     * @param players all the players in the game and if they are ready or not.
     */
    public ShowInGamePlayerObj(LinkedHashMap<String, Boolean> players) {
        this.players = players;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show when the players are in the lobby it notifies the list of the player
     * if they are ready or not.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_inGamePlayers(players);
    }
}
