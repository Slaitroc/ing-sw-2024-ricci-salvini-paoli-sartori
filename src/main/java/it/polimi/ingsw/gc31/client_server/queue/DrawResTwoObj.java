package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawResTwoObj implements QueueObject {

    private Player player;
    private GameModel model;

    public DrawResTwoObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute() {
        if (player.drawResourceCard2()) {
            model.endTurn();
            //System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN RESOURCE CARD2.");
        }
    }

}
