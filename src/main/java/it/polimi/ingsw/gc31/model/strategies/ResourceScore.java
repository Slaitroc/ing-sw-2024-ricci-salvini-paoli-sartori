package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

/**
 * This class represents the objective where the player obtain points based on how many resources he holds of a
 * specific type
 */
public class ResourceScore extends Objective {
    /**
     * This attribute represents the resource the player needs to have to obtain points
     */
    private Resources resource;

    /**
     * This method is the constructor of the class
     * @param resource is the specific Resource requested to obtain points
     */
    public ResourceScore(Resources resource){
        super();
        this.resource=resource;
    }

    /**
     * This method check if this particular objective is done
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint) {
        for (Point c : placedCard.keySet()) {
            for (int i=0; i >= 0 && i < placedCard.get(c).getResources().size(); i++) {
                if (resource.equals(placedCard.get(c).getResources().get(i))) {
                    score++;
                }
            }
        }
        return score;
    }
}
