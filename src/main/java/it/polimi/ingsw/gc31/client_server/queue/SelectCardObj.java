package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.player.Player;

public class SelectCardObj implements QueueObject {

    Player player;
    int i;

    public SelectCardObj(Player player, int i) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.setSelectedCard(i);
    }
}