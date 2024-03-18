package it.polimi.ingsw.gc31.model.deck;

import it.polimi.ingsw.gc31.model.Card.Card;
import it.polimi.ingsw.gc31.model.Card.PlayableCard;
import it.polimi.ingsw.gc31.model.Enum.CardType;rdType;
import it.polimi.ingsw.gc31.utility.file.FileUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.google.gson.Gson;

public class Deck {
    // TODO cambiare implementazione con queue
    private List<Card> deck;
    private Card card1;
    private Card card2;

    public Deck(List<Card> deck) {
        // TODO forse implementare deep copy
        this.deck = deck;
    }

    public Deck(CardType x) {
        String cardJson; //NOTE debug 
        switch (x) {
            case GOLD:
                cardJson = deserializaGoldDeck();
                System.out.println("Gold Deck letto");
                break;
            case RESOURCE:
                cardJson = deserializaResourceDeck();
                System.out.println("Resource Deck letto");
                break;
            case STARTER:
                cardJson = deserializaStarterDeck();
                System.out.println("Starter Deck letto");
                break;
            case OBJECTIVE:
                cardJson = deserializaObjectiveDeck();
                System.out.println("Objective Deck letto");
                break;
            default:
            cardJson = null;
            System.out.println("nessun file trovato");
                break;
        }

    }

    private String deserializaGoldDeck() {
        return FileUtility.fileToString("src/main/resources/it/polimi/ingsw/gc31/CardsJson/GoldCard.json");
    }
    private String deserializaResourceDeck() {
        return FileUtility.fileToString("src/main/resources/it/polimi/ingsw/gc31/CardsJson/ResourceCard.json");
    }
    private String deserializaStarterDeck() {
        return FileUtility.fileToString("src/main/resources/it/polimi/ingsw/gc31/CardsJson/StarterCard.json");
    }
    private String deserializaObjectiveDeck() {
        return FileUtility.fileToString("src/main/resources/it/polimi/ingsw/gc31/CardsJson/ObjectiveCard.json");
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
