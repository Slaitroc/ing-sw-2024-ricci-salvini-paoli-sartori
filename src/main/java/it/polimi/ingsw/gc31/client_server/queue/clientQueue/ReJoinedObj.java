package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.util.List;

import it.polimi.ingsw.gc31.view.UI;

public class ReJoinedObj extends ClientQueueObject {
    private boolean reconnect;
    List<String> players;

    public ReJoinedObj(boolean reconnect, List<String> players) {
        this.reconnect = reconnect;
        this.players = players;
    }

    @Override
    public void execute(UI ui) {
        ui.show_rejoined(reconnect, players);
    }
}
