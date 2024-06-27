package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.util.List;

import it.polimi.ingsw.gc31.view.UI;

/**
 * ShowGamesObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the list of the games of the server.
 */
public class ShowGamesObj extends ClientQueueObject {

    /**
     * List of the game of the server.
     */
    private final List<String> gameList;

    /**
     * Notify the list of the games of the server.
     *
     * @param gameList list of the game of the server
     */
    public ShowGamesObj(List<String> gameList) {
        this.gameList = gameList;
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
        ui.show_listGame(gameList);
    }

}
