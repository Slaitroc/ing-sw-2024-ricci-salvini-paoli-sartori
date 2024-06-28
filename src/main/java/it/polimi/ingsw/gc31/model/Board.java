package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

import java.util.LinkedHashMap;

/**
 * This class represents the board of the game. It contains the decks of cards
 * and the score of the players.
 */
public class Board {

    /**
     * The gold deck of the game
     */
    private final Deck<PlayableCard> goldDeck;
    /**
     * The resource deck of the game
     */
    private final Deck<PlayableCard> resourceDeck;
    /**
     * The objective deck of the game
     */
    private final Deck<ObjectiveCard> objectiveDeck;
    /**
     * The starter deck of the game
     */
    private final Deck<PlayableCard> starterDeck;
    /**
     * The score of the players
     */
    private final LinkedHashMap<String, Integer> playersScore;

    /**
     * Constructor of the class
     * It initializes the decks of cards and the score of the players
     */
    public Board() {
        goldDeck = new Deck<>(CardType.GOLD);
        resourceDeck = new Deck<>(CardType.RESOURCE);
        objectiveDeck = new Deck<>(CardType.OBJECTIVE);
        starterDeck = new Deck<>(CardType.STARTER);
        playersScore = new LinkedHashMap<>();
    }

    /**
     * @param username the username of the player
     * @param integer  the score of the player
     */
    public void updateScore(String username, int integer) {
        playersScore.put(username, integer);
    }

    /**
     * @return the score of the players
     */
    public LinkedHashMap<String, Integer> getPlayersScore() {
        return playersScore;
    }

    /**
     * @return the deck of gold cards
     */
    public Deck<PlayableCard> getDeckGold() {
        return goldDeck;
    }

    /**
     * @return the deck of starter cards
     */
    public Deck<PlayableCard> getDeckStarter() {
        return starterDeck;
    }

    /**
     * @return the deck of resource cards
     */
    public Deck<PlayableCard> getDeckResource() {
        return resourceDeck;
    }

    /**
     * @return the deck of objective cards
     */
    public Deck<ObjectiveCard> getDeckObjective() {
        return objectiveDeck;
    }

    // @Override
    // public Board deepCopy() {
    // Board clone = new Board();
    // clone.goldDeck = this.goldDeck.deepCopy();
    // clone.resourceDeck = this.resourceDeck.deepCopy();
    // clone.starterDeck = this.starterDeck.deepCopy();
    // clone.objectiveDeck = this.objectiveDeck.deepCopy();
    // return null;
    // }

}
