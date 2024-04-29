package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayerController extends Remote {
    public void drawGold() throws RemoteException;

    public void drawGoldCard1() throws RemoteException;

    public void drawGoldCard2() throws RemoteException;

    public void drawResource() throws RemoteException;

    public void drawResourceCard1() throws RemoteException;

    public void drawResourceCard2() throws RemoteException;
}
