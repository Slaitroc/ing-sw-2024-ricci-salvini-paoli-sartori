package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class SelectCardObj implements ServerQueueObject {

    Player player;
    int i;

    public SelectCardObj(Player player, int i) {
        this.player = player;
    }

//    @Override
//    public void execute() {
//        player.setSelectedCard(i);
//    }

    @Override
    public void execute(GameController gameController) {

    }

    @Override
    public void execute(Controller controller) {

    }

}
