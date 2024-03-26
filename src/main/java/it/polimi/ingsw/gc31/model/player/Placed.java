package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.awt.*;

public class Placed extends PlayerState {


    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(PlayableCard card, Player player){
        try {
            player.hand.add(card);
            player.setInGameState(new Waiting());
        }
        catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void moveCardInHand(Player player){
        executeMoveCardInHand(player);
    }

    @Override
    public void play(PlayableCard card, Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
}
