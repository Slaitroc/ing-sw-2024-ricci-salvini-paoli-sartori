package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class JoinedToGameObj extends ClientQueueObject {

    int idGame;

    public JoinedToGameObj(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public void execute(UI ui) {
        ui.show_joinedToGame(idGame);
    }

}
