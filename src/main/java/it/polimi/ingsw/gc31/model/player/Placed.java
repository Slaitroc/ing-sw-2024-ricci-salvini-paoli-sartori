package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;

public class Placed extends PlayerState {

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(PlayableCard card, Player player, Boolean byDeck)
            throws FullHandException, NullPointerException {
        executeAddToHand(card, player);
        player.setInGameState(new Waiting());
        //System.out.println("PLAYER: " + player.getUsername() + " HAS JUST DRAWN A CARD, IS NOW WAITING.");
    }

    @Override
    public void moveCardInHand(Player player) {
        executeMoveCardInHand(player);
    }

    @Override
    public void play(Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
}
