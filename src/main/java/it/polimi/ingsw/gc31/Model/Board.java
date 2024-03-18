package it.polimi.ingsw.gc31.Model;

import it.polimi.ingsw.gc31.Model.Card.Card;
import it.polimi.ingsw.gc31.Model.Deck.Deck;
import it.polimi.ingsw.gc31.Model.Enum.CardType;


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
