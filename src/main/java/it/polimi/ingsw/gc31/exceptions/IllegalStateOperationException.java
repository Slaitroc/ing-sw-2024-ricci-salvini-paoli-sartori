package it.polimi.ingsw.gc31.exceptions;

/**
 * Custom exception class for representing an illegal state operation in the game.
 * This exception is thrown when the game is not in the right state to perform a certain operation.
 */
public class IllegalStateOperationException extends Exception {

    public IllegalStateOperationException() {
        super("The game is not in the right state");
    }

}
