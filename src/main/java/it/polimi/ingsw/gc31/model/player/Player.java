package it.polimi.ingsw.gc31.model.player;

import java.awt.*;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String username;
    protected final PlayArea playArea;
    private final Color pawnColor;
    protected final List<PlayableCard> hand;
    protected ObjectiveCard objectiveCard;
    private PlayerState inGameState;
    protected int score;

    public Player(Color color, String username) {
        this.pawnColor = color;
        this.username = username;
        this.playArea = new PlayArea();
        this.inGameState = new Start();
        hand = new ArrayList<>();
        score = 0;
    }

    /**
     * Method add the selected card to the player hand
     * Notice that after the operation, if the player has 3 cards in hand,
     * he should not be able to draw anymore → changeState();
     * @param card: address of the card to add in hand
     */
    public void addToHand(PlayableCard card) {
        try {
            inGameState.addToHand(card, this);
            if(this.hand.size()==3) changeState();
        } catch (IllegalStateOperationException e) {
            System.out.println("Action not allowed in current state");
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
            System.out.println("Action not allowed in current state");
            e.getStackTrace();
        }
    }

    /**
     *  Method that calls player.playArea.place(card, point)
     * TODO questionable method!?
     * @param card: address of the card to place on players playArea
     * @param point: coordinate of where in the map to place the card
     */
    public void play(PlayableCard card, Point point) {
        try {
            inGameState.play(card, point, this);
            this.hand.remove(card);
            changeState();
        } catch (IllegalStateOperationException e) {
            System.out.println("Action not allowed in current state");
            e.getStackTrace();
        }
    }

    /**
     *
     * @param card: Objective Card to assign to the player (called secret obj in game)
     */
    public void addObjectiveCard(ObjectiveCard card) {
        try {
            inGameState.addObjectiveCard(card, this);
            changeState();
        } catch (IllegalStateOperationException e) {
            System.out.println("Action not allowed in current state");
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

    /**
     * Method cycles through the Player states like in a FSA
     */
    public void changeState() {
        inGameState = inGameState.changeState();
    }

}
