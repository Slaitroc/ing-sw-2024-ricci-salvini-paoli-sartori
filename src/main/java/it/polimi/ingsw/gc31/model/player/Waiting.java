package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.awt.*;

/**
 * This class represents the waiting status of the player in the match. Extend the {@link PlayerState} class. Defines which
 * actions are executable by the player. The only method available is the {@link Waiting#stateInfo()}.
 *
 * @see Player#infoState()
 */
public class Waiting extends PlayerState {

    @Override
    public void chooseSecretObjective(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(Deck<PlayableCard> deck, Deck<PlayableCard> subsistuteDeck, Player player, int index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
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
        return "waiting";
    }

}
