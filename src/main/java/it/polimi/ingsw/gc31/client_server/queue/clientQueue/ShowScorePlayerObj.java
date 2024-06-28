package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;
import javafx.util.Pair;

import java.util.LinkedHashMap;


/**
 * ShowScorePlayerObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the score of all players in the game.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowScorePlayerObj extends ClientQueueObject {

    /**
     * Map containing all the players in the game and their score.
     */
    private final LinkedHashMap<String, Pair<Integer, Boolean>> scores;

    /**
     * Notify the score of all players in the game.
     *
     * @param scores Map containing all the players in the game and their score.
     */
    public ShowScorePlayerObj(LinkedHashMap<String, Pair<Integer, Boolean>> scores) {
        this.scores = scores;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show the score of
     * all the players in the game.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_scorePlayer(scores);
    }
}
