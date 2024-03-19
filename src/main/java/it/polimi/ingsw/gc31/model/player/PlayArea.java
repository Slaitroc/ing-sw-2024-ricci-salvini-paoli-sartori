package it.polimi.ingsw.gc31.model.player;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

public class PlayArea {

    private Map<Point, PlayableCard> placedCards;

    //private Integer[] playAreaLimit;  //Actually inefficient in my opinion or unnecessary anyway
    private Map<Resources, Integer> achievedResources;

    PlayArea(){
    }


    //Create the HashMap, place starter at (0,0)
    //Create a hashMap for the achievedResources and
    //Add the resources to the map itself
    //It could be done calling UpdateAvailableRes, but it would be less efficient
    // and unnecessary being the first card placed
    public void placeStarter(PlayableCard card){
        placedCards = new HashMap<>();
        Point point = new Point(0, 0);
        placedCards.put(point, card);
        this.achievedResources = new HashMap<>();
        for (Resources r: card.getResources()){
            achievedResources.put(r, achievedResources.get(r)+1);
        }
    }

    // this method creates a set of keys from the requirements read from the card
    // than it proceed to slide them with a for to verify that in the map of achieved resources,
    // I have enough of them
    private boolean checkRequirements (PlayableCard card){
        Set<Resources> RequiredRes = card.getRequirements().keySet();
        for(Resources r: RequiredRes){
            if(achievedResources.get(r)==null) return false;
            if (card.getRequirements().get(r) < achievedResources.get(r)) return false;
        }
        return true;
    }

    //Firstly it checks out if I have enough Resources to play the card.
    //Then it adds the card in the placedCard Map if the function allowedMove return true.
    //Then return the value of points gained from that card
    //Notice that player will have to call:
    // score += hisPlayArea.place(card, point) to adds points at his score correctly
    public int place(PlayableCard card, Point point){
        if (checkRequirements(card)) {
            if (allowedMove(point)) {
                placedCards.put(point, card);
                updateAvailableRes(card, point);
            }
        }
        if (card.getObjective()!=null)
            return card.getObjective().isObjectiveDone(placedCards, point);
        return card.getScore();
    }

    /*
    //skeleton of the quickMoveCheck (unnecessary)

    private boolean quickMoveCheck(Point point){
        if (point.x >= playAreaLimit[0] || point.x <= playAreaLimit[2]) {
            if (point.y >= playAreaLimit[1] || point.y >= playAreaLimit[3])
                return true;
        }
        return false;
    }*/

    //Return true if move is allowed, false if it is not.
    //Refers to card placement rule only
    private boolean allowedMove(Point point){
        //Double corner coverage condition !!
        //(Think about it. Sum of coordinates NEEDS to be EVEN, or you are covering 2 edges of the same card)
        if((point.getX() + point.getY()) % 2 != 0) {
            Point newPoint = new Point(point);
            //Placing new card on NorthEst
            newPoint.move((int) point.getX()-1, (int) point.getY()-1);
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).getResources().get(0) != Resources.HIDDEN)
                    return true;
            }
            // Placing new card on SouthEast
            newPoint.move((int) point.getX()-1, (int) point.getY()+1);
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).getResources().get(1) != Resources.HIDDEN)
                    return true;
            }
            //Placing new card on SouthWest
            newPoint.move((int) point.getX()+1, (int) point.getY()+1);
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).getResources().get(2) != Resources.HIDDEN)
                    return true;
            }
            // Placing new card on NorthWest
            newPoint.move((int) point.getX()+1, (int) point.getY()-1);
            if (placedCards.get(newPoint)!=null) {
                if (placedCards.get(newPoint).getResources().get(2) != Resources.HIDDEN)
                    return true;
            }
        }
        return false;
    }

    //Command update the value in the achievedResource map under the key (r) with its value +1
    //when adding resources (unless r == HIDDEN)
    //than it checks all the cards that could have been covered by the new placed card and
    //update the value in the achievedResource map under the key (r) with its value -1
    private void updateAvailableRes(PlayableCard card, Point point){
        //Adding Resources
        for(Resources r: card.getResources()){
            if (r != Resources.HIDDEN)
                achievedResources.put(r, achievedResources.get(r)+1);
        }

        //Deleting Resources
        Point newPoint = new Point();
        Resources r;

        //Covering NorthEast
        newPoint.x = point.x + 1;
        newPoint.y = point.y + 1; //coordinates of the card in the NorthEst position of the one I am placing
        if (placedCards.get(newPoint) != null) {
            r = placedCards.get(newPoint).getResources().get(2);  //Assign to r the value of the Resources that im covering
            achievedResources.put(r, achievedResources.get(r) - 1); //Decrement the number of that given resource in the map
            placedCards.get(newPoint).coverCorner(2);  //CoverCorner of the card
        }

        //Covering SouthEast
        newPoint.x = point.x + 1;
        newPoint.y = point.y - 1;
        if (placedCards.get(newPoint) != null){
            r = placedCards.get(newPoint).getResources().get(3);
            achievedResources.put(r, achievedResources.get(r) - 1);
            placedCards.get(newPoint).coverCorner(3);
        }

        //Covering SouthWest
        newPoint.x = point.x - 1;
        newPoint.y = point.y - 1;
        if (placedCards.get(newPoint) != null) {
            r = placedCards.get(newPoint).getResources().get(0);
            achievedResources.put(r, achievedResources.get(r) - 1);
            placedCards.get(newPoint).coverCorner(0);
        }

        //Covering NorthWest
        newPoint.x = point.x - 1;
        newPoint.y = point.y + 1;
        if (placedCards.get(newPoint) != null) {
            r = placedCards.get(newPoint).getResources().get(1);
            achievedResources.put(r, achievedResources.get(r) - 1);
            placedCards.get(newPoint).coverCorner(1);
        }
    }


    public Map<Point, PlayableCard> getPlacedCards(){
        return this.placedCards;
    }

    public Map<Resources, Integer> getAchievedResources(){
        return this.achievedResources;
    }

}
