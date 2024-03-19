package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;

/**
 * This class represents the Objective where the player gets points based on how many corner are covered
 * by the played card
 */
public class CoverCornerScore extends Objective {

    /**
     * This method is the constructor of the class
     */
    public CoverCornerScore(){
        super();
    }

    /**
     * This method check if this particular objective is done
     * @param placedCard is the map that contains all the card on the player's board
     * @param point is the point of played card
     * @return the number of point obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point point){
        Point altoDx = new Point((int) point.getX() + 1, (int) point.getY() + 1);
        Point altoSx = new Point((int) point.getX() - 1, (int) point.getY() + 1);
        Point bassoDx = new Point((int) point.getX() + 1, (int) point.getY() - 1);
        Point bassoSx = new Point((int) point.getX() - 1, (int) point.getY() - 1);

        if(placedCard.get(altoDx)!=null){ score++; }
        if(placedCard.get(altoSx)!=null){ score++; }
        if(placedCard.get(bassoDx)!=null){ score++; }
        if(placedCard.get(bassoSx)!=null){ score++; }

        return 2 * score;
    }
}
