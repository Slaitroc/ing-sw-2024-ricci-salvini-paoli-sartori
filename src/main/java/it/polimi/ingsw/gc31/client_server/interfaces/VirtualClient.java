package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualClient extends Remote {
    public void setPlayerController(IPlayerController playerController) throws RemoteException;

    public void showHand(List<String> jsonHand) throws RemoteException;

    public void showGameList(List<String> gameList) throws RemoteException;

    public void sendMessage(String details) throws RemoteException;

    public void setGameID(int i) throws RemoteException;
}
