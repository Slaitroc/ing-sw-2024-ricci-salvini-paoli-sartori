package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.player.Player;

public class DrawResOneObj implements QueueObject {
    private Player player;

    public DrawResOneObj(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.drawResourceCard1();
    }
}
