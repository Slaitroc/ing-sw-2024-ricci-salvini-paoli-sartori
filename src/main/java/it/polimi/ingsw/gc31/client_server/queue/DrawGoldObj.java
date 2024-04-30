package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawGoldObj implements QueueObject {

    private Player player;
    private GameModel model;

    public DrawGoldObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute() {
        if (player.drawGold()) {
            model.endTurn();
            //System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN A GOLD CARD.");
        }
    }

}
