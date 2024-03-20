package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;

/**
 * This Class represents the generic Objective of the Card
 */
public abstract class Objective {
    /**
     * This method is the constructor of the class
     */
    public Objective() {
    }

    /**
     * This method check if the objective is done by the player
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @param point      is the point of the played card
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point point) {
        return 0;
    }

    /**
     * This method find the max X on the board
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the value of the max X found
     */
    protected int findMaxX(Map<Point, PlayableCard> placedCard) {
        int maxX = 0;
        for (Point c : placedCard.keySet()) {
            if (maxX < c.getX()) {
                maxX = (int) c.getX();
            }
        }
        return maxX;
    }

    /**
     * This method find the max Y on the board
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the value of the max Y found
     */
    protected int findMaxY(Map<Point, PlayableCard> placedCard) {
        int maxY = 0;
        for (Point c : placedCard.keySet()) {
            if (maxY < c.getY()) {
                maxY = (int) c.getY();
            }
        }
        return maxY;
    }

    /**
     * This method finds the min X on the board
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the value of the min X found
     */
    protected int findMinX(Map<Point, PlayableCard> placedCard) {
        int minX = 0;
        for (Point c : placedCard.keySet()) {
            if (minX > c.getX()) {
                minX = (int) c.getX();
            }
        }
        return minX;
    }

    /**
     * This method finds the min Y on the board
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the value of the min Y found
     */
    protected int findMinY(Map<Point, PlayableCard> placedCard) {
        int minY = 0;
        for (Point c : placedCard.keySet()) {
            if (minY > c.getY()) {
                minY = (int) c.getY();
            }
        }
        return minY;
    }
}

