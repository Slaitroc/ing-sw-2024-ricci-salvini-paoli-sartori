package it.polimi.ingsw.gc31.model.player;

import java.awt.*;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.StarterCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.exceptions.FullHandException;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Board board;
    private int selectedCard;
    private final String username;
    private final PlayArea playArea;
    private Color pawnColor;
    protected final List<PlayableCard> hand;
    protected ObjectiveCard objectiveCard;
    protected PlayableCard starterCard;
    protected PlayerState inGameState;
    protected int score;

    public Player(Color color, String username) {
        this.board = null;
        this.pawnColor = color;
        this.username = username;
        this.playArea = new PlayArea();
        this.inGameState = new Start();
        this.starterCard = null; // TODO possiamo farla pescare
        hand = new ArrayList<>();
        score = 0;
    }

    public Player(Player player, Board board) {
        this.pawnColor = player.pawnColor;
        this.username = player.username;
        this.playArea = player.playArea;
        this.hand = player.hand;
        this.score = player.score;
        this.board = board;
    }

    public Player(String username, Color color, Board board) {
        this.board = board;
        this.pawnColor = color;
        this.username = username;
        this.playArea = new PlayArea();
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    public void drawGold() {
        hand.add(board.getDeckGold().draw());
    }

    public void drawResource() {
        hand.add(board.getDeckResource().draw());
    }

    public void drawStarter() {
        hand.add(board.getDeckStarter().draw());
    }

    public List<PlayableCard> getHand() {
        return this.hand;
    }

    public void setPawnColor(Color color) {
        this.pawnColor = color;
    }

    public PlayableCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(PlayableCard card) {
        this.starterCard = card;
    }

    /**
     * Method add the selected card to the player hand
     * Notice that after the operation, if the player has 3 cards in hand,
     * he should not be able to draw anymore → changeState();
     * 
     * @param card: address of the card to add in hand
     */
    public void addToHand(PlayableCard card) {
        try {
            inGameState.addToHand(card, this);
        } catch (IllegalStateOperationException | FullHandException e) {
            System.out.println("Error adding to hand");
            e.getStackTrace();
        }
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
            System.out.println("Player not allowed to move cards in hands in current state");
            e.getStackTrace();
        }
    }

    /**
     * Method that calls player.playArea.place(card, point)
     * TODO questionable method!?
     * 
     * @param card:  address of the card to place on players playArea
     * @param point: coordinate of where in the map to place the card
     */
    public void play(Point point) {
        try {
            inGameState.play(getSelectedCard(), point, this);
            this.hand.remove(selectedCard);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player not allowed to place cards in current state");
            e.getStackTrace();
        }
    }

    public void playStarter() {
        try {
            inGameState.playStarter(this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player not allowed to place the starter card in current state");
            e.getStackTrace();
        }
    }

    /**
     *
     * @param card: Objective Card to assign to the player (called secret obj in
     *              game)
     */
    public void addObjectiveCard(ObjectiveCard card) {
        try {
            inGameState.addObjectiveCard(card, this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player not allowed to draw objective card in current state");
            e.getStackTrace();
        }
    }

    public int getScore() {
        return this.score;
    }

    public PlayArea getPlayArea() {
        return playArea;
    }

    public Player getPlayer() {
        return this;
    }

    public String getName() {
        return username;
    }

    public PlayableCard getSelectedCard() {
        return hand.get(selectedCard);
    }

    public void setSelectedCard(int selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setInGameState(PlayerState inGameState) {
        this.inGameState = inGameState;
    }
}
