package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.player.Player;

public class DrawGoldTwoObj implements QueueObject {
    private Player player;

    public DrawGoldTwoObj(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.drawGoldCard2();
    }
}
