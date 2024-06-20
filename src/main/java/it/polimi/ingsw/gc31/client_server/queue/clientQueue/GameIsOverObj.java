package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class GameIsOverObj extends ClientQueueObject{
    private final String username;

    public GameIsOverObj(String username) {
        this.username = username;
    }

    @Override
    public void execute(UI ui) {
        ui.show_GameIsOver(username);
    }
}
