package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.InvalidCardDraw;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

public class Start extends PlayerState {

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) {
        player.setObjectiveCard(card);
    }

    @Override
    public void addToHand(PlayableCard card, Player player, Boolean byDeck) throws FullHandException, InvalidCardDraw {
        if (!byDeck) {
            throw new InvalidCardDraw();
        }
        if (player.hand.size() > 3) {
            throw new FullHandException();
        }
        executeAddToHand(card, player);
    }

    @Override
    public void moveCardInHand(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void play(Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(Player player) {
        player.getPlayArea().placeStarter(player.getStarterCard());
    }

}
