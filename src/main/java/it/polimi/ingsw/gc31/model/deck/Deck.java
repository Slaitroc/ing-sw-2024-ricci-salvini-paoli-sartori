package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import it.polimi.ingsw.gc31.utility.gsonUtility.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class Deck<T extends Card> /* implements DeepCopy<Deck<T>> */ {
    // TODO cambiare implementazione con queue
    private Queue<T> deck;
    private T card1;
    private T card2;

    public Deck(CardType cardType) {
        List<T> tempDeck = new ArrayList<>();
        this.card1 = null;
        this.card2 = null;
        FileReader fileReader = null;
        // classe delle carte che formeranno il deck
        Type type = null;

        this.deck = new ArrayDeque<>();
        try {
            switch (cardType) {
                case GOLD:
                    fileReader = new FileReader(DefaultValues.DIRJsonGoldCard);
                    type = GoldCard.class;
                    break;
                case RESOURCE:
                    fileReader = new FileReader(DefaultValues.DIRJsonResourceCard);
                    type = ResourceCard.class;
                    break;
                case STARTER:
                    fileReader = new FileReader(DefaultValues.DIRJsonStarterCard);
                    type = StarterCard.class;
                    break;
                case OBJECTIVE:
                    fileReader = new FileReader(DefaultValues.DIRJsonObjectiveCard);
                    type = ObjectiveCard.class;
                    break;
                default:
                    // TODO aggiungere default
                    break;
            }

            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            fileReader.close();

            // TODO aggiungere else con expection?
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // create GsonBuilder and add typeAdapter necessary
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(GoldCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(ResourceCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(StarterCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(ObjectiveCard.class, new ObjectiveCardAdapter())
                        .registerTypeAdapter(CardFront.class, new FrontClassAdapter())
                        .registerTypeAdapter(CardBack.class, new BackClassAdapter())
                        .registerTypeAdapter(Objective.class, new ObjectiveAdapter())
                        .registerTypeAdapter(Resources.class, new ListResourcesEnumAdapter())
                        .registerTypeAdapter(new TypeToken<Map<Resources, Integer>>() {
                        }.getType(), new MapRequirementsAdapter())
                        .create();

                // pars every element
                for (JsonElement element : jsonArray) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();

                        // deserialize a single element and add it to the deck
                        T res = gson.fromJson(jsonObject, type);
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
        if (deck.isEmpty()) throw new EmptyDeckException();
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

    // @Override
    // public Deck<T> deepCopy() {
    // Deck<T> clone = new Deck<>();
    // for (T card : this.deck) {
    // clone.deck.add(card);
    // }
    // // Cast a (T) controllato perchè T è sottoclasse di deck
    // clone.card1 = (T) this.card1.deepCopy(); // TODO chiedi a cri una deep copy
    // di card utilizzando i suoi metodi di
    // // deepcopy
    // clone.card2 = (T) this.card2.deepCopy();
    // return clone;
    // }

    // public Queue<T> getQueue() { // FIX
    // return deck;
    // }
}
