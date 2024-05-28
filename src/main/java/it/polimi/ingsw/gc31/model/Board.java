package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.client_server.listeners.Observable;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

import java.util.LinkedHashMap;

public class Board extends Observable<LinkedHashMap<String, Integer>> {

    private final Deck<PlayableCard> goldDeck;
    private final Deck<PlayableCard> resourceDeck;
    private final Deck<ObjectiveCard> objectiveDeck;
    private final Deck<PlayableCard> starterDeck;
    private final LinkedHashMap<String, Integer> playersScore;

    public Board() {
        goldDeck = new Deck<>(CardType.GOLD);
        resourceDeck = new Deck<>(CardType.RESOURCE);
        objectiveDeck = new Deck<>(CardType.OBJECTIVE);
        starterDeck = new Deck<>(CardType.STARTER);
        playersScore = new LinkedHashMap<>();
    }
    public void updateScore(String username, int integer) {
        playersScore.put(username, integer);
        notifyListeners(playersScore);

    }

    public Deck<PlayableCard> getDeckGold() {
        return goldDeck;
    }

    public Deck<PlayableCard> getDeckStarter() {
        return starterDeck;
    }

    public Deck<PlayableCard> getDeckResource() {
        return resourceDeck;
    }

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
