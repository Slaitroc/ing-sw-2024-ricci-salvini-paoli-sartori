package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.util.List;

import it.polimi.ingsw.gc31.view.UI;

/**
 * ReJoinedObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the result of the rejoin to the game which the player asked to reconnect to has been successful
 */
public class ReJoinedObj extends ClientQueueObject {

    /**
     * The result of the rejoin to the game.
     */
    private final boolean reconnect;

    /**
     * List of the player that are playing the game.
     */
    List<String> players;

    /**
     * Notify the result of the rejoin to the game which the player asked to reconnect to has been successful
     */
    public ReJoinedObj(boolean reconnect, List<String> players) {
        this.reconnect = reconnect;
        this.players = players;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show the result of the rejoin
     * to the game which the player asked to reconnect to has been successful.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_rejoined(reconnect, players);
    }
}
