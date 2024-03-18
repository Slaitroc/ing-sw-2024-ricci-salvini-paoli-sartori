package it.polimi.ingsw.gc31.model.player;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.model.Card.Card;
import it.polimi.ingsw.gc31.model.Card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.Enum.Color;.gc31.model.enumeration.Color;

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
