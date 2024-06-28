package it.polimi.ingsw.gc31.exceptions;

/**
 * This class represents a custom exception that is thrown when the remains only one player in the game.
 */
public class LastPlayerRemainedException extends Exception{
    public final String lastConnected;
    public LastPlayerRemainedException(String lastConnected) {
        this.lastConnected = lastConnected;
    }

}
