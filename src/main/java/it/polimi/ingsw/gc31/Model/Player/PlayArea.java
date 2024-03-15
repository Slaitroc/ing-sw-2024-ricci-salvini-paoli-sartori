package it.polimi.ingsw.gc31.Model.Player;
import it.polimi.ingsw.gc31.Model.Card.PlayableCard;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

public class PlayArea {

    private Map<Point, PlayableCard> placedCards;

    //private Integer[] playAreaLimit;  //Actually dannoso a mio avviso o cmq non necessario
    private Map<Resources, Integer> achievedResources;

    PlayArea(){
    }

    public void placeStarter(PlayableCard card){
        placedCards = new HashMap<>();
        Point coordinate = new Point(0, 0);
        placedCards.put(coordinate, card);
        this.achievedResources = new HashMap<>();
        for (Resources r: card.getResources()){
            achievedResources.put(r, achievedResources.get(r)+1);
        }
    }
    public int place(PlayableCard card, Point point){
        if (allowedMove(point)) {
            place(card, point);
            updateAvailableRes(card, point);
        }
        return card.getScore();
    }

    /*private boolean moveQuickCheck(Point point){
        if (point.x >= playAreaLimit[0] || point.x <= playAreaLimit[2]) {
            if (point.y >= playAreaLimit[1] || point.y >= playAreaLimit[3])
                return true;
        }
        return false;
    }*/

    private boolean allowedMove(Point point){
        //Double corner coverage condition!!
        if((point.x + point.y) % 2 != 0) {
            Point newPoint = new Point();
            //Placing new card on NorthEst
            newPoint.x = point.x - 1;
            newPoint.y = point.y - 1;
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).getResources().get(0) != Resources.HIDDEN)
                    return true;
            }
            // Placing new card on SouthEast
            newPoint.x = point.x - 1;
            newPoint.y = point.y + 1;
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).getResources().get(1) != Resources.HIDDEN)
                    return true;
            }
            //Placing new card on SouthWest
            newPoint.x = point.x + 1;
            newPoint.y = point.y + 1;
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).getResources().get(2) != Resources.HIDDEN)
                    return true;
            }
            // Placing new card on NorthWest
            newPoint.x = point.x+1;
            newPoint.y = point.y-1;
            if (placedCards.get(newPoint)!=null) {
                if (placedCards.get(newPoint).getResources().get(2) != Resources.HIDDEN)
                    return true;
            }
        }
        return false;
    }
    private void updateAvailableRes(PlayableCard card, Point point){
        //Add Resources
        for(Resources r: card.getResources()){
            if (r != Resources.HIDDEN)
                achievedResources.put(r, achievedResources.get(r)+1);
            // comando aggiorna risorsa nella mappa delle risorse con il suo valore +1 (a meno che sia HIDDEN)
        }

        //Deleting Resources
        Point newPoint = new Point();

        //Covering NorthEast
        newPoint.x = point.x + 1;
        newPoint.y = point.y + 1;
        if (placedCards.get(newPoint) != null)
            placedCards.get(newPoint).coverCorner(2);

        //Covering SouthEast
        newPoint.x = point.x + 1;
        newPoint.y = point.y - 1;
        if (placedCards.get(newPoint) != null)
            placedCards.get(newPoint).coverCorner(3);

        //Covering SouthWest
        newPoint.x = point.x - 1;
        newPoint.y = point.y - 1;
        if (placedCards.get(newPoint) != null)
            placedCards.get(newPoint).coverCorner(0);

        //Covering NorthWest
        newPoint.x = point.x - 1;
        newPoint.y = point.y + 1;
        if (placedCards.get(newPoint) != null)
            placedCards.get(newPoint).coverCorner(1);
    }
    public Map<Point, PlayableCard> getPlacedCards(){
        return this.placedCards;
    }

}
