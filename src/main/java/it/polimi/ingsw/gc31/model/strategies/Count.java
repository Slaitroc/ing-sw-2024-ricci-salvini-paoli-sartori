package it.polimi.ingsw.gc31.model.strategies;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class represents the objective that gives points based on how many
 * resources are held by a player
 */
public class Count extends Objective {
    /**
     * This attribute represents the resources the player needs to hold to obtain
     * points
     */
    private final List<Resources> resources;

    /**
     * This method is the constructor of the class
     *
     * @param resources is the list of resources the player needs to hold to obtain
     *                  points
     */
    public Count(List<Resources> resources) {
        super();
        this.resources = resources;
    }

    /**
     * This method check if this particular objective is done and return the number
     * of points obtained by the player
     *
     * @param placedCard is the map that contains all the card on the player's board
     * @return the number of points obtained by the player
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint,
                               Map<Resources, Integer> achievedResources) {
        if (resources.size() == 1) {
            return goldenCount(achievedResources);
        }
        if (resources.get(0) == Resources.ANIMAL || resources.get(0) == Resources.PLANT ||
                resources.get(0) == Resources.INSECT || resources.get(0) == Resources.MUSHROOM) {
            return countAPIM(placedCard, achievedResources);
        }
        return countFIS(placedCard, achievedResources);
    }

    /**
     * This method check the objective in the particular case where the list of
     * resources contains only Animal,
     * Plants, Insects and Mushrooms
     *
     * @param placedCard is the map that contains all the card on the player's board
     *                   (it's useless if I get the
     *                   attribute achievedResources)
     * @return the number of points obtained by the player
     */
    private int countAPIM(Map<Point, PlayableCard> placedCard, Map<Resources, Integer> achievedResources) {
        Resources seed = resources.get(0);
        int found = achievedResources.get(seed);
        return 2 * ((found - found % 3) / 3);

        /*
         * useless after achievedResources as a parameter
         * int found = searchOnBoard(placedCard, seed);
         * return 2 * ((found - found % 3) / 3);
         */
    }

    /**
     * This method check the objective in the particular case where the list of
     * resources contains only Feathers, Ink
     * or Scrolls
     *
     * @param placedCard is the map that contains all the card on the player's board
     *                   (it's useless if I get the
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

        return 3 * found;
    }

    /**
     * This method is invoked only if the number of resources to gain points is 1.
     * In this particular case
     * the objective is of a golden card, and the player gains 1 point for every
     * resource of the spicified
     * type is possessed by the player.
     *
     * @param achievedResources is the map that contains the resource as a key and
     *                          the number of occurrences
     *                          of that resources
     * @return the number of points gained by the player
     */
    private int goldenCount(Map<Resources, Integer> achievedResources) {
        return achievedResources.get(resources.get(0));
    }

    @Override
    public String print() {
        StringBuilder res = new StringBuilder();
        res.append(ansi().restoreCursorPosition().cursorMove(2, 3).a(resources.get(0).getSymbol()));
        res.append(ansi().restoreCursorPosition().cursorMove(6, 3).a(resources.get(1).getSymbol()));
        if (resources.size() == 3)
            res.append(ansi().restoreCursorPosition().cursorMove(4, 2).a(resources.get(2).getSymbol()));
        return res.toString();
    }
}
