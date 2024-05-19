package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowScorePlayerObj implements ClientQueueObject{
    private final String username;
    private final Integer score;

    public ShowScorePlayerObj(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    @Override
    public void execute(UI ui) {

    }
}
