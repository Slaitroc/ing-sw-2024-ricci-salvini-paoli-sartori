package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawResOneObj implements QueueObject {
    private Player player;
    private GameModel model;

    public DrawResOneObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute() {
        if (player.drawResourceCard1()) {
        model.endTurn();
        }
    }
}
