package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * StartGameObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the game is started.
 */
public class StartGameObj extends ClientQueueObject {

    /**
     * Notify that the games is started.
     */
    public StartGameObj(){
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the game is started.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.update_ToPlayingState();
    }

}
