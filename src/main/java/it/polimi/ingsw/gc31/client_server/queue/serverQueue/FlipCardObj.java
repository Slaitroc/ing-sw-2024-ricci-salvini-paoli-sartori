package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class FlipCardObj implements ServerQueueObject {

    Player player;

    public FlipCardObj(Player player) {
        this.player = player;
    }

//    @Override
//    public void execute() {
//        player.getSelectedCard().changeSide();
//    }

    @Override
    public void execute(GameController gameController) {

    }

    @Override
    public void execute(Controller controller) {

    }

}
