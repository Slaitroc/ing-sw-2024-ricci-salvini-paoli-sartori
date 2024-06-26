package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import static org.fusesource.jansi.Ansi.Color.WHITE;
import static it.polimi.ingsw.gc31.utility.DV.getRgbColor;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class represents the Objective where the player needs to have some Card
 * placed forming the seven's shape in order
 * to obtain points
 */
public class Seven extends Objective {
    /**
     * color1 represents the color with more occurrences (2)
     */
    private final CardColor color1 = CardColor.PURPLE;
    /**
     * color2 represents the color with fewer occurrences (1)
     */
    private final CardColor color2 = CardColor.BLUE;

    /**
     * This method is the constructor of the class. Changed line 43 in
     * ObjectiveAdapter
     */
    public Seven() {
        super();
    }

    /**
     * This method check if this particular objective is done
     *
     * @param placedCard   is the map that contains all the card on the player's
     *                     board
     * @param uselessPoint is the point of the played card
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint,
            Map<Resources, Integer> achievedResources) {
        int maxX = findMaxX(placedCard), minX = findMinX(placedCard), maxY = findMaxY(placedCard),
                minY = findMinY(placedCard);
        int count = 0;
        Point point = new Point(0, 0);

        for (int j = maxY; j >= minY + 2; j--) {
            for (int i = minX; i <= maxX - 1; i++) {
                point.move(i, j);

                if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color2)) {
                    point.move(i + 1, j - 1);
                    if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color1)) {
                        point.move(i + 1, j - 3);
                        if (placedCard.containsKey(point) && placedCard.get(point).getColor().equals(color1)) {
                            count += 3;

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

    @Override
    public String print() {
        StringBuilder res = new StringBuilder();
        int[] color1 = getRgbColor(this.color1);
        int[] color2 = getRgbColor(this.color2);
        res.append(
                ansi().restoreCursorPosition().fgRgb(color2[0], color2[1], color2[2]).a("┌──┐"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(1)
                        .fgRgb(color2[0], color2[1], color2[2]).a("└──").fg(WHITE).a("⊠")
                        .fgRgb(color1[0], color1[1], color1[2]).a("──┐"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(2)
                        .fgRgb(color1[0], color1[1], color1[2]).a("   └──┘"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(3)
                        .fgRgb(color1[0], color1[1], color1[2]).a("   ┌──┐"));
        res.append(
                ansi().restoreCursorPosition().cursorDown(4)
                        .fgRgb(color1[0], color1[1], color1[2]).a("   └──┘"));
        return res.toString();
    }
}
