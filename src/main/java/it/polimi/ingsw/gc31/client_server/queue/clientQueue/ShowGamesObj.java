package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.util.List;

import it.polimi.ingsw.gc31.view.UI;

public class ShowGamesObj extends ClientQueueObject {

    private final List<String> gameList;

    public ShowGamesObj(List<String> gameList) {
        this.gameList = gameList;
    }

    @Override
    public void execute(UI ui) {
        ui.show_listGame(gameList);
    }

}
