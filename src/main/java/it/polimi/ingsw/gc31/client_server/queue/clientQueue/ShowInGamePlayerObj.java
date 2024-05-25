package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.util.LinkedHashMap;
import java.util.List;

public class ShowInGamePlayerObj extends ClientQueueObject{
    private final LinkedHashMap<String, Boolean> players;

    public ShowInGamePlayerObj(LinkedHashMap<String, Boolean> players) {
        this.players = players;
    }

    @Override
    public void execute(UI ui) {
        ui.show_inGamePlayers(players);
    }
}
