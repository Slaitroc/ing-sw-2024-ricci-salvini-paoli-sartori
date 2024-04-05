package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.player.Player;

import java.util.List;

public class PlayerController {
    final Player player;
    public PlayerController(Player player) {
        // TODO deep copy??
        this.player = player;
    }
    public void drawGold() {
        player.drawGold();
    }
    public List<PlayableCard> getHand() {
        return player.getHand();
    }
}
