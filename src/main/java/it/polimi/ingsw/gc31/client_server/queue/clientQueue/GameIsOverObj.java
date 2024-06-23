package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.util.Map;

public class GameIsOverObj extends ClientQueueObject{
    private final String username;
    private final Map<String, Integer> playersScore;

    public GameIsOverObj(String username, Map<String, Integer> playersScore) {
        this.username = username;
        this.playersScore = playersScore;
    }

    @Override
    public void execute(UI ui) {
        ui.show_GameIsOver(username, playersScore);
    }
}
