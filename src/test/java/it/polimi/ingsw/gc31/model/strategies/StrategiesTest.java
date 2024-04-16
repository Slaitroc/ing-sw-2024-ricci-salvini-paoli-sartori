package it.polimi.ingsw.gc31.model.strategies;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.PlayArea;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the attributes and methods used to test the ResourceScore
 * class
 */
class StrategiesTest {
    static private PlayArea playArea;
    static private Point point;

    @BeforeEach
    void initializeModel() {
        playArea = new PlayArea();
        point = new Point(0, 0);
        PlayableCard card;

        card = createStarterCard(Resources.PLANT, Resources.INSECT, Resources.MUSHROOM,
                Resources.ANIMAL, Resources.PLANT, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.ANIMAL, Resources.INSECT, Resources.EMPTY);
        card.changeSide();
        playArea.placeStarter(card);

        card = createResourceCard(Resources.PLANT, Resources.EMPTY, Resources.HIDDEN,
                Resources.PLANT, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.PLANT);
        card.changeSide();
        playArea.place(card, new Point(1,1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(1,1)));
        assertTrue(card.getSide());

        card = createResourceCard(Resources.PLANT, Resources.HIDDEN, Resources.INK,
                Resources.INSECT, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT);
        card.changeSide();
        playArea.place(card, new Point(-1,1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(-1,1)));

        card = createResourceCard(Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT);
        card.changeSide();
        playArea.place(card, new Point(-1,-1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(-1,-1)));

        card = createResourceCard(Resources.PLANT, Resources.PLANT, Resources.EMPTY,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.PLANT);
        card.changeSide();
        playArea.place(card, new Point(1,-1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(1,-1)));

        card = createResourceCard(Resources.MUSHROOM, Resources.ANIMAL, Resources.HIDDEN,
                Resources.INK, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.MUSHROOM);
        card.changeSide();
        playArea.place(card, new Point(0,2));
        assertEquals(card, playArea.getPlacedCards().get(new Point(0,2)));

        card = createResourceCard(Resources.FEATHER, Resources.INSECT, Resources.ANIMAL,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT);
        card.changeSide();
        playArea.place(card, new Point(-2,0));
        assertEquals(card, playArea.getPlacedCards().get(new Point(-2,0)));

        card = createResourceCard(Resources.INSECT, Resources.EMPTY, Resources.EMPTY,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT);
        card.changeSide();
        playArea.place(card, new Point(0,-2));
        assertEquals(card, playArea.getPlacedCards().get(new Point(0,-2)));

        card = createResourceCard(Resources.ANIMAL, Resources.HIDDEN, Resources.EMPTY,
                Resources.ANIMAL, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.ANIMAL);
        card.changeSide();
        playArea.place(card, new Point(-2,2));
        assertEquals(card, playArea.getPlacedCards().get(new Point(-2,2)));

        card = createResourceCard(Resources.EMPTY, Resources.EMPTY, Resources.HIDDEN,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT);
        card.changeSide();
        playArea.place(card, new Point(2, 2));
        assertEquals(card, playArea.getPlacedCards().get(new Point(2, 2)));

        card = createResourceCard(Resources.PLANT, Resources.EMPTY, Resources.EMPTY,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.PLANT);
        card.changeSide();
        playArea.place(card, new Point(2,-2));
        assertEquals(card, playArea.getPlacedCards().get(new Point(2,-2)));

        card = createResourceCard(Resources.INSECT, Resources.PLANT, Resources.FEATHER,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.PLANT);
        card.changeSide();
        playArea.place(card, new Point(-1,3));
        assertEquals(card, playArea.getPlacedCards().get(new Point(-1,3)));


        card = createResourceCard(Resources.FEATHER, Resources.MUSHROOM, Resources.PLANT,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.MUSHROOM);
        card.changeSide();
        playArea.place(card, new Point(0,4));
        assertEquals(card, playArea.getPlacedCards().get(new Point(0,4)));

        card = createResourceCard(Resources.EMPTY, Resources.PLANT, Resources.HIDDEN,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.PLANT);
        card.changeSide();
        playArea.place(card, new Point(1,3));
        assertEquals(card, playArea.getPlacedCards().get(new Point(1,3)));

        card = createResourceCard(Resources.INSECT, Resources.MUSHROOM, Resources.HIDDEN,
                Resources.SCROLL, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.INSECT);
        card.changeSide();
        playArea.place(card, new Point(2, 4));
        assertEquals(card, playArea.getPlacedCards().get(new Point(2, 4)));

        card = createResourceCard(Resources.ANIMAL, Resources.ANIMAL, Resources.HIDDEN,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.ANIMAL);
        card.changeSide();
        playArea.place(card, new Point(1,5));
        assertEquals(card, playArea.getPlacedCards().get(new Point(1,5)));

        card = createResourceCard(Resources.EMPTY, Resources.HIDDEN, Resources.PLANT,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.PLANT);
        card.changeSide();
        playArea.place(card, new Point(3,-3));
        assertEquals(card, playArea.getPlacedCards().get(new Point(3,-3)));

        card = createResourceCard(Resources.MUSHROOM, Resources.EMPTY, Resources.HIDDEN,
                Resources.MUSHROOM, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.MUSHROOM);
        card.changeSide();
        playArea.place(card, new Point(3,-1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(3,-1)));

        card = createResourceCard(Resources.ANIMAL, Resources.HIDDEN, Resources.EMPTY,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.ANIMAL);
        card.changeSide();
        playArea.place(card, new Point(2,0));
        assertEquals(card, playArea.getPlacedCards().get(new Point(2,0)));

        card = createResourceCard(Resources.EMPTY, Resources.HIDDEN, Resources.SCROLL,
                Resources.MUSHROOM, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.MUSHROOM);
        card.changeSide();
        playArea.place(card, new Point(4,0));
        assertEquals(card, playArea.getPlacedCards().get(new Point(4,0)));

        card = createResourceCard(Resources.EMPTY, Resources.ANIMAL, Resources.ANIMAL,
                Resources.HIDDEN, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.ANIMAL);
        card.changeSide();
        playArea.place(card, new Point(3,1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(3,1)));

        card = createResourceCard(Resources.EMPTY, Resources.HIDDEN, Resources.MUSHROOM,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.MUSHROOM);
        card.changeSide();
        playArea.place(card, new Point(5,1));
        assertEquals(card, playArea.getPlacedCards().get(new Point(5,1)));

        card = createResourceCard(Resources.HIDDEN, Resources.EMPTY, Resources.ANIMAL,
                Resources.ANIMAL, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY,
                Resources.ANIMAL);
        card.changeSide();
        playArea.place(card, new Point(4,2));
        assertEquals(card, playArea.getPlacedCards().get(new Point(4,2)));

    }

