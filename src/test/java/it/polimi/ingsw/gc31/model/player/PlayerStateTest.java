package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    private static Player player1, player2, player3;
    private static PlayableCard starterCard, resourceCard, goldCard;

    private static Board board;

    @BeforeAll
    public static void setUp() {
        player1 = new Player(Color.RED, "Player1");
        player2 = new Player(Color.BLUE, "Player2");
        player3 = new Player(Color.YELLOW, "Player3");
        board = new Board();
        starterCard = board.getDeckStarer().draw();
        assertNotNull(starterCard);
        // System.out.println("starterCard non è nulla");
        // System.out.println("player1");
        player1.getPlayArea().placeStarter(starterCard);
        // System.out.println("non è un problema");
        // System.out.println("player2");
        starterCard = board.getDeckStarer().draw();
        // System.out.println("non è un problema");
        player2.getPlayArea().placeStarter(starterCard);
        starterCard = createStarterCard(
                Resources.MUSHROOM, Resources.ANIMAL, Resources.INSECT, Resources.PLANT,
                Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.MUSHROOM);
        // System.out.println("player3");
        player3.getPlayArea().placeStarter(starterCard);
        // System.out.println("non è un problema");

        for (int j = 0; j < 2; j++) {
            resourceCard = board.getDeckResource().draw();
            player1.addToHand(resourceCard);
            // System.out.println("Assertion1");
            assertInstanceOf(Start.class, player1.inGameState);
            resourceCard = board.getDeckResource().draw();
            player2.addToHand(resourceCard);
            // System.out.println("Assertion3");
            assertInstanceOf(Start.class, player2.inGameState);
            resourceCard = board.getDeckResource().draw();
            player3.addToHand(resourceCard);
            // System.out.println("Assertion3");
            assertInstanceOf(Start.class, player3.inGameState);
        }
        goldCard = board.getDeckGold().draw();
        player1.addToHand(goldCard);
        player1.setInGameState(new NotPlaced());
        // System.out.println("Assertion4");
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Start.class, player2.inGameState);
        assertInstanceOf(Start.class, player3.inGameState);
        goldCard = board.getDeckGold().draw();
        player2.addToHand(goldCard);
        player2.setInGameState(new Waiting());
        // System.out.println("Assertion5");
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Start.class, player3.inGameState);
        goldCard = board.getDeckGold().draw();
        player3.addToHand(goldCard);
        player3.setInGameState(new Waiting());
        // System.out.println("Assertion6");
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        System.out.println("SetUp Completed");
    }

    // @Test
    // public void testGameTurns(){
    // System.out.println("Start testGameTurns");

    // assertInstanceOf(NotPlaced.class, player1.inGameState);
    // assertInstanceOf(Waiting.class, player2.inGameState);
    // assertInstanceOf(Waiting.class, player3.inGameState);

    // player1.play(player1.hand.get(1), new Point(1,1));
    // assertInstanceOf(Placed.class, player1.inGameState);
    // player1.addToHand(board.getDeckResource().draw());
    // assertInstanceOf(Waiting.class, player1.inGameState);

    // player2.setInGameState(new NotPlaced());

    // assertInstanceOf(NotPlaced.class, player2.inGameState);
    // player2.play(player2.hand.get(1), new Point(1,1));
    // assertInstanceOf(Placed.class, player2.inGameState);
    // player2.addToHand(board.getDeckResource().draw());
    // assertInstanceOf(Waiting.class, player2.inGameState);
    // assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1,1)),
    // player2.getPlayArea().getPlacedCards().get(new Point(1,1)));

    // player3.setInGameState(new NotPlaced());

    // assertInstanceOf(NotPlaced.class, player3.inGameState);
    // player3.play(player3.hand.get(1), new Point(1,1));
    // assertInstanceOf(Placed.class, player3.inGameState);
    // player3.addToHand(board.getDeckResource().draw());
    // assertInstanceOf(Waiting.class, player3.inGameState);

    // player1.setInGameState(new NotPlaced());
    // assertInstanceOf(NotPlaced.class, player1.inGameState);
    // assertInstanceOf(Waiting.class, player2.inGameState);
    // assertInstanceOf(Waiting.class, player3.inGameState);

    // player1.play(player1.hand.get(1), new Point(1,1));
    // assertInstanceOf(Placed.class, player1.inGameState);
    // player1.addToHand(board.getDeckResource().draw());
    // assertInstanceOf(Waiting.class, player1.inGameState);
    // player2.setInGameState(new NotPlaced());

    // assertInstanceOf(NotPlaced.class, player2.inGameState);
    // player2.play(player2.hand.get(1), new Point(1,1));
    // assertInstanceOf(Placed.class, player2.inGameState);
    // player2.addToHand(board.getDeckResource().draw());
    // assertInstanceOf(Waiting.class, player2.inGameState);
    // assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1,1)),
    // player2.getPlayArea().getPlacedCards().get(new Point(1,1)));
    // player3.setInGameState(new NotPlaced());

    // assertInstanceOf(NotPlaced.class, player3.inGameState);
    // player3.play(player3.hand.get(1), new Point(1,1));
    // assertInstanceOf(Placed.class, player3.inGameState);
    // player3.addToHand(board.getDeckResource().draw());
    // assertInstanceOf(Waiting.class, player3.inGameState);
    // }

    private static PlayableCard createStarterCard(Resources f0, Resources f1, Resources f2, Resources f3,
            Resources b0, Resources b1, Resources b2, Resources b3, Resources b4) {
        int score = 0;

        // resources deve avere 4 elementi
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(f0);
        resourcesFront.add(f1);
        resourcesFront.add(f2);
        resourcesFront.add(f3);

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
        resourceBack.add(b0);
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack);

        return new StarterCard(front, back);
    }
}