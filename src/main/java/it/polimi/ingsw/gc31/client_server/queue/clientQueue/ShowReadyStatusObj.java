package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowReadyStatusObj extends ClientQueueObject {

    private final String username;
    private final boolean status;

    public ShowReadyStatusObj(String username, boolean status) {
        this.username = username;
        this.status = status;
    }

    @Override
    public void execute(UI ui) {
        ui.show_readyStatus(username, status);
    }

}
