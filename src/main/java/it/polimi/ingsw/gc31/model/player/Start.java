package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.InvalidCardDraw;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.awt.*;

/**
 * Represents the status of ready of a user. Defines which actions are executable at that moment for the user. Extends the
 * {@link PlayerState} class. The only non-executable method is the {@link Start#play(Point, Player)} method.
 */
public class Start extends PlayerState {
    private boolean objectiveChosen = false;

    @Override
    public void chooseSecretObjective(ObjectiveCard card, Player player) {
        if (!objectiveChosen) {
            player.setObjectiveCard(card);
            objectiveChosen = true;
        }
    }

    @Override
    public void addToHand(Deck<PlayableCard> deck, Deck<PlayableCard> subsistuteDeck, Player player, int index) throws FullHandException, InvalidCardDraw {
        if (index != 0) {
            throw new InvalidCardDraw();
        }
        if (player.hand.size() > 3) {
            throw new FullHandException();
        }
        executeAddToHand(deck, subsistuteDeck, player, index);
    }

    @Override
    public void play(Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(Player player) throws ObjectiveCardNotChosenException {
        if (objectiveChosen) {
            player.getPlayArea().placeStarter(player.getStarterCard());
            // the players are all put in the waiting state, waiting for all the other
            // players to finish their setup phase
            player.setInGameState(new Waiting());
        } else {
            throw new ObjectiveCardNotChosenException();
        }
    }

    @Override
    public String stateInfo() {
        return "start";
    }

}
