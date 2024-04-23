package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public interface VirtualClient extends Remote {
    /**
     * Allows the Controller to set the client's remote PlayerController once
     * initialized locally on the server
     *
     * @throws RemoteException
     * @Slaitroc
     */

    public void setGameID(int gameID) throws RemoteException;

    public void showCards(List<String> cards) throws RemoteException;

    public void showListGame(List<String> listGame) throws RemoteException;

    public void showMessage(String msg) throws RemoteException;
}
