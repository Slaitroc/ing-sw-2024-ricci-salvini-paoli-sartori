package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowPlayerTurnObj extends ClientQueueObject{
    private final String username;
    private final String info;

    public ShowPlayerTurnObj(String username, String info) {
        this.username = username;
        this.info = info;
    }

    @Override
    public void execute(UI ui) {
        ui.show_playerTurn(username, info);
    }
}
