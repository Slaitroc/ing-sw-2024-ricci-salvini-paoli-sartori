package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.util.LinkedHashMap;

public class ShowScorePlayerObj extends ClientQueueObject {
    private final LinkedHashMap<String, Integer> scores;

    public ShowScorePlayerObj(LinkedHashMap<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public void execute(UI ui) {
        ui.show_scorePlayer(scores);
    }
}
