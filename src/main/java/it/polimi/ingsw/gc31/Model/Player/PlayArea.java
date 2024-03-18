package it.polimi.ingsw.gc31.Model.Player;
import it.polimi.ingsw.gc31.Model.Card.Card;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import java.awt.Point;
import javafx.scene.effect.Light;

import java.util.HashMap;
import java.util.Map;

public class PlayArea {

    private Player playerObj;
    private Map<Point, Card> placedCards;
    private Point[] playAreaLimit;
    private Map<Resources, Integer> achivedResources;

    PlayArea(Player player){
        this.playerObj=player;
    };

    public void placeStarter(Card card){
        placedCards = new HashMap<Point, Card>();
        Point coordinate = new Point(0, 0);
        placedCards.put(coordinate, card);
    };
    public int place(Card card, Point point){
        if (allowedMove(point)){
            place(card, point);
            playerObj.score += card.getScore();
            return playerObj.score;
        }
        else return playerObj.score;
    };
    private boolean allowedMove(Point point){
        return true;//FIX

    };
    private void updatePlayableArea(Point point){

    };
    private void updateAvaiableRes(Card card){

    };
    public Map<Point, Card> getPlacedCards(){
        return null; //FIX

    }

}
