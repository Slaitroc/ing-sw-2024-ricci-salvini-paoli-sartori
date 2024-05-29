package it.polimi.ingsw.gc31.exceptions;

public class IllegalStateOperationException extends Exception {

    public IllegalStateOperationException() {
        super("The game is not in the right state");
    }

}
