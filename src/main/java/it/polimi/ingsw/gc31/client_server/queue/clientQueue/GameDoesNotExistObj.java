package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * GameDoesNotExistObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the game the client wants to join doesn't exist.
 */
public class GameDoesNotExistObj extends ClientQueueObject {

    /**
     * Notify that the game the client wants to join doesn't exist.
     */
    public GameDoesNotExistObj(){}

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the game the client
     * wants to join doesn't exist.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_gameDoesNotExist();
    }
}
