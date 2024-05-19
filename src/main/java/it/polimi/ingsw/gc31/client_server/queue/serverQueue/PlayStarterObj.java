package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class PlayStarterObj implements ServerQueueObject {

    Player player;
    GameModel model;

    public PlayStarterObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute(GameController gameController) {

    }

    @Override
    public void execute(Controller controller) {

    }

//    @Override
//    public void execute() {
//        player.playStarter();
//        model.checkStartGame();
//    }
}
