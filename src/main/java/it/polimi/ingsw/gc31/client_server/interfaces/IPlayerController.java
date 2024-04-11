package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayerController extends Remote {
    public void getHand() throws RemoteException;

    public void drawGold() throws RemoteException;
}
