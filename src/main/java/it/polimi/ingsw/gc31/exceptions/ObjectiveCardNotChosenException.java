package it.polimi.ingsw.gc31.exceptions;

/**
 * This class represents an exception that is thrown when a player tries to play their starter card
 * without choosing an objective card first.
 */
public class ObjectiveCardNotChosenException extends Exception {
    public ObjectiveCardNotChosenException() {
        super();
    }
}
