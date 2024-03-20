package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

public class Board {

    private Deck<PlayableCard> goldDeck;
    private Deck<PlayableCard> resourceDeck;
    private Deck<ObjectiveCard> objectiveDeck;

    public Board() {
        goldDeck = new Deck<>(CardType.GOLD);
        resourceDeck = new Deck<>(CardType.RESOURCE);
        objectiveDeck = new Deck<>(CardType.OBJECTIVE);
    }

    public Deck getDeck(CardType x) {
        switch (x) {
            case GOLD:
                return goldDeck;
            case RESOURCE:
                return resourceDeck;
            case OBJECTIVE:
                return objectiveDeck;
            default:
                return null;
        }
    }

}
