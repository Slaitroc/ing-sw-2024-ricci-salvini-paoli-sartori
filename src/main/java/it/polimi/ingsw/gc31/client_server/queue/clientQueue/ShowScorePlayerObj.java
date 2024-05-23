package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowScorePlayerObj extends ClientQueueObject {
    private final String username;
    private final Integer score;

    public ShowScorePlayerObj(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    @Override
    public void execute(UI ui) {
        ui.show_scorePlayer(username, score);
    }
}
