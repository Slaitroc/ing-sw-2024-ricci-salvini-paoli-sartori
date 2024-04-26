package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import javafx.util.Pair;

public interface VirtualClient extends Remote {
    /**
     * Allows the Controller to set the client's remote PlayerController once
     * initialized locally on the server
     *
     * @throws RemoteException
     * @Slaitroc
     */

    public void setGameID(int gameID) throws RemoteException;

    public void show_handPlayer(String username, List<String> hand) throws RemoteException;
    public void show_scorePlayer(String username, Integer score) throws RemoteException;
    public void show_starterCard(String starterCard) throws RemoteException;
    public void show_objectiveCard(String objectiveCard) throws RemoteException;
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException;
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException;
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException;
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException;

    public void showListGame(List<String> listGame) throws RemoteException;

    public void showMessage(String msg) throws RemoteException;
}
