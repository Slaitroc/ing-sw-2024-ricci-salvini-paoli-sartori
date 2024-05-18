package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.model.player.Player;

public class FlipCardObj implements ServerQueueObject {

    Player player;

    public FlipCardObj(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.getSelectedCard().changeSide();
    }
}
