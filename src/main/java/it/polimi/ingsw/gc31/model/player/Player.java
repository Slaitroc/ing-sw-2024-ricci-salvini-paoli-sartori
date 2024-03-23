package it.polimi.ingsw.gc31.model.player;

import java.util.Scanner; // Import the Scanner class to test moveCardInHand

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private PlayArea playArea;
    private final Color pawnColor;
    private List<PlayableCard> hand;
    private ObjectiveCard objectiveCard;
    private PlayerState inGameState;
    protected int score;

    public Player(Color color, String username) {
        // inGameState = Waiting;
        this.pawnColor = color;
        this.username = username;
        this.playArea = new PlayArea();
        hand = new ArrayList<PlayableCard>();
        score = 0;

    }

    // Really Necessary?
    public boolean addToHand(PlayableCard card) {
        this.hand.add(card);
        return true;
    }

    // Basic repositioning of the card in hand implemented temporarily with an input
    // output System
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

    public void addObjectiveCard(ObjectiveCard card) {
        this.objectiveCard = card;
    }

    public int getScore() {
        return this.score;
    }

    public PlayArea getPlayArea() {
        return playArea;
    }

    public String getName() {
        return username;
    }

}
