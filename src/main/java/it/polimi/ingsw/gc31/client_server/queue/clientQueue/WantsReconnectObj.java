package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class WantsReconnectObj extends ClientQueueObject {

    public WantsReconnectObj() {
    }

    @Override
    public void execute(UI ui) {
        ui.show_wantReconnect();
    }

}
