package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Deck.GoldDeck;
import it.polimi.ingsw.gc31.Model.Enum.CardType;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getSide() {
        int score = 1;
        List<Resources> resourcesFront= new ArrayList<>();
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.HIDDEN);
        resourcesFront.add(Resources.MUSHROOM);
        resourcesFront.add(Resources.MUSHROOM);
        List<Resources> resourcesBack = new ArrayList<>();

        Map<Resources, Integer> requirements = new HashMap<>();
        requirements.put(Resources.MUSHROOM, 2);
        String dirImgFront = null;
        String dirImgBack = null;
        Objective ob = null;

    }

    @Test
    void changeSide() {
    }

    @Test
    void getImage() {
    }

    @Test
    void getObjective() {
    }
}