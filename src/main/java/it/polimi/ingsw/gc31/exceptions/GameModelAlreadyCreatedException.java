package it.polimi.ingsw.gc31.exceptions;

/**
 * The GameModelAlreadyCreatedException is thrown when an attempt is made to create a new GameModel instance when one already exists.
 * This exception extends the Exception class and inherits its methods and attributes.
 */
public class GameModelAlreadyCreatedException extends Exception {
    public GameModelAlreadyCreatedException() {
        super();
    }

}
