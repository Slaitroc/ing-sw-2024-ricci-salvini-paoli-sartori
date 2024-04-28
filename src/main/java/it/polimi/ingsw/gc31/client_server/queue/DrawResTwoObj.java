package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.player.Player;

public class DrawResTwoObj implements QueueObject {

    private Player player;

    public DrawResTwoObj(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.drawResourceCard2();
    }

}
