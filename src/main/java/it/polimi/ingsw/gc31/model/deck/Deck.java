package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import it.polimi.ingsw.gc31.utility.DeepCopy;
import it.polimi.ingsw.gc31.utility.gsonUtility.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class Deck<T extends Card> implements Cloneable, DeepCopy<Deck> {
    // TODO cambiare implementazione con queue
    private Queue<T> deck;
    private T card1;
    private T card2;
    private CardType deckCardType; // colpa di Slaitroc
    // TODO forse da mettere statiche da qualche altra parte
    private final String dirJsonGoldCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/GoldCard.json";
    private final String dirJsonResourceCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ResourceCard.json";
    private final String dirJsonStarterCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/StarterCard.json";
    private final String dirJsonObjectiveCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ObjectiveCard.json";

    private Deck() {
        deck = new ArrayDeque<>();

    }

    public Deck(CardType cardType) {
        deckCardType = cardType; // colpa di Slaitroc
        List<T> tempDeck = new ArrayList<>();
        FileReader fileReader = null;
        // classe delle carte che formeranno il deck
        Type type = null;

        this.deck = new ArrayDeque<>();
        try {
            switch (cardType) {
                case GOLD:
                    fileReader = new FileReader(dirJsonGoldCard);
                    type = GoldCard.class;
                    break;
                case RESOURCE:
                    fileReader = new FileReader(dirJsonResourceCard);
                    type = ResourceCard.class;
                    break;
                case STARTER:
                    fileReader = new FileReader(dirJsonStarterCard);
                    type = StarterCard.class;
                    break;
                case OBJECTIVE:
                    fileReader = new FileReader(dirJsonObjectiveCard);
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

    public T draw() {
        return deck.poll();
    }

    public void refill() {
        if (card1 == null) {
            card1 = this.draw();
            card1.changeSide();
        }
        if (card2 == null) {
            card2 = this.draw();
            card2.changeSide();
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

    @Override
    public Deck<T> deepCopy() {
        Deck<T> clone = new Deck<>();
        clone.deckCardType = this.deckCardType;
        for (T card : this.deck) {
            clone.deck.add(card);
        }
        clone.card1 = this.card1; // TODO chiedi a cri una deep copy di card utilizzando i suoi metodi di deepcopy
                                  // di front e back
        clone.card2 = this.card2; // WARN ricordati
        return clone;
    }

    public Queue<T> getQueue() { // FIX
        return deck;
    }
}
