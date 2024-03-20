package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import java.util.*;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayArea {

    private final Map<Point, PlayableCard> placedCards;

    // private Integer[] playAreaLimit; //Actually inefficient in my opinion or
    // unnecessary anyway

    private final Map<Resources, Integer> achievedResources;

    PlayArea() {
        this.placedCards = new HashMap<>();
        this.achievedResources = new HashMap<>();
        achievedResources.put(Resources.MUSHROOM, 0);
        achievedResources.put(Resources.ANIMAL, 0);
        achievedResources.put(Resources.PLANT, 0);
        achievedResources.put(Resources.INSECT, 0);
    }


    /**
     * Create the HashMap, place starter at (0,0)
     * Create a hashMap for the achievedResources and
     * Add the resources to the map itself
     * It could be done calling UpdateAvailableRes, but it would be less efficient
     * and unnecessary being the first card placed
     *
     * @author Matteo Paoli
     */
    public void placeStarter(PlayableCard card) {
        Point point = new Point(0, 0);
        placedCards.put(point, card);
        for (Resources r : card.getResources()) {
            achievedResources.put(r, achievedResources.get(r) + 1);
        }
    }

    /**
     * this method creates a set of keys from the requirements read from the cart
     * than it proceed to slide through them with a for to verify that in the map of
     * achieved resources I have enough of them
     *
     * @author Matteo Paoli
     */
    private boolean checkRequirements(PlayableCard card) {
        if (!card.getRequirements().equals(Collections.emptyMap())) {
            Set<Resources> RequiredRes = card.getRequirements().keySet();
            for (Resources r : RequiredRes) {
                if (achievedResources.get(r) == null) return false;
                if (card.getRequirements().get(r) < achievedResources.get(r)) return false;
            }
        }
        return true;
    }

    /**
     * Firstly it checks out if I have enough Resources to play the card.
     * Then it adds the card in the placedCard Map if the function allowedMove return true.
     * Then return the value of points gained from that card
     * Notice that player will have to call:
     * score += hisPlayArea.place(card, point) to adds points at his score correctly
     *
     * @author Matteo Paoli
     */
    public int place(PlayableCard card, Point point) {
        if (checkRequirements(card)) {
            if (allowedMove(point)) {
                placedCards.put(point, card);
                updateAvailableRes(card, point);
            }
        }
        if (card.getObjective() != null) return card.getObjective().isObjectiveDone(placedCards, point);
        return card.getScore();
    }

    /*
     * skeleton of the quickMoveCheck (unnecessary)
     *
     * private boolean quickMoveCheck(Point point){
     * if (point.x >= playAreaLimit[0] || point.x <= playAreaLimit[2]) {
     * if (point.y >= playAreaLimit[1] || point.y >= playAreaLimit[3])
     * return true;
     * }
     * return false;
     * }
     */

    /**
     * Return true if move is allowed, false if it is not.
     * Refers to card placement rule only
     *
     * @author Matteo Paoli
     */
    private boolean allowedMove(Point point) {
        // Double corner coverage condition !!
        // (Think about it. Sum of coordinates NEEDS to be EVEN, or you are covering 2
        // edges of the same card)
        if ((point.getX() + point.getY()) % 2 != 0) {
            Point newPoint = new Point(point);
            // Placing new card on NorthEst
            newPoint.x = point.x - 1;
            newPoint.y = point.y - 1;
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).checkCorner(0)) return true;
            }
            // Placing new card on SouthEast
            newPoint.x = point.x - 1;
            newPoint.y = point.y + 1;
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).checkCorner(1)) return true;
            }
            // Placing new card on SouthWest
            newPoint.x = point.x + 1;
            newPoint.y = point.y + 1;
            if (placedCards.get(newPoint) != null) {
                if (placedCards.get(newPoint).checkCorner(2)) return true;
            }
            // Placing new card on NorthWest
            newPoint.x = point.x + 1;
            newPoint.y = point.y - 1;
            if (placedCards.get(newPoint) != null) {
                return placedCards.get(newPoint).checkCorner(3);
            }
        }
        return false;
    }

    /**
     * Command update the value in the achievedResource map under the key (r) with
     * its value +1 when adding resources (unless r == HIDDEN)
     * than it checks all the cards that could have been covered by the new placed
     * card and update the value in the achievedResource map under the key (r)
     * with its value -1
     *
     * @author Matteo Paoli
     */
    private void updateAvailableRes(PlayableCard card, Point point) {
        // Adding Resources
        for (Resources newRes : card.getResources()) {
            achievedResources.put(newRes, achievedResources.get(newRes) + 1);
        }

        // Deleting Resources
        Point newPoint = new Point();
        Resources delRes;

        // Covering NorthEast
        newPoint.x = point.x + 1;
        newPoint.y = point.y + 1; // coordinates of the card in the NorthEst position of the one I am placing
        if (placedCards.get(newPoint) != null) {
            delRes = placedCards.get(newPoint).coverCorner(2);
            // Assign to delRes the value of the Resources that im covering

            achievedResources.put(delRes, achievedResources.get(delRes) - 1);
            // Decrement the number of that given resource in the map
        }

        // Covering SouthEast
        newPoint.x = point.x + 1;
        newPoint.y = point.y - 1;
        if (placedCards.get(newPoint) != null) {
            delRes = placedCards.get(newPoint).coverCorner(3);
            achievedResources.put(delRes, achievedResources.get(delRes) - 1);
        }

        // Covering SouthWest
        newPoint.x = point.x - 1;
        newPoint.y = point.y - 1;
        if (placedCards.get(newPoint) != null) {
            delRes = placedCards.get(newPoint).coverCorner(0);
            achievedResources.put(delRes, achievedResources.get(delRes) - 1);
        }

        // Covering NorthWest
        newPoint.x = point.x - 1;
        newPoint.y = point.y + 1;
        if (placedCards.get(newPoint) != null) {
            delRes = placedCards.get(newPoint).coverCorner(1);
            achievedResources.put(delRes, achievedResources.get(delRes) - 1);
        }
    }


    public Map<Point, PlayableCard> getPlacedCards() {
        return new HashMap<>(placedCards);
    }

    public Map<Resources, Integer> getAchievedResources() {
        return new HashMap<>(achievedResources);
    }

}
