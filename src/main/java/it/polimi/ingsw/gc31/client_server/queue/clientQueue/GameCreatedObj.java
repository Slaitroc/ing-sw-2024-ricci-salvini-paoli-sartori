package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class GameCreatedObj implements ClientQueueObject {
    int gameID;

    public GameCreatedObj(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void execute(UI ui) {
        ui.show_gameCreated(gameID);
    }

}
