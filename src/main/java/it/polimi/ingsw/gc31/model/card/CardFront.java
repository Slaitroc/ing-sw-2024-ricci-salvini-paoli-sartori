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
     * @param corner is the index (0 to 3) of the 4 corners of the card (central
     *               resources included from number 4 to 7)
     * @return The type of Resource that has been covered
     */
    public Resources coverCorner(int corner) {
        Resources ret = resources.get(corner);
        resources.set(corner, Resources.HIDDEN);
        return ret;
    }

    public List<Resources> getResources() {
        List<Resources> res = new ArrayList<>();
        for (Resources val : resources) {
            if (val != Resources.HIDDEN && val != Resources.EMPTY) {
                res.add(val);
            }
        }
        return res;
    }

    public Map<Resources, Integer> getRequirements() {
        return requirements;
    }

    public Objective getObjective() {
        return objective;
    }

    public String getImage() {
        return this.dirImg;
    }

    public int getScore() {
        return this.score;
    }

    public List<Resources> getCorners() {
        return resources;
    }
}
