package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

/**
 * This Class represents the Objective where the player needs to have 3 cards placed diagonally (towards the bottom)
 */
public class StairDown extends Objective {
    /**
     * This attribute represents the color requested by the objective to be done
     */
    private final Color color;

    /**
     * This method is the constructor of the class
     *
     * @param color is the color requested by the objective
     */
    public StairDown(Color color) {
        super();
        this.color = color;
    }

    /**
     * This method check if this particular objective is done
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint) {
        int maxX = findMaxX(placedCard), minX = findMinX(placedCard), maxY = findMaxY(placedCard), minY = findMinY(placedCard);
        int count = 0;

        for (int j = maxY; j >= minY + 2; j--) {
            for (int i = minX; i <= maxX - 2; i++) {
                Point point = new Point(i, j);

                if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color)) {
                    point.move(i + 1, j - 1);
                    if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color)) {
                        point.move(i + 2, j - 2);
                        if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color)) {
                            count += 2;

                            placedCard.remove(point);
                            point.move(i + 1, j - 1);
                            placedCard.remove(point);
                            point.move(i, j);
                            placedCard.remove(point);
                        }
                    }
                }
            }
        }

        return count;
    }
}
