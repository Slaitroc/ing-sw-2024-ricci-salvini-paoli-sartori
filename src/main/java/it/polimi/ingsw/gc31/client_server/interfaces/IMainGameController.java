package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMainGameController extends Remote {
    public boolean isGameStarted() throws RemoteException;
}
