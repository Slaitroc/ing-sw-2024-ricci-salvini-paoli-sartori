package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPlayerController extends Remote {

    public List<String> getHand() throws RemoteException;

    public void drawGold() throws RemoteException;
}
