package it.polimi.ingsw.gc31.model.card;

import com.google.gson.JsonObject;

import it.polimi.ingsw.gc31.model.strategies.Objective;
import it.polimi.ingsw.gc31.utility.DeepCopy;


/**
 * This is an abstract class that represents a generic Card.
 * All cards have three attributes: {@link CardFront}, {@link CardBack} and
 * side. Side is a boolean parameter
 * that represents which side of the card is active:
 * side = false -> back is active
 * side = true -> front is active
 *
 * @author Christian Salvini
 */
public abstract class Card{
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
     * side = false -> back is active
     * side = true -> front is active
     */
    protected boolean side;

    /**
     * Constructor of a Card.
     * All the cards are initially set to false
     */
    public Card(CardFront front, CardBack back) {
        this.front = front;
        this.back = back;
        side = false;
    }

    /**
     * @return which side of the card is active
     */
    public boolean getSide() {
        return side;
    }

    /**
     * change the side of the card to the opposite value
     */

    public void changeSide() {
        side = !side;
    }

    /**
     * @return the link to the image related to the active side
     */
    public String getImage() {
        if (side)
            return front.getImage();
        else
            return back.getImage();
    }
    /**
     * @return the score obtained with the placement of the card. If back is active, always return 0.
     */
    public int getScore() {
        if (side)
            return front.getScore();
        else return 0;
    }

    /**
     * @return return the {@link Objective} of a card that must be verified to obtain
     * the score.
     */
    abstract public Objective getObjective();
    abstract public Card deepCopy();
}
