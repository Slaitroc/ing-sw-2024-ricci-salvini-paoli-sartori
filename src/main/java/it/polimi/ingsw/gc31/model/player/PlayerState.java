package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.OurScanner;
import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.InvalidCardDraw;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

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
     * @throws IllegalStateOperationException if the operation is not allowed in the current state.
     */
    public abstract void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException;

    /**
     * Adds a card to the player's hand.
     *
     * @param card   the card to add.
     * @param player the player to add the card to.
     * @param byDeck whether the card is drawn from the deck or not.
     * @throws IllegalStateOperationException if the operation is not allowed in the current state.
     * @throws FullHandException              if the player’s hand is full.
     * @throws InvalidCardDraw                if the card cannot be drawn.
     */
    public abstract void addToHand(PlayableCard card, Player player, Boolean byDeck)
            throws IllegalStateOperationException, FullHandException, InvalidCardDraw;

    /**
     * Moves a card in the player's hand.
     *
     * @param player the player to move the card for.
     * @throws IllegalStateOperationException if the operation is not allowed in the current state.
     */
    public abstract void moveCardInHand(Player player) throws IllegalStateOperationException;

    /**
     * Plays a card in the designated player's PlayArea.
     *
     * @param point  the point to play the card at.
     * @param player the player to play the card.
     * @throws IllegalStateOperationException if the operation is not allowed in the current state.
     */
    public abstract void play(Point point, Player player) throws IllegalStateOperationException;

    /**
     * Plays the designated Player's starter card in his PlayArea.
     *
     * @param player the player to play the card for.
     * @throws IllegalStateOperationException if the operation is not allowed in the current state.
     */
    public abstract void playStarter(Player player) throws IllegalStateOperationException, ObjectiveCardNotChosenException;
    /*
     * public void drawResource(Player player) throws
     * IllegalStateOperationException;
     *
     * public void drawResourceCard1(Player player) throws
     * IllegalStateOperationException;
     *
     * public void drawResourceCard2(Player player) throws
     * IllegalStateOperationException;
     *
     * public void drawGold(Player player) throws IllegalStateOperationException;
     *
     * public void drawGoldCard1(Player player) throws
     * IllegalStateOperationException;
     *
     * public void drawGoldCard2(Player player) throws
     * IllegalStateOperationException;
     */

    // Notice: Intellij gives me a warning if I copy the same code multiple times

    /**
     * Executes the move card in hand operation.
     * This method is common to all states and therefore is implemented in this class.
     *
     * @param player the player to move the card for.
     */
    public void executeMoveCardInHand(Player player) {
        System.out.println("Insert position of the first card [1-3]: ");
        int cardPosition1 = OurScanner.scanner.nextInt();
        PlayableCard card1 = player.hand.get(cardPosition1);

        System.out.println("Insert position of the second card [1-3]: ");
        int cardPosition2 = OurScanner.scanner.nextInt();
        PlayableCard card2 = player.hand.get(cardPosition2);

        player.hand.set(cardPosition1, card2);
        player.hand.set(cardPosition2, card1);
        System.out.println("New Hand disposition: " + player.hand);
    }

    /**
     * Executes the addToHand operation.
     * This method is common to all states and therefore is implemented in this class.
     *
     * @param card   the card to add.
     * @param player the player to add the card to.
     * @throws NullPointerException if the card is null.
     * @throws FullHandException    if the player's hand is full.
     */
    public void executeAddToHand(PlayableCard card, Player player) throws NullPointerException, FullHandException {
        if (player.hand.size() > 2) {
            throw new FullHandException();
        }
        try {
            player.hand.add(card);
        } catch (NullPointerException e) {
            System.out.println("There was a problem adding card in hand (is card null?)");
            e.getStackTrace();
        }
    }

}
