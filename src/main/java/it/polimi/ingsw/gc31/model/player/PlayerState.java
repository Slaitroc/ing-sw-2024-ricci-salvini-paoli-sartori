package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.awt.*;

/**
 * This abstract class represents the state of a player.
 * It defines the operations that a player can perform in a certain state.
 */
public abstract class PlayerState {

    /**
     * Adds an objective card to the player.
     *
     * @param card   the objective card to add.
     * @param player the player to add the card to.
     * @throws IllegalStateOperationException if the operation is not allowed in the
     *                                        current state.
     */
    public abstract void chooseSecretObjective(ObjectiveCard card, Player player) throws IllegalStateOperationException;

    /**
     * Adds a card to the player's hand.
     *
     * @param deck   the deck to draw from.
     * @param player the player to add the card to.
     * @throws IllegalStateOperationException if the operation is not allowed in the
     *                                        current state.
     * @throws FullHandException              if the player’s hand is full.
     * @throws InvalidCardDraw                if the card cannot be drawn.
     */
    public abstract void addToHand(Deck<PlayableCard> deck, Deck<PlayableCard> substituteDeck, Player player, int index)
            throws IllegalStateOperationException, FullHandException, InvalidCardDraw;

    /**
     * Plays a card in the designated player's PlayArea.
     *
     * @param point  the point to play the card at.
     * @param player the player to play the card.
     * @throws IllegalStateOperationException if the operation is not allowed in the
     *                                        current state.
     */
    public abstract void play(Point point, Player player) throws IllegalStateOperationException, IllegalPlaceCardException;

    /**
     * Plays the designated Player's starter card in his PlayArea.
     *
     * @param player the player to play the card for.
     * @throws IllegalStateOperationException if the operation is not allowed in the
     *                                        current state.
     */
    public abstract void playStarter(Player player)
            throws IllegalStateOperationException, ObjectiveCardNotChosenException;

    public abstract String stateInfo();
    // Notice: Intellij gives me a warning if I copy the same code multiple times

    /**
     * Executes the addToHand operation.
     * This method is common to all states and therefore is implemented in this
     * class.
     *
     * @param deck   deck to draw from.
     * @param player the player to add the card to.
     * @throws NullPointerException if the card is null.
     * @throws FullHandException    if the player's hand is full.
     */
    public void executeAddToHand(Deck<PlayableCard> deck, Deck<PlayableCard> subsistuteDeck, Player player, int index) throws NullPointerException, FullHandException {
        if (player.hand.size() > 2) {
            throw new FullHandException();
        }
        try {
            if (index == 0)
                player.hand.add(deck.draw());
            else if (index == 1)
                player.hand.add(deck.getCard1());
            else if (index == 2)
                player.hand.add(deck.getCard2());
        } catch (NullPointerException e) {
            System.out.println("There was a problem adding card in hand (is card null?)");
            e.getStackTrace();
        }

        if (deck.isEmpty()) {
            deck.replaceDeck(subsistuteDeck.getQueueDeck());
        }
        // TODO aggiungere qualcosa quando tutti e due i deck sono vuoti?
    }
}
