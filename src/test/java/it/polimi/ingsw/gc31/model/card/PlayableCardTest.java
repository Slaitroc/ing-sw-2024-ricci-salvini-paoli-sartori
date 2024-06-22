package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.model.strategies.ResourceScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayableCardTest {
    PlayableCard goldCard;
    @BeforeEach
    void setUp() {
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(Resources.MUSHROOM);
        resourcesFront.add(Resources.ANIMAL);
        resourcesFront.add(Resources.PLANT);
        resourcesFront.add(Resources.INSECT);

        List<Resources> resourcesBack = new ArrayList<>();
        resourcesBack.add(Resources.HIDDEN);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.INK);
        resourcesBack.add(Resources.FEATHER);
        resourcesBack.add(Resources.SCROLL);

        Map<Resources, Integer> requirementsFront = new HashMap<>();
        requirementsFront.put(Resources.MUSHROOM, 2);
        requirementsFront.put(Resources.ANIMAL, 1);

        List<Resources> resourceOb = new ArrayList<>();
        resourceOb.add(Resources.MUSHROOM);
        resourceOb.add(Resources.MUSHROOM);
        resourceOb.add(Resources.MUSHROOM);

        goldCard = new GoldCard(
                CardColor.RED,
                new CardFront(
                        2,
                        resourcesFront,
                        requirementsFront,
                        "provaDirFront",
                        new ResourceScore(Resources.ANIMAL)
                ),
                new CardBack(
                        resourcesBack,
                        "provaDirBack"
                )
        );
    }

    @Test
    void getScore() {
        assertEquals(0, goldCard.getScore());
        goldCard.changeSide();
        assertEquals(2, goldCard.getScore());
    }

    @Test
    void getColor() {
    }

    @Test
    void checkCorner() {
        // 0 corner of the back is hidden, checkCorner must return false
        assertFalse(goldCard.checkCorner(0));
        // 1 corner of the back is EMPTY, checkCorner must return true
        assertTrue(goldCard.checkCorner(1));
        // 2 corner of the back is INK, checkCorner must return true
        assertTrue(goldCard.checkCorner(2));
        // 2 corner of the back is FEATHER, checkCorner must return true
        assertTrue(goldCard.checkCorner(3));
        // 2 corner of the back is SCROLL, checkCorner must return true
        assertTrue(goldCard.checkCorner(4));

        goldCard.changeSide();
        // 0 corner of the front is MUSHROOM, checkCorner should return true
        assertTrue(goldCard.checkCorner(0));
        // 0 corner of the front is ANIMAL, checkCorner should return true
        assertTrue(goldCard.checkCorner(1));
        // 0 corner of the front is PLANT, checkCorner should return true
        assertTrue(goldCard.checkCorner(2));
        // 0 corner of the front is INSECT, checkCorner should return true
        assertTrue(goldCard.checkCorner(3));
    }

    @Test
    void coverCorner() {

        // cover corner 1: from EMPTY to HIDDEN
        assertTrue(goldCard.checkCorner(1));
        goldCard.coverCorner(1);
        assertFalse(goldCard.checkCorner(1));

        goldCard.changeSide();
        assertTrue(goldCard.checkCorner(0));
        goldCard.coverCorner(0);
        assertFalse(goldCard.checkCorner(0));

        goldCard.coverCorner(0);
        assertFalse(goldCard.checkCorner(0));
    }

    @Test
    void getResources() {
        List<Resources> resources = goldCard.getResources();
        assertEquals(3, resources.size());
        assertTrue(resources.contains(Resources.INK));
        assertTrue(resources.contains(Resources.FEATHER));
        assertTrue(resources.contains(Resources.SCROLL));

        goldCard.coverCorner(2);
        resources = goldCard.getResources();
        assertEquals(2, resources.size());
        assertTrue(resources.contains(Resources.FEATHER));
        assertTrue(resources.contains(Resources.SCROLL));

        goldCard.changeSide();
        resources = goldCard.getResources();
        assertEquals(4, resources.size());
        assertTrue(resources.contains(Resources.MUSHROOM));
        assertTrue(resources.contains(Resources.ANIMAL));
        assertTrue(resources.contains(Resources.PLANT));
        assertTrue(resources.contains(Resources.INSECT));

        goldCard.coverCorner(0);
        goldCard.coverCorner(1);
        goldCard.coverCorner(2);
        goldCard.coverCorner(3);
        assertEquals(0, goldCard.getResources().size());
    }

    @Test
    void getRequirements() {
        Map<Resources, Integer> requirements = goldCard.getRequirements();
        assertEquals(0, requirements.size());

        goldCard.changeSide();
        requirements = goldCard.getRequirements();
        assertEquals(2, requirements.size());
        assertEquals(2, requirements.get(Resources.MUSHROOM));
        assertEquals(1, requirements.get(Resources.ANIMAL));
    }

    @Test
    void getObjective() {
        assertNull(goldCard.getObjective());
        goldCard.changeSide();
        assertInstanceOf(ResourceScore.class, goldCard.getObjective());
    }

    @Test
    void getSide() {
    }

    @Test
    void changeSide() {
    }

    @Test
    void getImage() {
        assertEquals("provaDirBack", goldCard.getImage());
        goldCard.changeSide();
        assertEquals("provaDirFront", goldCard.getImage());
    }

    @Test
    void getCorners() {
        List<Resources> corners = goldCard.getCorners();
        assertEquals(5, corners.size());
        assertTrue(corners.contains(Resources.HIDDEN));
        assertTrue(corners.contains(Resources.EMPTY));
        assertTrue(corners.contains(Resources.INK));
        assertTrue(corners.contains(Resources.FEATHER));
        assertTrue(corners.contains(Resources.SCROLL));

        goldCard.changeSide();
        corners = goldCard.getCorners();
        assertEquals(4, corners.size());
        assertTrue(corners.contains(Resources.MUSHROOM));
        assertTrue(corners.contains(Resources.ANIMAL));
        assertTrue(corners.contains(Resources.INSECT));
        assertTrue(corners.contains(Resources.PLANT));
    }
}