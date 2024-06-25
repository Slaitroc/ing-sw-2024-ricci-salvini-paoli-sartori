package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;
import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShowScorePlayerObj extends ClientQueueObject {
    private final LinkedHashMap<String, Pair<Integer, Boolean>> scores;

    public ShowScorePlayerObj(LinkedHashMap<String, Pair<Integer, Boolean>> scores) {
        this.scores = scores;
    }

    @Override
    public void execute(UI ui) {
        ui.show_scorePlayer(scores);
    }
}