    /**
     * This method tests if the result of ResourceScore is correct with a certain
     * Resources contained on the board
     * Firstly create a ResourceScore objective, then places 4 card on the play
     * area. When the 4 cards are placed
     * the result of the ResourceScore class is tested
     */
    @Test
    void ResourcesCountTest() {
        System.out.println("ResourcesCountTest Starting:");
        System.out.println(playArea.getAchievedResources());

        Objective ob = new ResourceScore(Resources.INK);
        assertEquals(0, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (INK) SUCCESS");

        ob = new ResourceScore(Resources.SCROLL);
        assertEquals(1, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (SCROLL) SUCCESS");

        ob = new ResourceScore(Resources.FEATHER);
        assertEquals(2, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (FEATHER) SUCCESS");

        ob = new ResourceScore(Resources.ANIMAL);
        assertEquals(9, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (ANIMAL) SUCCESS");

        ob = new ResourceScore(Resources.MUSHROOM);
        assertEquals(2, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (MUSHROOM) SUCCESS");

        ob = new ResourceScore(Resources.INSECT);
        assertEquals(3, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (INSECT) SUCCESS");

        ob = new ResourceScore(Resources.PLANT);
        assertEquals(4, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("ResourcesCountTest (PLANT) SUCCESS");

    }

    @Test
    void LShapeTest(){
        Objective ob = new LShape();
        assertEquals(3, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("LShapeTest SUCCESS");
    }

    @Test
    void SevenTest(){
        Objective ob = new Seven();
        assertEquals(6, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("SevenTest SUCCESS");
    }

    @Test
    void LShapeReverseTest(){
        Objective ob = new LShapeReverse();
        assertEquals(3, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("LShapeReverseTest SUCCESS");
    }

    @Test
    void SevenReverseTest(){
        Objective ob = new SevenReverse();
        assertEquals(0, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("SevenReverseTest SUCCESS");
    }

    @Disabled
    void CoverCornerScore(){

    }

    @Test
    void StairUpTest(){
        Objective ob = new StairUp(CardColor.BLUE);
        assertEquals(2, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("StairUpTest (BLUE) SUCCESS");

        ob = new StairUp(CardColor.RED);
        assertEquals(2, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("StairUpTest (RED) SUCCESS");
    }

    @Test
    void StairDownTest(){
        Objective ob = new StairDown(CardColor.PURPLE);
        assertEquals(2, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("StairDownTest (PURPLE) SUCCESS");

        ob = new StairDown(CardColor.GREEN);
        assertEquals(2, ob.isObjectiveDone(playArea.getPlacedCards(), point, playArea.getAchievedResources()));
        System.out.println("StairDownTest (GREEN) SUCCESS");
    }




    private static PlayableCard createStarterCard(Resources f1, Resources f2, Resources f3, Resources f4, Resources b1,
                                                  Resources b2, Resources b3, Resources b4, Resources b5, Resources b6, Resources b7) {
        int score = 0;

        List<Resources> resourceFront = new ArrayList<>();
        resourceFront.add(f1);
        resourceFront.add(f2);
        resourceFront.add(f3);
        resourceFront.add(f4);

        Map<Resources, Integer> requirements = new HashMap<>();

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourceFront,
                requirements,
                dirImgFront,
                ob);

        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);
        resourceBack.add(b6);
        resourceBack.add(b7);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack);

        return new StarterCard(CardColor.NOCOLOR, front, back);
    }

    private static PlayableCard createGoldCard(Resources f1, Resources f2, Resources f3, Resources f4,
                                               Resources b1, Resources b2, Resources b3, Resources b4, Resources b5,
                                               Resources r1, int n1, Resources r2, int n2) {
        it.polimi.ingsw.gc31.model.enumeration.CardColor color = it.polimi.ingsw.gc31.model.enumeration.CardColor.RED;

        int score = 0;

        // resources deve avere 4 elementi
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(f1);
        resourcesFront.add(f2);
        resourcesFront.add(f3);
        resourcesFront.add(f4);

        Map<Resources, Integer> requirements = new HashMap<>();
        requirements.put(r1, n1);
        requirements.put(r2, n2);

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                dirImgFront,
                ob);

        // resourceBack può avere dai 4 ai 7 elementi
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack);

        return new GoldCard(color, front, back);
    }

    // f1 sta per front alto-dx, f2 basso-dx, f3 basso-sx, f4 alto-sx
    private static PlayableCard createResourceCard(Resources f1, Resources f2, Resources f3, Resources f4,
                                                   Resources b1, Resources b2, Resources b3, Resources b4, Resources b5) {

        CardColor color;
        switch (b5){
            case ANIMAL -> color=CardColor.BLUE;
            case MUSHROOM -> color=CardColor.RED;
            case INSECT -> color=CardColor.PURPLE;
            case PLANT -> color=CardColor.GREEN;
            default -> color=CardColor.NOCOLOR;
        }

        int score = 0;

        // resources deve avere 4 elementi
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(f1);
        resourcesFront.add(f2);
        resourcesFront.add(f3);
        resourcesFront.add(f4);

        Map<Resources, Integer> requirements = Collections.emptyMap();

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                dirImgFront,
                ob);

        // resourceBack può avere dai 4 ai 7 elementi
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack);

        return new ResourceCard(color, front, back);
    }
}
