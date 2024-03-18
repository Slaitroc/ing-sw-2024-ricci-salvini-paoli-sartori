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
        playArea = new PlayArea(this);
        hand = new ArrayList<Card>();
        score = 0;
    }

    public boolean addToHand(Card card){
        return true;//FIX

    }
    public void moveCardInHand(){

    }
    public void changeState(){

    }
    public void addObjectiveCard(ObjectiveCard card){

    }
    public int getScore(){
        return this.score;
    }

}
