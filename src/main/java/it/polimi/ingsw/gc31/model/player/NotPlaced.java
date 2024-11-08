package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.awt.*;

/**
 * This class represents the NotPlaced state of a Player.
 * In this state, the player has not yet placed a card on the play area.
 */
public class NotPlaced extends PlayerState {

    @Override
    public void chooseSecretObjective(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(Deck<PlayableCard> deck, Deck<PlayableCard> substituteDeck, Player player, int index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void play(Point point, Player player) throws IllegalPlaceCardException {
        PlayableCard card = player.getSelectedCard();
        player.score += player.getPlayArea().place(card, point);
        player.hand.remove(card);
        player.setInGameState(new Placed());
    }

    @Override
    public void playStarter(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public String stateInfo() {
        return "notplaced";
    }
}
