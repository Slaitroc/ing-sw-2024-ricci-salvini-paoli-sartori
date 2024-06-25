package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents a generic card that can be placed on the playArea. It extends {@link Card}
 * All PlayableCard must extend this class.
 *
 * @author Christian Salvini
 */
public abstract class PlayableCard implements Card {
    /**
     * The front side of a Card
     */
    protected final CardFront front;
    /**
     * The back side of a Card
     */
    protected final CardBack back;
    /**
     * Side is a boolean parameter that represents which side of the card is active:
     * side = false → back is active
     * side = true → front is active
     */
    protected boolean side;
    /**
     * It represents the color of a card. It must be set by subclasses, and could be
     * set to null.
     * It is final because the color of the card can't change.
     */
    protected final CardColor cardColor;

    /**
     * The constructor of the PlayableCard.
     */
    public PlayableCard(CardColor cardColor, CardFront front, CardBack back) {
        this.front = front;
        this.back = back;
        this.side = false;
        this.cardColor = cardColor;
    }

    /**
     * Retrieves the score of the card.
     *
     * @return The score of the card. If the front side of the card is active,
     * it returns the score from the front side. Otherwise, it returns 0.
     */
    public int getScore() {
        if (side)
            return front.getScore();
        else
            return 0;
    }

    /**
     * @return the color of the card
     */
    public CardColor getColor() {
        return cardColor;
    }

    /**
     * Check if it's possible to place a card on the corner indicated by the parameter.
     * If side is true call checkCorner on front, otherwise call checkCorner on back.
     *
     * @param corner The index (0 to 3) of the corner to be checked.
     * @return True if it is possible to place a card on that corner, false otherwise.
     */
    public boolean checkCorner(int corner) {
        if (side)
            return front.checkCorner(corner);
        else
            return back.checkCorner(corner);
    }

    /**
     * Sets the corner of the active side to hidden after a card covering it has been placed
     * If side is true call coverCorner on front, otherwise call coverCorner on back.
     *
     * @param corner The index (0 to 3) of the corner to be covered.
     * @return The type of resource that has been covered.
     */
    public Resources coverCorner(int corner) {
        if (side)
            return front.coverCorner(corner);
        else
            return back.coverCorner(corner);
    }

    /**
     * Retrieves the list of resources representing all the corners of the active side of the card.
     *
     * @return A list of {@code Resources} objects representing the corners of the card.
     */
    public List<Resources> getCorners() {
        if (side)
            return front.getCorners();
        else
            return back.getCorners();
    }

    /**
     * Returns a list of non-hidden and non-empty resources available in the active side of the card.
     *
     * @return a list of {@code Resources} objects representing the available resources
     */
    public List<Resources> getResources() {
        if (side)
            return front.getResources();
        else
            return back.getResources();
    }

    /**
     * Retrieves the requirements of the card only if the card is on front side. If the card is
     * on back side return an empty map.
     *
     * @return A map of Resources and their required quantity.
     */
    public Map<Resources, Integer> getRequirements() {
        if (side)
            return front.getRequirements();
        else
            return Collections.emptyMap();
    }

    /**
     * Retrieves the objective associated with the card if only card is on front side.
     * If the card is on back side return null.
     *
     * @return The objective of the card.
     */
    public Objective getObjective() {
        if (side)
            return front.getObjective();
        else
            return null;
    }

    /**
     * Retrieves the value of the "side" field.
     *
     * @return The value of the "side" field. True if the front side of the card is active, false if the back side is active.
     */
    @Override
    public boolean getSide() {
        return side;
    }

    /**
     * Changes the active side of the card.
     * If the current side is true, it will change it to false.
     * If the current side is false, it will change it to true.
     */
    @Override
    public void changeSide() {
        side = !side;
    }

    /**
     * Retrieves the path of the image associated with the active side of the card.
     *
     * @return The path of the image.
     */
    @Override
    public String getImage() {
        if (side)
            return front.getImage();
        else
            return back.getImage();
    }
}
