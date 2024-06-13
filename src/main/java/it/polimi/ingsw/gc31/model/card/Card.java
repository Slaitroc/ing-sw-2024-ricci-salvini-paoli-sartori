package it.polimi.ingsw.gc31.model.card;

/**
 * This is an interface that defines the basic methods used by a generic Card.
 * Every card must have an attribute "side" and an image for each side.
 *
 * @author Christian Salvini
 */
public interface Card {

    /**
     * This method is used to determine which side of the card is visible.
     * @return the value of the attribute side
     */
    public boolean getSide();

    /**
     * This method change the side of a card, which means that it change the visible side of a card
     */
    public void changeSide();

    /**
     *
     * @return
     */
    public String getImage();

}
