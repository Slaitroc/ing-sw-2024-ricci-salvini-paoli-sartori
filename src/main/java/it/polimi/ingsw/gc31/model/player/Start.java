package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

public class Start extends PlayerState {
    private static boolean firstToPlay = true;

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) {
        player.objectiveCard = card;
    }

    @Override
    public void addToHand(PlayableCard card, Player player) {
        try {
            player.hand.add(card);
        }
        catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void moveCardInHand(Player player) throws IllegalStateOperationException{
        throw new IllegalStateOperationException();
    }

    @Override
    public void play(PlayableCard card, Point point, Player player) throws IllegalStateOperationException{
        throw new IllegalStateOperationException();
    }

    @Override
    public PlayerState changeState() {
        if (firstToPlay) {
            firstToPlay = false;
            return new NotPlaced();
        }
        else
            return new Waiting();
    }

}
