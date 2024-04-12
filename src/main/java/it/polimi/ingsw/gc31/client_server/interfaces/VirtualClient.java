package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public interface VirtualClient extends Remote {
    public void setUsername(String n) throws RemoteException;

    public void setPlayerController(IPlayerController playerController) throws RemoteException;

    public void sendMessage(String details) throws RemoteException;

    public void setGameID(int i) throws RemoteException;

    public int getGameID() throws RemoteException;

    public boolean createGame(int i) throws RemoteException;

    public List<String> showGames() throws RemoteException, NoGamesException;

    public void joinGame(int idGame) throws RemoteException;

    public boolean ready() throws RemoteException;

    public List<String> showHand() throws RemoteException;

    public void drawGold() throws RemoteException;

}
