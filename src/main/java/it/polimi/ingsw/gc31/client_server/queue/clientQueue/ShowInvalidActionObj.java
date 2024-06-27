package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * ShowInvalidActionObj is a class that extends ClientQueueObject
 * It is sent to the client to notify an invalid action of the player.
 */
public class ShowInvalidActionObj extends ClientQueueObject{

    /**
     * What invalid action the player did.
     */
    private final String message;

    /**
     * Notify an invalid action of the player.
     *
     * @param message what invalid action the player did.
     */
    public ShowInvalidActionObj(String message) {
        this.message = message;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show an invalid action.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_invalidAction(message);
    }
}
