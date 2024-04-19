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
     * @param playerController
     * @throws RemoteException
     * 
     * @Slaitroc
     */

    public void setGameID(int gameID) throws RemoteException;

    public void showHand(List<String> hand) throws RemoteException;

    public void showListGame(List<String> listGame) throws RemoteException;

    public void showMessage(String msg) throws RemoteException;

    public void setPlayerController(IPlayerController playerController) throws RemoteException;

}
