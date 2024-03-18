package it.polimi.ingsw.gc31.model.player;
import java.util.Scanner;  // Import the Scanner class to test moveCardInHand

import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private PlayArea playArea;
    private final Color pawnColor;
    private List<Card> hand;
    private ObjectiveCard objectiveCard;
    protected int score;


    public Player(Color color, String username){
        this.pawnColor = color;
        this.username = username;
    }

    //Really Necessary?
    public boolean addToHand(Card card){
        this.hand.add(card);
    return true;
    }

    //Basic repositioning of the card in hand implemented temporarily with an input output System
    public void moveCardInHand(){
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Insert position of the first card [1-3]: ");
        int cardPosition1 = myScanner.nextInt();
        Card card1 = hand.get(cardPosition1);

        System.out.println("Insert position of the second card [1-3]: ");
        int cardPosition2 = myScanner.nextInt();
        Card card2 = hand.get(cardPosition2);

        this.hand.set(cardPosition1, card2);
        this.hand.set(cardPosition2, card1);
        System.out.println("New Hand disposition: " + hand);
    }
    public void changeState(){
        //??
    }
    public void addObjectiveCard(ObjectiveCard card){
        this.objectiveCard = card;
    }
    public int getScore(){
        return this.score;
    }

}
