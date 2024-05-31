package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowInvalidActionObj extends ClientQueueObject{
    private final String message;

    public ShowInvalidActionObj(String message) {
        this.message = message;
    }

    @Override
    public void execute(UI ui) {
        ui.show_invalidAction(message);
    }
}
