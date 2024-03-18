package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.model.Card.Card;
import it.polimi.ingsw.gc31.model.Deck.Deck;
import it.polimi.ingsw.gc31.model.Enum.CardType;


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
