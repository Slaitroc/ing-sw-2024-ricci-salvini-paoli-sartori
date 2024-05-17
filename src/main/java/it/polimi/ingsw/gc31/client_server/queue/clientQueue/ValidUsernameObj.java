package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ValidUsernameObj implements ClientQueueObject {

    @Override
    public void execute(UI ui) {
        ui.validUsername();
    }

}
