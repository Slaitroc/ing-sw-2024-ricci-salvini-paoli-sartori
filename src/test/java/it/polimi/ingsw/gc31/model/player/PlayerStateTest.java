package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
//import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;
//import it.polimi.ingsw.gc31.model.strategies.Objective;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    private static Player player1, player2, player3;

    private static Board board;

    @BeforeAll
    public static void setUp() {
        player1 = new Player(Color.RED, "Player1");
        player2 = new Player(Color.BLUE, "Player2");
        player3 = new Player(Color.YELLOW, "Player3");
        board = new Board();
        player1.playStarter(board.getDeckStarer().draw());
        player2.playStarter(board.getDeckStarer().draw());
        player3.getPlayArea().placeStarter(createStarterCard(
        ));

        for (int j = 0; j < 2; j++) {
            PlayableCard resourceCard = board.getDeckResource().draw();
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
        PlayableCard goldCard = board.getDeckGold().draw();
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

        //System.out.println("SetUp Completed");
    }

    @Test
    public void testGameTurns() {
        //System.out.println("Start testGameTurns");

        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.play(player1.hand.get(1), new Point(1, 1));
        assertInstanceOf(Placed.class, player1.inGameState);
        player1.addToHand(board.getDeckResource().draw());
        assertInstanceOf(Waiting.class, player1.inGameState);

        player1.addToHand(board.getDeckResource().draw());
        player1.play(player1.hand.get(1), new Point(1, 1));
        player2.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player2.inGameState);
        player2.play(player2.hand.get(1), new Point(1, 1));
        assertInstanceOf(Placed.class, player2.inGameState);
        player2.addToHand(board.getDeckResource().draw());
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1, 1)),
                player2.getPlayArea().getPlacedCards().get(new Point(1, 1)));

        player3.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player3.inGameState);
        player3.play(player3.hand.get(1), new Point(1, 1));
        assertInstanceOf(Placed.class, player3.inGameState);
        player3.addToHand(board.getDeckResource().draw());
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.setInGameState(new NotPlaced());
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.play(player1.hand.get(1), new Point(1, 1));
        assertInstanceOf(Placed.class, player1.inGameState);
        player1.addToHand(board.getDeckResource().draw());
        assertInstanceOf(Waiting.class, player1.inGameState);
        player2.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player2.inGameState);
        player2.play(player2.hand.get(1), new Point(1, 1));
        assertInstanceOf(Placed.class, player2.inGameState);
        player2.addToHand(board.getDeckResource().draw());
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1, 1)),
                player2.getPlayArea().getPlacedCards().get(new Point(1, 1)));
        player3.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player3.inGameState);
        player3.play(player3.hand.get(1), new Point(1, 1));
        assertInstanceOf(Placed.class, player3.inGameState);
        player3.addToHand(board.getDeckResource().draw());
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
                null
        );

        // resourceBack can have from 4 to 7 Resources
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.EMPTY);
        resourceBack.add(Resources.MUSHROOM);

        CardBack back = new CardBack(
                resourceBack,
                null
        );

        return new StarterCard(front, back);
    }
}