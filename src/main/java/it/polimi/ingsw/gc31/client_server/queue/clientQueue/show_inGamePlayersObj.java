package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.util.List;

public class show_inGamePlayersObj extends ClientQueueObject {
    private final List<String> players;

    public show_inGamePlayersObj(List<String> players) {
        this.players = players;
    }

    @Override
    public void execute(UI ui) {

    }
}
