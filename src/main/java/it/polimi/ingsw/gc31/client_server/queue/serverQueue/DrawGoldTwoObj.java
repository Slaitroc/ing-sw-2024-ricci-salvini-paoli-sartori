package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class DrawGoldTwoObj extends ServerQueueObject {
    private final String username;

    public DrawGoldTwoObj(String username) {
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {
//        if (player.drawGoldCard2()) {
//            model.endTurn();
//            // System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN GOLD
//            // CARD2.");
//        }
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
