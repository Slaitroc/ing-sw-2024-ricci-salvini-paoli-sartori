package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

/**
 * This class represents the objective that gives points based on how many resources are held by a player
 */
public class Count extends Objective {
    /**
     * This attribute represents the resources the player needs to hold to obtain points
     */
    private final List<Resources> resources;

    /**
     * This method is the constructor of the class
     *
     * @param resources is the list of resources the player needs to hold to obtain points
     */
    public Count(List<Resources> resources) {
        super();
        this.resources = resources;
    }

    /**
     * This method check if this particular objective is done and return the number of points obtained by the player
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint, Map<Resources, Integer> achievedResources) {
        if(resources.size() == 1) {
            return goldenCount(achievedResources);
        }
        if (resources.get(0) == Resources.ANIMAL || resources.get(0) == Resources.PLANT ||
                resources.get(0) == Resources.INSECT || resources.get(0) == Resources.MUSHROOM) {
            return countAPIM(placedCard, achievedResources);
        }
        return countFIS(placedCard, achievedResources);
    }

    /**
     * This method check the objective in the particular case where the list of resources contains only Animal,
     * Plants, Insects and Mushrooms
     *
     * @param placedCard is the map that contains all the card on the player's board (it's useless if I get the
     *                   attribute achievedResources)
     * @return the number of points obtained by the player
     */
    private int countAPIM( Map<Point, PlayableCard> placedCard, Map<Resources, Integer> achievedResources) {
        Resources seed = resources.get(0);
        int found = achievedResources.get(seed);
        return 2 * ((found - found % 3) / 3);

        /* useless after achievedResources as a parameter
            int found = searchOnBoard(placedCard, seed);
            return 2 * ((found - found % 3) / 3);
        */
    }

    /**
     * This method check the objective in the particular case where the list of resources contains only Feathers, Ink
     * or Scrolls
     *
     * @param placedCard is the map that contains all the card on the player's board (it's useless if I get the
     *                   attribute achievedResources)
     * @return the number of points obtained by the player
     */
    private int countFIS(Map<Point, PlayableCard> placedCard, Map<Resources, Integer> achievedResources) {
        Resources seed = null;
        int found = 0;

        if (resources.size() == 2) {
            seed = resources.get(0);
            found = achievedResources.get(seed);
            return 2 * ((found - found % 2) / 2);
        }

        found = achievedResources.get(Resources.FEATHER);
        if (achievedResources.get(Resources.INK) < found)
            found = achievedResources.get(Resources.INK);
        if (achievedResources.get(Resources.SCROLL) < found)
            found = achievedResources.get(Resources.SCROLL);

        return 3 * ((found - found % 3) / 3);
    }

    /* it's useless if I have the attribute achievedResources
    /**
     * This method searches the Resource "seed" on the board
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @param seed       is the resource I want to search on the player's board
     * @return the number of occurrences of seed

     useless after achievedResources as a parameter
    private int searchOnBoard(Map<Point, PlayableCard> placedCard, Resources seed) {
        List<Resources> list = null;
        int count = 0;

        for (Point c : placedCard.keySet()) {
            list = placedCard.get(c).getResources();

            for (Resources value : list) {
                if (value.equals(seed)) {
                    count++;
                }
            }

        }

        return count;
    }
    */

    private int goldenCount(Map<Resources, Integer> achievedResources){
        return achievedResources.get(resources.get(0));
    }
}
