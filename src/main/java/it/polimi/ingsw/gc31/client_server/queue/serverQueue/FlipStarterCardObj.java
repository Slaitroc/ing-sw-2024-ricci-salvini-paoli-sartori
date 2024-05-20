package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.player.Player;

public class FlipStarterCardObj extends ServerQueueObject {

    Player player;

    public FlipStarterCardObj(Player player) {
        this.player = player;
    }

    @Override
    public void execute(GameController gameController) {
        if (player.getPlayArea().getPlacedCards().isEmpty()) {
            player.getStarterCard().changeSide();
        } else
            System.out.println("Cannot flip already placed Starter Card");
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