package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawResObj implements ServerQueueObject {

    private Player player;
    private GameModel model;

    public DrawResObj(Player player, GameModel model) {
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
//    public void execute(Player player, GameModel model) {
//
//    }

//    @Override
//    public void execute() {
//        if (player.drawResource()) {
//            model.endTurn();
//            // System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN A
//            // RESOURCE CARD.");
//        }
//    }

}
