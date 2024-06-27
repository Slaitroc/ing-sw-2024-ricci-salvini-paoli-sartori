package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * GameCreateObj is a class that extends ClientQueueObject
 * Is sent to the client when a is created when a game is created. When executed, it notifies the UI that a game has been created with a specific ID.
 */
public class GameCreatedObj extends ClientQueueObject {
    /**
     * Represents the ID of a game that has been created.
     */
    int gameID;

    /**
     * Represents an object that is created when a game is created.
     */
    public GameCreatedObj(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Call the corresponding method on the UI to show that the game has started
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_gameCreated(gameID);
    }

}
