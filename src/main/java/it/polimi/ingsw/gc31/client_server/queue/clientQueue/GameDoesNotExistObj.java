package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class GameDoesNotExistObj extends ClientQueueObject {

    public GameDoesNotExistObj(){

    }
    @Override
    public void execute(UI ui) {
        ui.show_gameDoesNotExist();
    }
}