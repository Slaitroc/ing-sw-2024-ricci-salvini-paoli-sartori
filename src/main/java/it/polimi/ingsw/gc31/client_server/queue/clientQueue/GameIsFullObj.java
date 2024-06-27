package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * GameIsFullObj is a class that extends ClientQueueObject.
 * It is sent to the client to notify that the game the client wants to join is full.
 */
public class GameIsFullObj extends ClientQueueObject {

    /**
     * The id of the Game
     */
    private final int gameID;

    /**
     * Notify that the game the client wants to join is full.
     *
     * @param gameID the id of the game
     */
    public GameIsFullObj(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the game the client
     * wants to join is full.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_gameIsFull(gameID);
    }

}
