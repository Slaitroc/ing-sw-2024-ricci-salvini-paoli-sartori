package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.*;

/**
 * This class represents the front side of card;
 *
 * @author Christian Salvini
 */
public class CardFront {
    /**
     * The score obtained from placing the card.
     */
    private final int score;
    /**
     * List of Resources that represents the corners of the card's Front. The
     * corners are identified according
     * to the following convention:
     * 0-> Up Dx
     * 1-> Down Dx
     * 2-> Down Sx
     * 3-> Up Sx
     * It can have a maximum of 4 elements.
     */
    private final List<Resources> resources;
    /**
     * The resources the Player must have in their playArea to be able to place the
     * card.
     */
    private final Map<Resources, Integer> requirements;
    /**
     * link to the image of the front side
     */
    private final String dirImg;
    /**
     * Objective of the card to be verified in order to obtain the score points.
     */
    private final Objective objective;

    public CardFront(int score, List<Resources> resources, Map<Resources, Integer> requirements, String dirImg,
            Objective ob)
    {
        this.score = score;

        this.resources = resources;
        this.requirements = requirements;

        this.dirImg = dirImg;
        this.objective = ob;
    }

    /**
     * Check if it's possible to place a card on the corner indicated by the
     * parameter Corner
     *
     * @param corner corner to be checked
     * @return true if it is possible to place a card on that corner, false
     *         otherwise
     */
    public boolean checkCorner(int corner) {
        return resources.get(corner) != Resources.HIDDEN;
    }

    /**
     * Sets the corner to hidden because a card covering it has been placed
     *
     * @param corner The index (0 to 3) of the corner to be covered.
     * @return The type of resource that has been covered.
     */
    public Resources coverCorner(int corner) {
        Resources ret = resources.get(corner);
        resources.set(corner, Resources.HIDDEN);
        return ret;
    }

    /**
     * Returns a list of non-hidden and non-empty resources available in the card back.
     *
     * @return a list of {@code Resources} objects representing the available resources
     */
    public List<Resources> getResources() {
        List<Resources> res = new ArrayList<>();
        for (Resources val : resources) {
            if (val != Resources.HIDDEN && val != Resources.EMPTY) {
                res.add(val);
            }
        }
        return res;
    }

    /**
     * Retrieves the requirements of the card.
     *
     * @return A map of Resources and their required quantity.
     */
    public Map<Resources, Integer> getRequirements() {
        return requirements;
    }

    /**
     * Retrieves the objective associated with the card.
     *
     * @return The objective of the card.
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * Retrieves the path of the image associated with the back side of the card.
     *
     * @return The path of the image.
     */
    public String getImage() {
        return this.dirImg;
    }

    /**
     * Retrieves the score of the card if the objective is satisfied.
     *
     * @return The score of the card.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Retrieves the list of resources representing the corners of the card.
     *
     * @return A list of {@code Resources} objects representing the corners of the card.
     */
    public List<Resources> getCorners() {
        return resources;
    }
}
