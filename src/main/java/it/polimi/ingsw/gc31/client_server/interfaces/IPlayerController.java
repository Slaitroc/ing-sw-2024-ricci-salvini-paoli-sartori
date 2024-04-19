package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPlayerController extends Remote {
    public void drawGold() throws RemoteException;
}
