package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.io.Serializable;

import it.polimi.ingsw.gc31.view.UI;

public class ReJoinedObj extends ClientQueueObject implements Serializable {
    private boolean reconnect;

    public ReJoinedObj(boolean reconnect) {
        this.reconnect = reconnect;
    }

    @Override
    public void execute(UI ui) {
        ui.show_rejoined(reconnect);
    }
}
