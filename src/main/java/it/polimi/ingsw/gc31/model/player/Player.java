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

    public void addToHand(PlayableCard card) {
        try {
            inGameState.addToHand(card, this);
            if(this.hand.size()==3) changeState();
        } catch (IllegalStateOperationException e) {
            System.out.println("Action not allowed in current state");
            e.getStackTrace();
        }
    }

    // Basic repositioning of the card in hand implemented temporarily with an input
    // output System
    // TODO change I/O System with what we really will use
    public void moveCardInHand() {
        try {
            inGameState.moveCardInHand(this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Action not allowed in current state");
            e.getStackTrace();
        }
    }

    // TODO questionable method!?
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

    public void changeState() {
        inGameState = inGameState.changeState();
    }

}
