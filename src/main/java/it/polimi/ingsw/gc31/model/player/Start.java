package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.exceptions.FullHandException;

public class Start extends PlayerState {

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) {
        player.objectiveCard = card;
    }

    @Override
    public void addToHand(PlayableCard card, Player player) throws NullPointerException, FullHandException{
        executeAddToHand(card, player);
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
    public void playStarter(PlayableCard card, Player player){
        player.getPlayArea().placeStarter(card);
    }

}
