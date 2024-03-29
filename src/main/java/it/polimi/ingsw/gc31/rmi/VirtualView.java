package it.polimi.ingsw.gc31.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualView extends Remote {
    public void showHand(List<String> jsonHand) throws RemoteException;
    public void showGameList(List<String> gameList) throws RemoteException;
    public void reportError(String details) throws RemoteException;

}
