package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

public class Start extends PlayerState {
    private static boolean isPlaying = false;

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) {

    }

    @Override
    public boolean addToHand(PlayableCard card) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addToHand'");
    }

    @Override
    public void moveCardInHand() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveCardInHand'");
    }

    @Override
    public void play(PlayableCard card, Point point) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    public PlayerState changeState() {
        if (!isPlaying)
            return new NotPlaced();
        else
            return new Waiting();
    }

}
