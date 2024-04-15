package it.polimi.ingsw.gc31.tcp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void connect(String username) throws RemoteException;

    public void getGameList(String username) throws RemoteException;

    public void createGame(String username, int maxNumberPlayers) throws RemoteException;

    public void joinGame(String username, Integer idGame) throws RemoteException;

    public void getHand(String username, Integer idGame) throws RemoteException;

    public void drawGold(String username, Integer idGame) throws RemoteException;

    public void info();
}
