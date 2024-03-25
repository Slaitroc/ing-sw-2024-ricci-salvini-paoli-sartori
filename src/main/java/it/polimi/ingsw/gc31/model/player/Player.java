package it.polimi.ingsw.gc31.model.player;

import java.awt.*;
import java.util.Scanner; // Import the Scanner class to test moveCardInHand

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

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
        hand = new ArrayList<>();
        score = 0;
    }

    // Really Necessary?
    public boolean addToHand(PlayableCard card) {
        try {
            this.hand.add(card);
            return true;
        }

        catch (NullPointerException e) {
            e.getStackTrace();
            return false;
        }
    }

    // Basic repositioning of the card in hand implemented temporarily with an input
    // output System
    // TODO change I/O System with what we really will use
    public void moveCardInHand() {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Insert position of the first card [1-3]: ");
        int cardPosition1 = myScanner.nextInt();
        PlayableCard card1 = hand.get(cardPosition1);

        System.out.println("Insert position of the second card [1-3]: ");
        int cardPosition2 = myScanner.nextInt();
        PlayableCard card2 = hand.get(cardPosition2);

        this.hand.set(cardPosition1, card2);
        this.hand.set(cardPosition2, card1);
        System.out.println("New Hand disposition: " + hand);
    }

    public void changeState() {
        // ??
    }

    // TODO questionable method!?
    public void play(PlayableCard card, Point point) {
        this.score += playArea.place(card, point);
        changeState();
    }

    public void addObjectiveCard(ObjectiveCard card) {
        inGameState.addObjectiveCard(objectiveCard, this);
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

}
