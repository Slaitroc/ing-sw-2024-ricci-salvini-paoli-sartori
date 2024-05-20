package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class GameIsFullObj extends ClientQueueObject {

    int gameID;

    public GameIsFullObj(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void execute(UI ui) {
        ui.show_gameIsFull(gameID);
    }

}
