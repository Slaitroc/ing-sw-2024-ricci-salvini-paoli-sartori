package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawGoldOneObj extends ServerQueueObject {

    private Player player;
    private GameModel model;

    public DrawGoldOneObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute(GameController gameController) {
        if (player.drawGoldCard1()) {
            model.endTurn();
            // System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN GOLD
            // CARD1.");
        }
    }

    @Override
    public void execute(Controller controller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
