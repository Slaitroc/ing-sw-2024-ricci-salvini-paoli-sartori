package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;


public class Board {

    private Deck goldDeck;
    private Deck resourceDeck;
    private Deck objectiveDeck;
    
    public Board(){
        goldDeck = new Deck(CardType.GOLD);
        resourceDeck = new Deck(CardType.RESOURCE);
        objectiveDeck = new Deck(CardType.OBJECTIVE);
    }
    
    
}
