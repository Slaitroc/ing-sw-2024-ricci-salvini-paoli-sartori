package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

public class Board implements Cloneable {

    private Deck<PlayableCard> goldDeck;
    private Deck<PlayableCard> resourceDeck;
    private Deck<ObjectiveCard> objectiveDeck;
    private Deck<PlayableCard> starterDeck;

    public Board() {
        goldDeck = new Deck<>(CardType.GOLD);
        resourceDeck = new Deck<>(CardType.RESOURCE);
        objectiveDeck = new Deck<>(CardType.OBJECTIVE);
        starterDeck = new Deck<>(CardType.STARTER);
    }

    public Deck<PlayableCard> getDeckGold() {
        return goldDeck;
    }

    public Deck<PlayableCard> getDeckStarer() {
        return starterDeck;
    }

    public Deck<PlayableCard> getDeckResource() {
        return resourceDeck;
    }

    public Deck<ObjectiveCard> getDeckObjective() {
        return objectiveDeck;
    }

    // NOTE cloneable
    @Override
    public Board clone() {
        Board clone = new Board();
        clone.goldDeck = this.goldDeck.clone();
        clone.resourceDeck = this.resourceDeck.clone();
        clone.starterDeck = this.starterDeck.clone();
        clone.objectiveDeck = this.objectiveDeck.clone();
        return null;
    }

}
