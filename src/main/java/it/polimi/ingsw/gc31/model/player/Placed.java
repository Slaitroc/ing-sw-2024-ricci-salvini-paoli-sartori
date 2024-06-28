package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.awt.*;

/**
 * This class represents the Placed state of a player.
 */
public class Placed extends PlayerState {

    @Override
    public void chooseSecretObjective(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(Deck<PlayableCard> deck, Deck<PlayableCard> substituteDeck, Player player, int index)
            throws FullHandException, NullPointerException {
        executeAddToHand(deck, substituteDeck, player, index);
        player.setInGameState(new Waiting());
    }

    @Override
    public void play(Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public String stateInfo() {
        return "placed";
    }
}
