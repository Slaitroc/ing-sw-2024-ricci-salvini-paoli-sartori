package it.polimi.ingsw.gc31.exceptions;

public class LastPlayerRemainedException extends Exception{
    public final String lastConnected;
    public LastPlayerRemainedException(String lastConnected) {
        this.lastConnected = lastConnected;
    }

}
