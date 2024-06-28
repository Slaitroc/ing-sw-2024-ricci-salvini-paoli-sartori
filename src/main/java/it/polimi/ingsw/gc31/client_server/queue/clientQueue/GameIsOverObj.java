package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.util.Map;

/**
 * GameIsOverObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the game he was playing is over.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class GameIsOverObj extends ClientQueueObject{

    /**
     * Username of the winner who has won.
     */
    private final String username;

    /**
     * Final score of the player.
     */
    private final Map<String, Integer> playersScore;

    /**
     * Notify that the game he was playing is over.
     *
     * @param username username of the player who has won.
     * @param playersScore map the contains each player of the game with their final scores.
     */
    public GameIsOverObj(String username, Map<String, Integer> playersScore) {
        this.username = username;
        this.playersScore = playersScore;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the game the client
     * was playing is over.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_GameIsOver(username, playersScore);
    }
}
