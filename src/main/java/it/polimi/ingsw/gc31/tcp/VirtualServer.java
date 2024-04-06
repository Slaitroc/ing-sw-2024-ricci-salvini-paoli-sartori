package it.polimi.ingsw.gc31.tcp;

import it.polimi.ingsw.gc31.rmi.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void connect(VirtualView client, String username) throws RemoteException;
    public void getGameList(String username) throws RemoteException;
    public Integer createGame(String username, int maxNumberPlayers) throws RemoteException;
    public void joinGame(String username, Integer idGame) throws RemoteException;
    public void getHand(String username, Integer idGame) throws RemoteException;
    public void drawGold(String username, Integer idGame) throws RemoteException;
}
