package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class WantsReconnectObj extends ClientQueueObject {
    final String username;

    public WantsReconnectObj(String username) {
        this.username = username;
    }

    @Override
    public void execute(UI ui) {
        ui.show_wantReconnect(username);
    }

}
