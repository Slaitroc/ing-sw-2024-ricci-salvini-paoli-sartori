package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import static it.polimi.ingsw.gc31.utility.DV.getRgbColor;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class represent the Objective where the player needs to have 3 cards
 * placed diagonally (towards the top)
 */
public class StairUp extends Objective {
    /**
     * This attribute represents the color requested by the objective to be done
     */
    private final CardColor cardColor;

    /**
     * This method is the constructor of the class
     * 
     * @param color is the color requested by the objective
     */
    public StairUp(CardColor color) {
        super();
        this.cardColor = color;
    }

    /**
     * This method check if this particular objective is done
     *
     * @param placedCard SevenReverse
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint,
            Map<Resources, Integer> achievedResources) {
        int maxX = findMaxX(placedCard), minX = findMinX(placedCard), maxY = findMaxY(placedCard),
                minY = findMinY(placedCard);
        int count = 0;
        Point point = new Point(0, 0);

        for (int j = minY; j <= maxY - 2; j++) {
            for (int i = minX; i <= maxX - 2; i++) {
                point.move(i, j);

                if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(cardColor)) {
                    point.move(i + 1, j + 1);
                    if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(cardColor)) {
                        point.move(i + 2, j + 2);
                        if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(cardColor)) {
                            count += 2;

                            placedCard.remove(point);
                            point.move(i + 1, j + 1);
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

    @Override
    public String print() {
        int[] cardColor = getRgbColor(this.cardColor);
        StringBuilder res = new StringBuilder();
        res.append(
                ansi().restoreCursorPosition().fgRgb(cardColor[0], cardColor[1], cardColor[2]).a("      ┌──┐"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(1)
                        .fgRgb(cardColor[0], cardColor[1], cardColor[2]).a("   ┌──⊠──┘"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(2)
                        .fgRgb(cardColor[0], cardColor[1], cardColor[2]).a("┌──⊠──┘"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(3)
                        .fgRgb(cardColor[0], cardColor[1], cardColor[2]).a("└──┘"));
        return res.toString();
    }
}
