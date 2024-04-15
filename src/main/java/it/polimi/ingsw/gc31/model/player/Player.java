package it.polimi.ingsw.gc31.model.player;

import java.awt.*;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.exceptions.FullHandException;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.exceptions.InvalidCardDraw;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Board board;
    private int selectedCard;
    private PlayableCard selectedStarterCard;
    private ObjectiveCard objectiveCard;
    private final String username;
    private final PlayArea playArea;
    private PawnColor pawnColor;
    protected final List<PlayableCard> hand;
    protected PlayableCard starterCard;
    protected PlayerState inGameState;
    protected int score;

    // CONSTRUCTORS
    public Player(PawnColor pawnColor, String username, Board board) {
        this.board = board;
        this.username = username;
        this.playArea = new PlayArea();
        this.inGameState = new Start();
        this.pawnColor = pawnColor;
        hand = new ArrayList<>();
        setStarterCard();
        score = 0;
    }

    // PRIVATE METHODS

    /**
     * Method add the selected card to the player hand
     * Notice that the logic of this class is specified in the states
     * and the method is called by the 6 public drawing methods.
     * Here are written the exceptions messages
     *
     * @param card: address of the card to add in hand
     */
    private void addToHand(PlayableCard card, Boolean byDeck) {
        try {
            inGameState.addToHand(card, this, byDeck);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " cannot draw in current state");
            e.getStackTrace();
        } catch (FullHandException e) {
            System.out.println("Player " + username + "'s hand is full");
            e.getStackTrace();
        } catch (InvalidCardDraw e) {
            System.out.println("Player " + username + " tried to draw an invalid card");
            e.getStackTrace();
        }
    }

    // PUBLIC METHODS

    /**
     * Methods to call when drawing a card. There are going to be 6 buttons in the
     * GUI
     * and each one is going to call one of those methods.
     */
    public void drawGold() {
        addToHand(board.getDeckGold().draw(), true);
    }

    public void drawGoldCard1() {
        addToHand(board.getDeckGold().getCard1(), false);
    }

    public void drawGoldCard2() {
        addToHand(board.getDeckGold().getCard2(), false);
    }

    public void drawResource() {
        addToHand(board.getDeckResource().draw(), true);
    }

    public void drawResourceCard1() {
        addToHand(board.getDeckResource().getCard1(), false);
    }

    public void drawResourceCard2() {
        addToHand(board.getDeckResource().getCard2(), false);
    }

    /**
     * Basic repositioning of the card in hand implemented temporarily with an input
     * output System
     * TODO change I/O System with what we will actually use
     */
    public void moveCardInHand() {
        try {
            inGameState.moveCardInHand(this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " not allowed to move cards in hand in current state");
            e.getStackTrace();
        }
    }

    /**
     * Method that calls player.playArea.place(point)
     *
     * @param point: coordinate of where in the map to place the card
     */
    public void play(Point point) {
        try {
            inGameState.play(point, this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " not allowed to place cards in current state");
            e.getStackTrace();
        }
    }

    public void playStarter() {
        try {
            inGameState.playStarter(this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player" + username + " not allowed to place the starter card in current state");
            e.getStackTrace();
        }
    }

    /**
     * @param card: Objective Card to assign to the player (called secret obj in
     *              game)
     */
    public void addObjectiveCard(ObjectiveCard card) {
        try {
            inGameState.addObjectiveCard(card, this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " not allowed to draw objective card in current state");
            e.getStackTrace();
        }
    }

    // GETTERS
    public Board getBoard() {
        return this.board;
    }

    public PlayableCard getSelectedCard() {
        return hand.get(selectedCard);
    }

    public PlayableCard getStarterCard() {
        return this.selectedStarterCard;
    }

    public String getName() {
        return this.username;
    }

    public int getScore() {
        return this.score;
    }

    public PlayArea getPlayArea() {
        return this.playArea;
    }

    public List<PlayableCard> getHand() {
        return this.hand;
    }

    // SETTERS
    public void setSelectedCard(int selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setStarterCard() {
        this.selectedStarterCard = board.getDeckStarter().draw();
    }

    public void setObjectiveCard(ObjectiveCard card) {
        this.objectiveCard = card;
    }

    public void setInGameState(PlayerState inGameState) {
        this.inGameState = inGameState;
    }
}
