package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

/**
 * This class represents the Objective where the player needs to have 3 cards placed as an L's reverted shape
 */
public class LShapeReverse extends Objective {
    /**
     * color1 represents the color with more occurrences (2)
     */
    private final Color color1;
    /**
     * color2 represents the color with fewer occurrences (2)
     */
    private final Color color2;

    /**
     * This method is the constructor
     *
     * @param color1 represents the color with more occurrences
     * @param color2 represents the color with fewer occurrences
     */
    public LShapeReverse(Color color1, Color color2) {
        super();
        this.color1 = color1;
        this.color2 = color2;
    }

    /**
     * This method check if this particular objective is done
     *
     * @param placedCard   is the map that contains all the card on the player's board
     * @param uselessPoint is the point of the played card
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint, Map<Resources, Integer> achievedResources) {
        int maxX = findMaxX(placedCard), minX = findMinX(placedCard), maxY = findMaxY(placedCard), minY = findMinY(placedCard);
        int count = 0;

        for (int j = maxY; j >= minY + 2; j--) {
            for (int i = maxX; i >= minX + 1; i--) {
                Point point = new Point(i, j);

                if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color1)) {
                    point.move(i, j - 1);
                    if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color1)) {
                        point.move(i - 1, j - 2);
                        if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color2)) {
                            count += 3;

                            placedCard.remove(point);
                            point.move(i, j - 1);
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
