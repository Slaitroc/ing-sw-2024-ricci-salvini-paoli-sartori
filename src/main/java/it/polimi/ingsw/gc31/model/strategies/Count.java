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
     * @param resources the list of resources the player needs to hold to obtain points
     */
    public Count(List<Resources> resources) {
        super();
        this.resources = resources;
    }

    /**
     * This method check if this particular objective is done
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint) {
        if (resources.get(0) == Resources.ANIMAL || resources.get(0) == Resources.PLANT ||
                resources.get(0) == Resources.INSECT || resources.get(0) == Resources.MUSHROOM) {
            return countAPIM(placedCard);
        }
        return countFIS(placedCard);
    }

    /**
     * This method check the objective in the particular case where the list of resources contains only Animal,
     * Plants, Insects and Mushrooms
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    private int countAPIM(Map<Point, PlayableCard> placedCard) {
        Resources seed = resources.get(0);
        int found = searchOnBoard(placedCard, seed);
        return 2 * ((found - found % 3) / 3);
    }

    /**
     * This method check the objective in the particular case where the list of resources contains only Feathers, Ink
     * or Scrolls
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    private int countFIS(Map<Point, PlayableCard> placedCard) {
        Resources seed = null;
        int found = 0;

        if (resources.size() == 2) {
            seed = resources.get(0);
            found = searchOnBoard(placedCard, seed);

            return 2 * ((found - found % 2) / 2);
        }

        found = searchOnBoard(placedCard, Resources.FEATHER);
        if (searchOnBoard(placedCard, Resources.INK) < found)
            found = searchOnBoard(placedCard, Resources.INK);
        if (searchOnBoard(placedCard, Resources.SCROLL) < found)
            found = searchOnBoard(placedCard, Resources.SCROLL);

        return 3 * ((found - found % 3) / 3);
    }

    /**
     * This method searches the Resource "seed" on the board
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @param seed       is the resource I want to search on the player's board
     * @return the number of occurrences of seed
     */
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
}
