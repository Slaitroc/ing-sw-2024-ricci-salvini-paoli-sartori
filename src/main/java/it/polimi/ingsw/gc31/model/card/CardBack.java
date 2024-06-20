package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the back side of a card.
 * Contains a list of resources to represent the corners of the card and the central resources. It also contains
 * the path of the png image associated with the back of the card.
 */
public class CardBack {
    /**
     * List of resources to represent the corners of the card and the central resources.
     * The corners are identified according to the following convention:
     * 0 -> Up Dx
     * 1 -> Down Dx
     * 2 -> Down Sx
     * 3 -> Up Sx
     * 4-5-6 -> Central resources
     */
    private final List<Resources> resources;
    /**
     * Path of the png image associated with the back of the card.
     */
    private final String dirImg;

    /**
     * Constructs a new back with the specified resources and image directory.
     * @param resources corners of the card
     * @param dirImg path of the png image of the back side of the card
     */
    public CardBack(List<Resources> resources, String dirImg) {
        this.resources = resources;
        this.dirImg = dirImg;
    }

    /**
     * Check if it's possible to place a card on the corner indicated by the parameter.
     *
     * @param corner The index (0 to 3) of the corner to be checked.
     * @return True if it is possible to place a card on that corner, false otherwise.
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
     * Retrieves the path of the image associated with the back side of the card.
     *
     * @return The path of the image.
     */
    public String getImage() {
        return this.dirImg;
    }

    /**
     * Retrieves the list of resources representing all the corners of the card.
     *
     * @return A list of {@code Resources} objects representing the corners of the card.
     */
    public List<Resources> getCorners() {
        return resources;
    }
}
