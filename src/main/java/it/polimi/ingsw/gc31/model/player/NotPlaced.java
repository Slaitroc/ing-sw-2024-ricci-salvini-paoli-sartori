package it.polimi.ingsw.gc31.model.player;

public class NotPlaced extends PlayerState {
    @Override
    public int getScore(Player player) {
        return player.score;
    }
    
}
