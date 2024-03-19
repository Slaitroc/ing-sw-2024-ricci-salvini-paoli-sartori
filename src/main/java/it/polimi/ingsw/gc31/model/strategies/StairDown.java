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
    private Color color;

    /**
     * This method is the constructor of the class
     * @param color is the color requested by the objective
     */
    public StairDown(Color color){
        super();
        this.color=color;
    }

    /**
     * This method check if this particular objective is done
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint){
        int maxX=findMaxX(placedCard), minX=findMinX(placedCard), maxY=findMaxY(placedCard), minY=findMinY(placedCard);

        for(int j = minY; j <= maxY-2; j++) {
            for (int i = minX; i <= maxX-2; i++) {
                Point point = new Point(i,j);
                if(placedCard.get(point)!=null) {

                    if (placedCard.get(point).getColor().equals(color)) {
                        point = new Point(i+1, j+1);
                        if (placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color)) {
                            point = new Point(i+2, j+2);
                            if (placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color)) {
                                score += 2;

                                placedCard.remove(point);
                                point= new Point(i+1, j+1);
                                placedCard.remove(point);
                                point = new Point(i, j);
                                placedCard.remove(point);
                            }
                        }
                    }
                }
            }
        }

        return score;
    }
}
