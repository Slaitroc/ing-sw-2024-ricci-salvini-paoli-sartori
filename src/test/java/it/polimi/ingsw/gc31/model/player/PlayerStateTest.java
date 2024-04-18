package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    private static Player player1, player2, player3;


    @BeforeAll
    public static void setUp() throws EmptyDeckException {
        Board board = new Board();
        player1 = new Player(PawnColor.RED, "Alessandro", board);
        player2 = new Player(PawnColor.BLUE, "Christian", board);
        player3 = new Player(PawnColor.YELLOW, "Lorenzo", board);
        player1.playStarter();
        player2.playStarter();
        player3.getPlayArea().placeStarter(createStarterCard());

        for (int j = 0; j < 2; j++) {
            player2.drawResourceCard1();
            player1.drawResource();
            // System.out.println("Assertion1");
            assertInstanceOf(Start.class, player1.inGameState);
            player2.drawResource();
            // System.out.println("Assertion2");
            assertInstanceOf(Start.class, player2.inGameState);
            player3.drawResource();
            // System.out.println("Assertion3");
            assertInstanceOf(Start.class, player3.inGameState);
        }
        player1.drawGold();
        player1.drawGoldCard1();
        player1.drawResource();
        player1.setInGameState(new NotPlaced());
        // System.out.println("Assertion4");
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Start.class, player2.inGameState);
        assertInstanceOf(Start.class, player3.inGameState);

        player2.drawGold();
        player2.setInGameState(new Waiting());
        // System.out.println("Assertion5");
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Start.class, player3.inGameState);

        player3.drawGold();
        player3.setInGameState(new Waiting());
        // System.out.println("Assertion6");
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        // System.out.println("SetUp Completed");
    }

    @Test
    public void testGameTurns() throws EmptyDeckException{
        // System.out.println("Start testGameTurns");
        PlayableCard verifyCard;

        player1.setSelectedCard(0);
        assertEquals(player1.getHand().get(0), player1.getSelectedCard());
        player1.setSelectedCard(1);
        assertEquals(player1.getHand().get(1), player1.getSelectedCard());
        player1.setSelectedCard(2);
        assertEquals(player1.getHand().get(2), player1.getSelectedCard());

        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.setSelectedCard(2);
        verifyCard = player1.getSelectedCard();
        player1.play(new Point(1, 1));
        assertInstanceOf(Placed.class, player1.inGameState);
        assertEquals(verifyCard, player1.getPlayArea().getPlacedCards().get(new Point(1, 1)));
        player1.drawResourceCard1();
        assertInstanceOf(Waiting.class, player1.inGameState);

        player1.drawResource();
        player1.play(new Point(1, 1));
        player2.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player2.inGameState);
        player2.play(new Point(1, 1));
        assertInstanceOf(Placed.class, player2.inGameState);
        player2.drawResource();
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1, 1)),
                player2.getPlayArea().getPlacedCards().get(new Point(1, 1)));

        player3.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player3.inGameState);
        player3.play(new Point(1, 1));
        assertInstanceOf(Placed.class, player3.inGameState);
        player3.drawResource();
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.setInGameState(new NotPlaced());
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.play(new Point(1, 1));
        assertInstanceOf(Placed.class, player1.inGameState);
        player1.drawResourceCard1();
        assertInstanceOf(Waiting.class, player1.inGameState);
        player2.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player2.inGameState);
        player2.play(new Point(1, 1));
        assertInstanceOf(Placed.class, player2.inGameState);
        player2.drawResourceCard2();
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1, 1)),
                player2.getPlayArea().getPlacedCards().get(new Point(1, 1)));
        player3.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player3.inGameState);
        player3.play(new Point(1, 1));
        assertInstanceOf(Placed.class, player3.inGameState);
        player3.drawResource();
        assertInstanceOf(Waiting.class, player3.inGameState);
    }

    private static PlayableCard createStarterCard() {
        int score = 0;

        // resources needs to have 4 Resources
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(Resources.MUSHROOM);
        resourcesFront.add(Resources.ANIMAL);
        resourcesFront.add(Resources.INSECT);
        resourcesFront.add(Resources.PLANT);

        Map<Resources, Integer> requirements = Collections.emptyMap();

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                null,
                null);

        // resourceBack can have from 4 to 7 Resources
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.MUSHROOM);

        CardBack back = new CardBack(
                resourceBack,
                null);

        return new StarterCard(CardColor.NOCOLOR, front, back);
    }
}