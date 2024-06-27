package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * QuitFromGameRObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the quit from the game the player was playing has been successful.
 */
public class QuitFromGameRObj extends ClientQueueObject {

    /**
     * Notify that the quit from the game the player was playing has been successful.
     */
    public QuitFromGameRObj() {
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the quit from the game the player
     * was playing has been successful.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_quitFromGame();
    }

}
