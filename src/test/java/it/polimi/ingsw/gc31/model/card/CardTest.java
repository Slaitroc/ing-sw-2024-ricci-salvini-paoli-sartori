package it.polimi.ingsw.gc31.model.card;

import org.junit.jupiter.api.Test;

import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

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