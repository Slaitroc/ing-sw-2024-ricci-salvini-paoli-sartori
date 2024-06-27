package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    private static Player player1, player2, player3;

    @BeforeAll
    public static void setUp() throws EmptyDeckException {
        @SuppressWarnings("unused")
        Player player = new Player(PawnColor.BLUE, "Ssolvo", null);

        /*
         * Board board = new Board();
         * board.getDeckResource().refill();
         * board.getDeckGold().refill();
         * player1 = new Player(PawnColor.RED, "Alessandro", board);
         * player2 = new Player(PawnColor.BLUE, "Christian", board);
         * player3 = new Player(PawnColor.YELLOW, "Lorenzo", board);
         * 
         * for (int j = 0; j < 2; j++) {
         * // player2.drawResourceCard1(); // Error message supposed to be thrown here
         * // player1.drawResource();
         * // player2.drawResource();
         * // player3.drawResource();
         * }
         * // player1.drawGold();
         * // player1.drawGoldCard1(); // Error message supposed to be thrown here
         * // player1.drawResource(); // Error message supposed to be thrown here
         * // player2.drawGold();
         * // player3.drawGold();
         * 
         * player1.setStarterCard();
         * player2.setStarterCard();
         * player3.setStarterCard();
         * 
         * // TODO da riguardare
         * // player1.addObjectiveCard(board.getDeckObjective().draw());
         * // player2.addObjectiveCard(board.getDeckObjective().draw());
         * // player3.addObjectiveCard(board.getDeckObjective().draw());
         * 
         * player1.playStarter();
         * player2.playStarter();
         * player3.playStarter();
         * 
         * player2.setInGameState(new Waiting());
         * player3.setInGameState(new Waiting());
         * // System.out.println("Assertion6");
         * assertInstanceOf(NotPlaced.class, player1.inGameState);
         * assertInstanceOf(Waiting.class, player2.inGameState);
         * assertInstanceOf(Waiting.class, player3.inGameState);
         * 
         * // System.out.println("SetUp Completed");
         */
    }

    @Disabled
    public void testGameTurns() throws EmptyDeckException, IllegalStateOperationException, WrongIndexSelectedCard {
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
        try {
            player1.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(Placed.class, player1.inGameState);
        assertEquals(verifyCard, player1.getPlayArea().getPlacedCards().get(new Point(1, 1)));
        // player1.drawResourceCard1();
        assertInstanceOf(Waiting.class, player1.inGameState);

        // player1.drawResource();
        try {
            player1.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        player2.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player2.inGameState);
        try {
            player2.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(Placed.class, player2.inGameState);
        // player2.drawResource();
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1, 1)),
                player2.getPlayArea().getPlacedCards().get(new Point(1, 1)));

        player3.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player3.inGameState);
        try {
            player3.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(Placed.class, player3.inGameState);
        // player3.drawResource();
        assertInstanceOf(Waiting.class, player3.inGameState);

        player1.setInGameState(new NotPlaced());
        assertInstanceOf(NotPlaced.class, player1.inGameState);
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertInstanceOf(Waiting.class, player3.inGameState);

        try {
            player1.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(Placed.class, player1.inGameState);
        // player1.drawResourceCard1();
        assertInstanceOf(Waiting.class, player1.inGameState);
        player2.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player2.inGameState);
        try {
            player2.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(Placed.class, player2.inGameState);
        // player2.drawResourceCard2();
        assertInstanceOf(Waiting.class, player2.inGameState);
        assertNotEquals(player1.getPlayArea().getPlacedCards().get(new Point(1, 1)),
                player2.getPlayArea().getPlacedCards().get(new Point(1, 1)));
        player3.setInGameState(new NotPlaced());

        assertInstanceOf(NotPlaced.class, player3.inGameState);
        try {
            player3.play(new Point(1, 1));
        } catch (IllegalPlaceCardException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(Placed.class, player3.inGameState);
        // player3.drawResource();
        assertInstanceOf(Waiting.class, player3.inGameState);
    }
    /*
     * private static PlayableCard createStarterCard() {
     * int score = 0;
     * 
     * // resources needs to have 4 Resources
     * List<Resources> resourcesFront = new ArrayList<>();
     * resourcesFront.add(Resources.MUSHROOM);
     * resourcesFront.add(Resources.ANIMAL);
     * resourcesFront.add(Resources.INSECT);
     * resourcesFront.add(Resources.PLANT);
     * 
     * Map<Resources, Integer> requirements = Collections.emptyMap();
     * 
     * CardFront front = new CardFront(
     * score,
     * resourcesFront,
     * requirements,
     * null,
     * null);
     * 
     * // resourceBack can have from 4 to 7 Resources
     * List<Resources> resourceBack = new ArrayList<>();
     * resourceBack.add(Resources.EMPTY);
     * resourceBack.add(Resources.EMPTY);
     * resourceBack.add(Resources.EMPTY);
     * resourceBack.add(Resources.EMPTY);
     * resourceBack.add(Resources.MUSHROOM);
     * 
     * CardBack back = new CardBack(
     * resourceBack,
     * null);
     * 
     * return new StarterCard(CardColor.NOCOLOR, front, back);
     * }
     */
}