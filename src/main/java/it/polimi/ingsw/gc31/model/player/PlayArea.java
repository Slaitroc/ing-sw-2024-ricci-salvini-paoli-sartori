package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.awt.*;
import java.util.*;

/**
 * This class represents the play area of a player in the game.
 * It manages the player's placed cards and achieved resources.
 *
 * @author Matteo Paoli
 */
public class PlayArea {

    private final LinkedHashMap<Point, PlayableCard> placedCards;
    private final Map<Resources, Integer> achievedResources;
    private final Point lastPlaced = new Point(0, 0);

    /**
     * Constructor for the PlayArea class.
     * It initializes the placed cards and achieved resources.
     */
    public PlayArea() {
        this.placedCards = new LinkedHashMap<>();
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
     * Places the starter card at (0,0) and updates the achieved resources.
     *
     * @param card the starter card to place.
     */
    public void placeStarter(PlayableCard card) {
        Point point = new Point(0, 0);
        placedCards.put(point, card);
        for (Resources r : card.getResources()) {
            achievedResources.put(r, achievedResources.get(r) + 1);
        }
    }

    /**
     * Checks if the player has enough resources to play the card.
     *
     * @param card the card to check.
     * @return true if the player has enough resources, false otherwise.
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
     * score += hisPlayArea.place(card, point) to add points at his score correctly
     *
     * @param card  the card to place.
     * @param point the point to place the card at.
     * @return the score gained from the card.
     */
    public int place(PlayableCard card, Point point) throws IllegalPlaceCardException {
        if (checkRequirements(card)) {
            if (allowedMove(point)) {
                placedCards.put(new Point(point), card);
                updateAvailableRes(card, point);
                lastPlaced.setLocation(point);
                if (card.getObjective() != null) {
                    return card.getObjective().isObjectiveDone(getPlacedCards(), point, getAchievedResources());
                }
                return card.getScore();
            }
            throw new IllegalPlaceCardException();
        }
        throw new IllegalPlaceCardException();
    }

    /**
     * Return true if move is allowed, false if it is not.
     * Refers to card placement rule only
     * The algorithm starts with the idea that the move is ILLEGAL.
     * It needs to check all four corners of already placed cards that it could be
     * covering.
     * If it finds at least one of those corners HIDDEN, then the move is ILLEGAL.
     * If it finds at least one existing corner not HIDDEN, then it flags the move as
     * POSSIBLE
     * To return true it needs to have all four corners checked
     *
     * @param point the point to check.
     * @return true if the move is allowed, false otherwise.
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
     * its value +1 when adding resources (unless r == HIDDEN).
     * Then it checks all the cards that could have been covered by the new placed
     * card and update the value in the achievedResource map under the key (r)
     * with its value -1.
     * Notice that the condition of covering a not HIDDEN corner has already been
     * checked
     * (Method also calls coverCorner(int) to modify the card value)
     *
     * @param card  the card that is placed.
     * @param point the point where the card is placed.
     */
    private void updateAvailableRes(PlayableCard card, Point point) {
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

    /**
     * @return a copy of the placed cards.
     */
    public LinkedHashMap<Point, PlayableCard> getPlacedCards() {
        return placedCards;
    }

    /**
     * @return a copy of the achieved resources.
     */
    public Map<Resources, Integer> getAchievedResources() {
        return new HashMap<>(achievedResources);
    }
}
