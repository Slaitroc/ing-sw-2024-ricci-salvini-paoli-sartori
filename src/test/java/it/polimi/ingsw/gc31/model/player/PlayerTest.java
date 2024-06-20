package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import org.junit.jupiter.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private PlayArea playArea;
    private Board board;
    private Player player;

    /**
     * The method sets up the player and the board where the player "plays"
     *
     * @throws EmptyDeckException
     */
    @BeforeEach
    public void setUp() throws EmptyDeckException{
        playArea = new PlayArea();
        board = new Board();
        PlayableCard starterCard = board.getDeckStarter().draw();
        starterCard.changeSide();
        playArea.placeStarter(starterCard);
        player = new Player(PawnColor.RED, "testPlayer", board);
    }

    //FIXME il player deve essere dentro una partita, invalidcarddraw cosa sarebbe,
    /**
     * This method checks if the player can draw a goldCard correctly.
     */
    @Test
    public void drawGoldTest() throws IllegalStateOperationException {
        //check if the index are considered correctly
        assertFalse(player.drawGold(-1), "Index out of bound");
        assertFalse(player.drawGold(3), "Index out of bound");

        player.setInGameState(new Placed());
        assertTrue(player.drawGold(0), "First card drawn");

        player.setInGameState(new Placed());
        assertTrue(player.drawGold(2), "Second card drawn");

        player.setInGameState(new Placed());
        assertTrue(player.drawGold(1), "Third card drawn");

        //check if the player can draw more than 3 cards
        player.setInGameState(new Placed());
        assertTrue(player.drawGold(2), "Try to draw first card with full hand");
        assertEquals(player.hand.size(), 3, "Still 3 cards in hand");
        player.setInGameState(new Placed());
        assertTrue(player.drawGold(1), "Try to draw second card with full hand");
        assertEquals(player.hand.size(), 3, "Still 3 cards in hand");

        //check if the player can draw a car in the wrong state
        player.setInGameState(new Waiting());
        assertTrue(player.drawGold(1), "Try to draw in a wrong state");
        assertEquals(player.hand.size(), 3, "Still 3 cards in hand");
    }

    /**
     * The method checks if the drawResource method of the player works properly.
     * Checks if the index are managed correctly. Checks if the player can draw more than
     * 3 cards at a time (3 cards should be the maximum size of the hand)
     */
    @Test
    public void drawResourceTest(){
        //check if the index are considered correctly
        assertFalse(player.drawResource(-1), "Index out of bound");
        assertFalse(player.drawResource(3), "Index out of bound");

        player.setInGameState(new Placed());
        assertTrue(player.drawResource(0), "First card drawn");

        player.setInGameState(new Placed());
        assertTrue(player.drawResource(2), "Second card drawn");

        player.setInGameState(new Placed());
        assertTrue(player.drawResource(1), "Third card drawn");

        //check if the player can draw more than 3 cards
        player.setInGameState(new Placed());
        assertTrue(player.drawResource(2), "Try to draw first card with full hand");
        assertEquals(player.hand.size(), 3, "Still 3 cards in hand");
        player.setInGameState(new Placed());
        assertTrue(player.drawResource(1), "Try to draw second card with full hand");
        assertEquals(player.hand.size(), 3, "Still 3 cards in hand");

        //check if the player can draw a car in the wrong state
        player.setInGameState(new Waiting());
        assertTrue(player.drawResource(1), "Try to draw in a wrong state");
        assertEquals(player.hand.size(), 3, "Still 3 cards in hand");
    }

    /**
     * The method checks if the play method of the player works properly.
     * Checks if the player can play a card in the wrong state (an exception should be launched)
     */
    @Test
    public void playTest() {
        //check if the player can play in the wrong states
        player.setInGameState(new Waiting());
        assertThrows(IllegalStateOperationException.class, () -> player.play(new Point(1, 1)),
                "Tried to play a card in the wrong state");
        player.setInGameState(new Start());
        assertThrows(IllegalStateOperationException.class, () -> player.play(new Point(1, 1)),
                "Tried to play a card in the wrong state");
        player.setInGameState(new Placed());
        assertThrows(IllegalStateOperationException.class, () -> player.play(new Point(1, 1)),
                "Tried to play a card in the wrong state");

        //check if the player can correctly invoke the method play if it is in the correct state
        //FIXME se la carta pescata casualmente non potesse essere giocata nella posizione 1 1 cosa succederebbe?
        player.setInGameState(new Placed());
        player.drawResource(0);
        player.setInGameState(new NotPlaced());
        assertDoesNotThrow(() -> player.play(new Point(1, 1)),
                "The correct invocation of the method does not throw anything");
    }

    /**
     * The method checks if the playStarter method of the player works properly.
     * Check if the player can play the starter card in the wrong state. Checks
     * if the player can play the starter before the objective has been chosen
     */
    @Test
    public void playStarterTest(){
        //check if the player can play the starter card in the wrong states
        player.setInGameState(new Placed());
        assertThrows(IllegalStateOperationException.class, () -> player.playStarter());
        player.setInGameState(new NotPlaced());
        assertThrows(IllegalStateOperationException.class, () -> player.playStarter());
        player.setInGameState(new Waiting());
        assertThrows(IllegalStateOperationException.class, () -> player.playStarter());


        //check if the player can play the starter before the objectives have been chosen
        player.setInGameState(new Start());
        assertThrows(ObjectiveCardNotChosenException.class, () -> player.playStarter(),
                "The player can't play the starter if the objectives are not already chosen");

        //check if the player can play the starter correctly
        Start start = new Start();
        start.chooseSecretObjective(board.getDeckObjective().peekCard(), player);
        player.setInGameState(start);
        player.setStarterCard();
        assertDoesNotThrow(() ->player.playStarter(), "If the objective card is chosen the method works properly");
    }

    /**
     * The method checks if the chooseSecretObjective of the player works properly.
     * Checks if the state is correct and if it is not the exception is thrown
     */
    @Test
    public void chooseSecretObjectiveTest(){
        //Populate the list of objective cards at the start of the game
        player.drawChooseObjectiveCards();
        assertEquals(player.getChooseSecretObjective().size(), 2);

        //check the player can't choose the secret objective in the wrong states
        player.setInGameState(new Placed());
        assertThrows(IllegalStateOperationException.class, () -> player.chooseSecretObjective(0));
        player.setInGameState(new NotPlaced());
        assertThrows(IllegalStateOperationException.class, () -> player.chooseSecretObjective(1));
        player.setInGameState(new Waiting());
        assertThrows(IllegalStateOperationException.class, () -> player.chooseSecretObjective(0));

        //check if the player can choose the secret objective in the correct state
        player.setInGameState(new Start());
        assertDoesNotThrow(() -> player.chooseSecretObjective(1));
    }

    /**
     * The method checks if the setSelectCard method of the player
     * works properly, launching the exception when needed
     */
    @Test
    public void setSelectedCardTest(){
        //check if the selection of the card can select an index out of the bound of the hand correctly
        player.hand.add(board.getDeckResource().getCard1());
        player.hand.add(board.getDeckResource().getCard1());
        player.hand.add(board.getDeckResource().getCard1());
        assertThrows(WrongIndexSelectedCard.class, () -> player.setSelectedCard(-1), "The index is too small");
        assertThrows(WrongIndexSelectedCard.class, () -> player.setSelectedCard(5), "The index is too big");

        //check if the player select the card of the hand correctly
        assertDoesNotThrow(() -> player.setSelectedCard(1));
    }

    //many methods (getter and setter mostly) are not checked here but are used in
    // many other tests. The total coverage provided exceed 90% of coverage for the methods
    // and the coverage of the lines is approximately 90%
}
