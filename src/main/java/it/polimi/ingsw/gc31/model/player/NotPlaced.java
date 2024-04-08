package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.awt.*;

public class NotPlaced extends PlayerState {

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(PlayableCard card, Player player, Boolean byDeck) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void moveCardInHand(Player player) {
        executeMoveCardInHand(player);
    }

    @Override
    public void play(Point point, Player player) {
        PlayableCard card = player.getSelectedCard();
        player.score += player.getPlayArea().place(card, point);
        player.hand.remove(card);
        player.setInGameState(new Placed());
    }

    @Override
    public void playStarter(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
}
