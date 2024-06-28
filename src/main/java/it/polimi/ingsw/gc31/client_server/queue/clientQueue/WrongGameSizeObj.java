package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * WrongGameSizeObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the number of player he sent to create the game is not valid.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class WrongGameSizeObj extends ClientQueueObject {

    /**
     * Notify that the number of player he sent to create the game is not valid.
     */
    public WrongGameSizeObj(){
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the number he sent to crate the game is not valid.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_wrongGameSize();
    }

}
