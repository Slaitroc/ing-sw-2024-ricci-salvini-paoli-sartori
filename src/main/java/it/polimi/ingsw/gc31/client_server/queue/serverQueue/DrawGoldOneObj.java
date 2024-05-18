package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawGoldOneObj implements ServerQueueObject {

    private Player player;
    private GameModel model;

    public DrawGoldOneObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute() {
        if (player.drawGoldCard1()) {
            model.endTurn();
            // System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN GOLD
            // CARD1.");
        }
    }

}