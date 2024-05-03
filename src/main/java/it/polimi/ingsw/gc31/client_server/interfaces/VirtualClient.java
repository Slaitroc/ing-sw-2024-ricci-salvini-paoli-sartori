package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

public interface VirtualClient extends ShowUpdate {

    public boolean isReady() throws RemoteException;

    public void setGameID(int gameID) throws RemoteException;

    public void showListGame(List<String> listGame) throws RemoteException;

    public void startGame() throws RemoteException;

    public ShowUpdate getUI() throws RemoteException;

}
