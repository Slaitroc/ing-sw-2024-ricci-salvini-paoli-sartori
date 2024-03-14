package it.polimi.ingsw.gc31.Model.Deck;

import it.polimi.ingsw.gc31.Model.Card.Card;
import it.polimi.ingsw.gc31.Model.Card.PlayableCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Deck {
    // TODO cambiare implementazione con queue
    private List<Card> deck;
    private Card card1;
    private Card card2;
    public Deck(List<Card> deck) {
        // TODO forse implementare deep copy
        this.deck = deck;
    }
    public Card draw() {
        return deck.remove(0);
    }
    public Card getCard1() {
        Card ret = card1;
        card1 = draw();
        return ret;
    }
    public Card getCard2() {
        Card ret = card2;
        card2 = draw();
        return ret;
    }
}
