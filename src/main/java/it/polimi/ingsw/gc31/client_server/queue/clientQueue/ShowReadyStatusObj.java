package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowReadyStatusObj extends ClientQueueObject {

    boolean status;

    public ShowReadyStatusObj(boolean status) {
        this.status = status;
    }

    @Override
    public void execute(UI ui) {
        ui.show_readyStatus(status);
    }

}
