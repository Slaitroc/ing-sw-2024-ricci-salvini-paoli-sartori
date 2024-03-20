package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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

public class Deck <T> {
    // TODO cambiare implementazione con queue
    private Queue<T> deck;
    private T card1;
    private T card2;
    // TODO forse da mettere statiche da qualche altra parte
    private final String dirImgGoldCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/GoldCard.json";
    private final String dirImgResourceCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ResourceCard.json";
    private final String dirImgStarterCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/StarterCard.json";
    private final String dirImgObjectiveCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ObjectiveCard.json";

    public Deck(CardType cardType) {
        List<T> tempDeck = new ArrayList<>();
        FileReader fileReader = null;
        // classe delle carte che formeranno il deck
        Type type = null;

        this.deck = new ArrayDeque<>();
        try {
            switch (cardType) {
                case GOLD:
                    fileReader = new FileReader(dirImgGoldCard);
                    type = GoldCard.class;
                    break;
                case RESOURCE:
                    fileReader = new FileReader(dirImgResourceCard);
                    type = ResourceCard.class;
                    break;
                case STARTER:
                    fileReader = new FileReader(dirImgStarterCard);
                    type = StarterCard.class;
                    break;
                case OBJECTIVE:
                    fileReader = new FileReader(dirImgObjectiveCard);
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

                //create GsonBuilder and add typeAdapter necessary
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(GoldCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(ResourceCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(StarterCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(CardFront.class, new FrontClassAdapter())
                        .registerTypeAdapter(CardBack.class, new BackClassAdapter())
                        .registerTypeAdapter(Objective.class, new ObjectiveAdapter())
                        .registerTypeAdapter(Resources.class, new ListResourcesEnumAdapter())
                        .registerTypeAdapter(new TypeToken<Map<Resources, Integer>>(){}.getType(), new MapRequirementsAdapter())
                        .create();

                // pars every element
                for (JsonElement element: jsonArray) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();

                        // deserialize a single element and add it to the deck
                        T res = gson.fromJson(jsonObject, type);
                        tempDeck.add((T) res);
                    }
                }

                //TODO, tradurre: Mischio le carte
                Collections.shuffle(tempDeck);
                for (T card: tempDeck) {
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
        return deck.peek();
    }

    public Card getCard1() {
        return null;
    }

    public Card getCard2() {
        /*
        Card ret = card2;
        card2 = draw();

         */
        return null;
    }
}
