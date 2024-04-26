package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualClient extends Remote {
    /**
     * Allows the Controller to set the client's remote PlayerController once
     * initialized locally on the server
     *
     */

    void setGameID(int gameID) throws RemoteException;

    void showCards(List<String> cards) throws RemoteException;

    void showListGame(List<String> listGame) throws RemoteException;

    void showMessage(String msg) throws RemoteException;
}
