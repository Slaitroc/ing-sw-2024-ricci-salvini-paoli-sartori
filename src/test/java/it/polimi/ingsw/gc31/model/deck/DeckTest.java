package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import it.polimi.ingsw.gc31.utility.gsonUtility.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class DeckTest {

    @Test
    void draw() {
        try {
            List<Card> deck = new ArrayList<>();

            FileReader fileReader = new FileReader("src/main/resources/it/polimi/ingsw/gc31/CardsJson/GoldCard.json");

            JsonElement jsonElement = JsonParser.parseReader(fileReader);

            fileReader.close();


            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // create GsonBuilder and add typeAdapter necessary
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(GoldCard.class, new PlayableCardAdapter())
                        .registerTypeAdapter(CardFront.class, new FrontClassAdapter())
                        .registerTypeAdapter(CardBack.class, new BackClassAdapter())
                        .registerTypeAdapter(Resources.class, new ListResourcesEnumAdapter())
                        .registerTypeAdapter(new TypeToken<Map<Resources, Integer>>(){}.getType(), new MapRequirementsAdapter())
                        .create();

                // pars every element
                for (JsonElement element: jsonArray) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();

                        // deserialize a single element and add it to the deck
                        Card res = gson.fromJson(jsonObject, GoldCard.class);
                        deck.add(res);
                    }
                }

                for (Card card: deck) {
                    card.changeSide();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void refill() {
    }
    /*
    public GoldCard crateGoldCard() throws WrongNumberOfCornerException, DirImgValueMissingException {
        Color color = Color.RED;
        int score = 0;
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.FEATHER);
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.HIDDEN);

        List<Resources> resourcesBack = new ArrayList<>();
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.MUSHROOM);

        Map<Resources, Integer> requirements = new HashMap<>();
        requirements.put(Resources.MUSHROOM, 2);
        requirements.put(Resources.ANIMAL, 1);

        String dirImgFront = "";
        String dirImgBack = "";
        Objective ob = null;


        GoldCard goldCard = new GoldCard(color, score, resourcesFront, resourcesBack, requirements, dirImgFront, dirImgBack, ob);
        return goldCard;
    }
     */
}
