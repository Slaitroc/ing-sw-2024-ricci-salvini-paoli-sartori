package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;

import it.polimi.ingsw.gc31.client_server.listeners.Observable;
import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.utility.DV;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

@SuppressWarnings("rawtypes")
public class Deck<T extends Card> extends Observable<Deck> {

    // TODO cambiare implementazione con queue
    private Queue<T> deck;
    private T card1;
    private T card2;

    public Deck(CardType cardType) {
        List<T> tempDeck = new ArrayList<>();
        this.card1 = null;
        this.card2 = null;
        Reader fileReader = null;
        InputStream is = null;
        // classe delle carte che formeranno il deck
        Type type = null;

        this.deck = new ArrayDeque<>();
        try {
            switch (cardType) {
                case GOLD:
                    is = getClass().getResourceAsStream(DV.DIRJsonGoldCard);
                    fileReader = new InputStreamReader(is);
                    type = GoldCard.class;
                    break;
                case RESOURCE:
                    is = getClass().getResourceAsStream(DV.DIRJsonResourceCard);
                    fileReader = new InputStreamReader(is);
                    type = ResourceCard.class;
                    break;
                case STARTER:
                    is = getClass().getResourceAsStream(DV.DIRJsonStarterCard);
                    fileReader = new InputStreamReader(is);
                    type = StarterCard.class;
                    break;
                case OBJECTIVE:
                    is = getClass().getResourceAsStream(DV.DIRJsonObjectiveCard);
                    fileReader = new InputStreamReader(is);
                    type = ObjectiveCard.class;
                    break;
                default:
                    // TODO aggiungere default
                    break;
            }

            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            fileReader.close();

            // TODO aggiungere else con exception?
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // pars every element
                for (JsonElement element : jsonArray) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();

                        // deserialize a single element and add it to the deck
                        T res = gsonTranslater.fromJson(jsonObject, type);
                        tempDeck.add((T) res);
                    }
                }

                // TODO, tradurre: Mischio le carte
                Collections.shuffle(tempDeck);
                for (T card : tempDeck) {
                    deck.add(card);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void replaceDeck(Queue<T> deck) {
        this.deck = deck;
        refill();
    }

    public Queue<T> getQueueDeck() {
        return deck;
    }

    public T draw() throws EmptyDeckException {
        if (deck.isEmpty())
            throw new EmptyDeckException();
        T card = deck.poll();
        notifyListeners(this);
        return card;
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
        notifyListeners(this);
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

    public void flipCard1() {
        this.card1.changeSide();
    }

    public void flipCard2() {
        this.card2.changeSide();
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
}
