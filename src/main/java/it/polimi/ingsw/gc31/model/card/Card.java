package it.polimi.ingsw.gc31.model.card;

import com.google.gson.JsonObject;

import it.polimi.ingsw.gc31.model.strategies.Objective;


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
public interface Card{
    public boolean getSide();
    public void changeSide();
    public String getImage();
    public int getScore();
    abstract public Objective getObjective();
    abstract public Card deepCopy();
    public JsonObject frontSerializeToJson();
    public JsonObject backSerializeToJson();
}
