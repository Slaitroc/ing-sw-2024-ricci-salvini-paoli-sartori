package it.polimi.ingsw.gc31.model.deck;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.utility.gsonUtility.*;

import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class DeckTest {

    @Test
    void draw() {
        Deck<PlayableCard> deck1 = new Deck<>(CardType.GOLD);
        Deck<PlayableCard> deck2 = new Deck<>(CardType.RESOURCE);
        Deck<StarterCard> deck3 = new Deck<>(CardType.STARTER);

        List<PlayableCard> hand = new ArrayList<>();
        hand.add(deck1.draw());
        hand.add(deck2.draw());
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
