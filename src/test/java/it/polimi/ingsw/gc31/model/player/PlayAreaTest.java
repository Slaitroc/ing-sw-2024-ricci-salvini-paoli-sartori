package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.deck.Deck;
//import it.polimi.ingsw.gc31.model.player.PlayArea;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class PlayAreaTest {

    private PlayArea playArea;
    private PlayableCard starterCard;
    private Deck<PlayableCard> resourceDeck, goldDeck;

    @BeforeEach
    public void setUp() {
        playArea = new PlayArea();
        Deck<PlayableCard> starterDeck = new Deck<>(CardType.STARTER);
        starterCard = starterDeck.draw();
        //starterCard.changeSide();
        playArea.placeStarter(starterCard);
        resourceDeck = new Deck<>(CardType.RESOURCE);
        goldDeck = new Deck<>(CardType.GOLD);
    }

    // TODO Ask why the starter card seems to not be random?
    @Test
    @DisplayName("First Card Placement Test")
    public void testPlaceStarter() {
        assertEquals(1, playArea.getPlacedCards().size());
        assertEquals(starterCard, playArea.getPlacedCards().get(new Point(0, 0)));
    }

    /*
    * Generic test that place random cards (placed on the back) in specified location
    * To add new card in new space copy/paste the pattern and update the location value
    * TODO Implement a Test non dependent on the randomness of the deck.draw() function
    *  @author Matteo Paoli
    */
    @Test
    @DisplayName("Placing overlapping Cards Test")
    public void testPlaceOnRight() {

        System.out.println("testPlace (1,1)):");
        PlayableCard playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(1, 1));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(1, 1)));
        System.out.println("Correct");

        System.out.println("testPlace (-1,1):");
        playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(-1,1));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(-1,1)));
        System.out.println("Correct");

        System.out.println("testPlace (-1,-1):");
        playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(-1, -1));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(-1, -1)));
        System.out.println("Correct");

        System.out.println("testPlace (1,-1):");
        playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(1, -1));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(1, -1)));
        System.out.println("Correct");

        System.out.println("testPlace (1,-1):");
        PlayableCard playableCard2 = resourceDeck.draw();
        playArea.place(playableCard2, new Point(1, -1));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(1, -1)));
        System.out.println("Correct");

        System.out.println("testPlace (2, 0):");
        playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(2, 0));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(2, 0)));
        System.out.println("Correct");

        System.out.println("testPlace (2, 0):");
        playableCard2 = resourceDeck.draw();
        playArea.place(playableCard2, new Point(2, 0));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(2, 0)));
        System.out.println("Correct");

        System.out.println("testPlace (2, -2):");
        playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(2, -2));
        assertEquals(playableCard, playArea.getPlacedCards().get(new Point(2, -2)));
        System.out.println("Correct");

        System.out.println("testPlace (100, -200):");
        playableCard = resourceDeck.draw();
        playArea.place(playableCard, new Point(100, -200));
        assertNull(playArea.getPlacedCards().get(new Point(100, -200)));
        System.out.println("Correct");
    }


    /*
    * Warning:
    * This test SHOULD result positive in the vast majority of the cases
    * TODO Implement a Test non dependent on the randomness of the deck.draw() function
    *  @author Matteo Paoli
    */
    @Test
    public void testCheckRequirements() {

        PlayableCard resourceCard = resourceDeck.draw();
        playArea.place(resourceCard, new Point(1, 1));
        assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(1, 1)));

        resourceCard = resourceDeck.draw();
        playArea.place(resourceCard, new Point(-1, -1));
        assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(-1, -1)));

        resourceCard = resourceDeck.draw();
        playArea.place(resourceCard, new Point(1, -1));
        assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(1, -1)));

        resourceCard = resourceDeck.draw();
        playArea.place(resourceCard, new Point(-1, 1));
        assertEquals(resourceCard, playArea.getPlacedCards().get(new Point(-1, 1)));

        PlayableCard goldCard = goldDeck.draw();
        goldCard.changeSide();
        //Map<Resources, Integer> achievedResources = new HashMap<Resources, Integer>(playArea.getAchievedResources());
        playArea.place(goldCard, new Point(-2, 2));
        assertNull(playArea.getPlacedCards().get(new Point(-2, 2)));
    }

    @Disabled
    public void testUpdateAvailableRes() {
        Point point = new Point(1, 1);
        Map<Resources, Integer> initialResources = new HashMap<>(playArea.getAchievedResources());
        playArea.updateAvailableRes(resourceDeck.draw(), point);
        // Verify the Resources are being updated correctly
        assertEquals(initialResources.getOrDefault(Resources.MUSHROOM, 0) + 1,
                playArea.getAchievedResources().getOrDefault(Resources.MUSHROOM, 0).intValue());
        assertEquals(initialResources.getOrDefault(Resources.ANIMAL, 0) + 1,
                playArea.getAchievedResources().getOrDefault(Resources.PLANT, 0).intValue());
        // Go on with more resources ...
    }

    public PlayableCard createCard() {
        Color color = Color.RED;

        int score = 0;

        // resources deve avere 4 elementi
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.EMPTY);

        Map<Resources, Integer> requirements = new HashMap<>();
        requirements.put(Resources.ANIMAL, 2);

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                dirImgFront,
                ob
        );

        // resourceBack può avere dai 4 ai 7 elementi
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.MUSHROOM);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack
        );

        return new GoldCard(color, front, back);
    }

}