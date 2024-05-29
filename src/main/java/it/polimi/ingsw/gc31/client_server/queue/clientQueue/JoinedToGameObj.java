package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class JoinedToGameObj extends ClientQueueObject {

    int idGame;
    int maxNumberOfPlayers;

    public JoinedToGameObj(int idGame, int maxNumberOfPlayers) {
        this.idGame = idGame;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    @Override
    public void execute(UI ui) {
        ui.show_joinedToGame(idGame, maxNumberOfPlayers);
    }

}
