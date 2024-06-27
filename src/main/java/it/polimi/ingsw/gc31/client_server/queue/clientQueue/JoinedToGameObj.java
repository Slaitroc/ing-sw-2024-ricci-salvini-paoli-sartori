package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * JoinedToGameObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the join to the game has been successful.
 */
public class JoinedToGameObj extends ClientQueueObject {

    /**
     * The id of the Game
     */
    int idGame;

    /**
     * The number of players of the game.
     */
    int maxNumberOfPlayers;

    /**
     * Notify that the join to the game has been successful.
     */
    public JoinedToGameObj(int idGame, int maxNumberOfPlayers) {
        this.idGame = idGame;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the join
     * to the game has been successful.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_joinedToGame(idGame, maxNumberOfPlayers);
    }
}
