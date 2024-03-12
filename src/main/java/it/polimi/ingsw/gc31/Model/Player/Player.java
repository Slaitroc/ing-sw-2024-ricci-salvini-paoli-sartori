package it.polimi.ingsw.gc31.Model.Player;
import it.polimi.ingsw.gc31.Model.Card.Card;
import it.polimi.ingsw.gc31.Model.Card.ObjectiveCard;
import it.polimi.ingsw.gc31.Model.Enum.Color;

import java.util.List;

public class Player {
    private String username;
    private PlayArea playArea;
    private final Color pawnColor;
    private List<Card> hand;
    private ObjectiveCard objectiveCard;
    protected int score;

    Player(Color color, String username){
        this.pawnColor = color;
        this.username = username;
    }

    public boolean addToHand(Card card){

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
