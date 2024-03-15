package it.polimi.ingsw.gc31.Model.Card;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.gc31.Model.Enum.CardType;
import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.*;

/**
 * This is an abstract class that represents a generic Card.
 * All cards have three attributes: {@link CardFront}, {@link CardBack} and side. Side is a boolean parameter
 * that represents which side of the card is active:
 *      side = false -> back is active
 *      side = true -> front is active
 * @author Christian Salvini
 */
public abstract class Card{
    protected final CardFront front;
    protected final CardBack back;
    protected boolean side;

    /**
     * Constructor of the class.
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
    public String getImage() {
        if (side) return front.getImage();
        else return back.getImage();
    }
    public int getScore() {
        return 0;
    }
    abstract public Objective getObjective();
    abstract public JsonObject serializeToJson();

}
