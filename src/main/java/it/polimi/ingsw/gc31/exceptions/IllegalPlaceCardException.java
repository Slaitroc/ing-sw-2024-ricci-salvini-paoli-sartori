package it.polimi.ingsw.gc31.exceptions;

public class IllegalPlaceCardException extends Exception{
    public IllegalPlaceCardException() {
        super("The card cant' be placed");
    }
}
