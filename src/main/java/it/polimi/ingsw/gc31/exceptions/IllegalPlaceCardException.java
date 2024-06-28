package it.polimi.ingsw.gc31.exceptions;

/**
 * This exception is thrown when a card cannot be placed.
 */
public class IllegalPlaceCardException extends Exception{
    public IllegalPlaceCardException() {
        super("The card cant' be placed");
    }
}
