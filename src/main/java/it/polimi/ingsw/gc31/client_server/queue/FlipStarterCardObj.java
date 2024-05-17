package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.player.Player;

public class FlipStarterCardObj implements QueueObject {

    Player player;

    public FlipStarterCardObj(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        if(player.getPlayArea().getPlacedCards().isEmpty()){
            player.getStarterCard().changeSide();
        }
        else System.out.println("Cannot flip already placed Starter Card");
    }
}