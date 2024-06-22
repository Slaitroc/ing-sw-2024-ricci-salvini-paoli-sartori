package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.utility.DV;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * This class represents a deck of cards of type T.
 *
 * @param <T> the type of cards in the deck, must extend the Card interface
 */
public class Deck<T extends Card>{
    private Queue<T> deck;
    private T card1;
    private T card2;

    public Deck(CardType cardType) {
        List<T> tempDeck = new ArrayList<>();
        this.card1 = null;
        this.card2 = null;
        Reader fileReader;
        InputStream is;
        Type type;

        this.deck = new ArrayDeque<>();
        try {
            switch (cardType) {
                case GOLD:
                    is = getClass().getResourceAsStream(DV.DIRJsonGoldCard);
                    type = GoldCard.class;
                    break;
                case RESOURCE:
                    is = getClass().getResourceAsStream(DV.DIRJsonResourceCard);
                    type = ResourceCard.class;
                    break;
                case STARTER:
                    is = getClass().getResourceAsStream(DV.DIRJsonStarterCard);
                    type = StarterCard.class;
                    break;
                case OBJECTIVE:
                    is = getClass().getResourceAsStream(DV.DIRJsonObjectiveCard);
                    type = ObjectiveCard.class;
                    break;
                default:
                    throw new RuntimeException("Unknown card type");
            }
            if (is != null) {
                fileReader = new InputStreamReader(is);
            } else {
                throw new RuntimeException();
            }

            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            fileReader.close();

            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // pars every element
                for (JsonElement element : jsonArray) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();

                        // deserialize a single element and add it to the deck
                        T res = gsonTranslater.fromJson(jsonObject, type);
                        tempDeck.add(res);
                    }
                }

                Collections.shuffle(tempDeck);
                deck.addAll(tempDeck);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void replaceDeck(Queue<T> deck) {
        this.deck = deck;
    }

    public Queue<T> getQueueDeck() {
        return deck;
    }

    public T draw() throws EmptyDeckException {
        if (deck.isEmpty())
            throw new EmptyDeckException();
//        T card = deck.poll();
//        card.changeSide();
        return deck.poll();
    }

    public void refill() {
        if (card1 == null) {
            try {
                card1 = this.draw();
                card1.changeSide();
            } catch (EmptyDeckException e) {
                card1 = null;
            }
        }
        if (card2 == null) {
            try {
                card2 = this.draw();
                card2.changeSide();
            } catch (EmptyDeckException e) {
                card1 = null;
            }
        }
    }

    public T getCard1() {
        T retCard = card1;
        card1 = null;
        refill();
        return retCard;
    }

    public T getCard2() {
        T retCard = card2;
        card2 = null;
        refill();
        return retCard;
    }

    public T peekCard() {
        return this.deck.peek();
    }

    public T peekCard1() {
        return this.card1;
    }

    public T peekCard2() {
        return this.card2;
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
}
