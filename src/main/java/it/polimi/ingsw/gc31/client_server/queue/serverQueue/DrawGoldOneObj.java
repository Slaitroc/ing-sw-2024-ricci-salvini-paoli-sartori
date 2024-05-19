package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawGoldOneObj implements ServerQueueObject {
    private final String username;

    public DrawGoldOneObj(Player player, GameModel model, String username) {
        this.username = username;
    }

//    @Override
//    public void execute() {
//        if (player.drawGoldCard1()) {
//            model.endTurn();
//            // System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN GOLD
//            // CARD1.");
//        }
//    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(Controller controller) {

    }

}
