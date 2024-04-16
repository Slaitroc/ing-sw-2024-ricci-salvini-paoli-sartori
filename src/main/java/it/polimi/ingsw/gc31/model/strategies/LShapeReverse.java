package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

/**
 * This class represents the Objective where the player needs to have 3 cards placed as an L's reverted shape
 */
public class LShapeReverse extends Objective {
    /**
     * color1 represents the color with more occurrences (2)
     */
    private static final CardColor color1 = CardColor.GREEN;
    /**
     * color2 represents the color with fewer occurrences (2)
     */
    private static final CardColor color2 = CardColor.PURPLE;

    /**
     * This method is the constructor. Changed one line in ObjectiveAdapter (35)
     */
    public LShapeReverse() {
        super();
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
        Point point = new Point(0, 0);

        for (int j = maxY; j >= minY + 2; j--) {
            for (int i = maxX; i >= minX + 1; i--) {
                point.move(i, j);

                if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color1)) {
                    point.move(i, j - 2);
                    if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color1)) {
                        point.move(i - 1, j - 3);
                        if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color2)) {
                            count += 3;

                            placedCard.remove(point);
                            point.move(i, j - 2);
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
