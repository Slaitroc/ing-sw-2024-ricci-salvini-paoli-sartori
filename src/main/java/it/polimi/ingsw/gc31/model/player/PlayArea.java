package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;
import java.util.*;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

public class PlayArea {

    private final Map<Point, PlayableCard> placedCards;

    // private Integer[] playAreaLimit; //Actually inefficient in my opinion or
    // unnecessary anyway

    private final Map<Resources, Integer> achievedResources;
    private final Point lastPlaced = new Point(0, 0);

    public PlayArea() {
        this.placedCards = new HashMap<>();
        this.achievedResources = new HashMap<>();
        achievedResources.put(Resources.ANIMAL, 0);
        achievedResources.put(Resources.INSECT, 0);
        achievedResources.put(Resources.INK, 0);
        achievedResources.put(Resources.FEATHER, 0);
        achievedResources.put(Resources.MUSHROOM, 0);
        achievedResources.put(Resources.PLANT, 0);
        achievedResources.put(Resources.SCROLL, 0);
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
            Set<Resources> requiredRes = card.getRequirements().keySet();
            for (Resources r : requiredRes) {
                if (card.getRequirements().get(r) > achievedResources.get(r))
                    return false;
            }
        }
        return true;
    }

    /**
     * Firstly it checks out if I have enough Resources to play the card.
     * Then it adds the card in the placedCard Map if the function allowedMove
     * return true.
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

                if (card.getObjective() != null)
                    return card.getObjective().isObjectiveDone(getPlacedCards(), point, getAchievedResources());
                lastPlaced.setLocation(point);
                return card.getScore();
            }
        }
        return 0;
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
     * The algorithm starts with the idea that the move is illegal.
     * It needs to check all 4 corners of already placed cards that it could be
     * covering
     * if it finds at least 1 of those corner HIDDEN than the move is ILLEGAL
     * if it finds at least 1 existing corner not HIDDEN than it flags the move as
     * POSSIBLE
     * To return true it needs to have all 4 corners checked
     *
     * @author Matteo Paoli
     */
    private boolean allowedMove(Point point) {
        // Double corner coverage condition !!
        // (Think about it. Sum of coordinates NEEDS to be EVEN, or you are covering 2
        // edges of the same card)
        boolean possibleMove = false;
        if ((point.getX() + point.getY()) % 2 == 0) {
            if (placedCards.get(point) == null) {
                Point alreadyPlaced = new Point(point);

                // Placing new card on NorthEst
                alreadyPlaced.setLocation(point.getX() - 1, point.getY() - 1);
                if (placedCards.get(alreadyPlaced) != null) {
                    if (!placedCards.get(alreadyPlaced).checkCorner(0))
                        return false;
                    possibleMove = true;
                }
                // Placing new card on SouthEast
                alreadyPlaced.setLocation(point.getX() - 1, point.getY() + 1);
                if (placedCards.get(alreadyPlaced) != null) {
                    if (!placedCards.get(alreadyPlaced).checkCorner(1))
                        return false;
                    possibleMove = true;
                }
                // Placing new card on SouthWest
                alreadyPlaced.setLocation(point.getX() + 1, point.getY() + 1);
                if (placedCards.get(alreadyPlaced) != null) {
                    if (!placedCards.get(alreadyPlaced).checkCorner(2))
                        return false;
                    possibleMove = true;
                }
                // Placing new card on NorthWest
                alreadyPlaced.setLocation(point.getX() + 1, point.getY() - 1);
                if (placedCards.get(alreadyPlaced) != null) {
                    if (!placedCards.get(alreadyPlaced).checkCorner(3))
                        return false;
                    possibleMove = true;
                }
            }
        }
        return possibleMove;
    }

    /**
     * Command update the value in the achievedResource map under the key (r) with
     * its value +1 when adding resources (unless r == HIDDEN)
     * than it checks all the cards that could have been covered by the new placed
     * card and update the value in the achievedResource map under the key (r)
     * with its value -1
     * Notice that the condition of covering a not HIDDEN corner has already been
     * checked
     * (Method also calls .coverCorner(int) to modify the card value)
     *
     * @author Matteo Paoli
     */
    protected void updateAvailableRes(PlayableCard card, Point point) {
        // Adding Resources
        for (Resources newRes : card.getResources()) {
            achievedResources.put(newRes, achievedResources.get(newRes) + 1);
        }

        // Deleting Resources
        Point alreadyPlaced = new Point();
        Resources delRes;

        // Covering NorthEast
        alreadyPlaced.setLocation(point.getX() + 1, point.getY() + 1);
        // coordinates of the card in the NorthEst position of the one I am placing

        if (placedCards.get(alreadyPlaced) != null) {
            delRes = placedCards.get(alreadyPlaced).coverCorner(2);
            // Assign to delRes the value of the Resources that im covering

            if (delRes != Resources.EMPTY)
                achievedResources.put(delRes, achievedResources.get(delRes) - 1);
            // Decrement the number of that given resource in the map
        }

        // Covering SouthEast
        alreadyPlaced.setLocation(point.getX() + 1, point.getY() - 1);
        if (placedCards.get(alreadyPlaced) != null) {
            delRes = placedCards.get(alreadyPlaced).coverCorner(3);
            if (delRes != Resources.EMPTY)
                achievedResources.put(delRes, achievedResources.get(delRes) - 1);
        }

        // Covering SouthWest
        alreadyPlaced.setLocation(point.getX() - 1, point.getY() - 1);
        if (placedCards.get(alreadyPlaced) != null) {
            delRes = placedCards.get(alreadyPlaced).coverCorner(0);
            if (delRes != Resources.EMPTY)
                achievedResources.put(delRes, achievedResources.get(delRes) - 1);
        }

        // Covering NorthWest
        alreadyPlaced.setLocation(point.getX() - 1, point.getY() + 1);
        if (placedCards.get(alreadyPlaced) != null) {
            delRes = placedCards.get(alreadyPlaced).coverCorner(1);
            if (delRes != Resources.EMPTY)
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
