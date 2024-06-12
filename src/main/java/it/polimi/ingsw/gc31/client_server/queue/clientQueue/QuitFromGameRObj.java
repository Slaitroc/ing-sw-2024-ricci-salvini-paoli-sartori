package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class QuitFromGameRObj extends ClientQueueObject {

    int id;

    public QuitFromGameRObj(int id) {
        this.id = id;
    }

    @Override
    public void execute(UI ui) {
        ui.show_quitFromGame(id);
    }

}
