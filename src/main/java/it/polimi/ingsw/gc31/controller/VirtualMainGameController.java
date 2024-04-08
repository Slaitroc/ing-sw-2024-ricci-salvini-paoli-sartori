package it.polimi.ingsw.gc31.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMainGameController extends Remote {
    public boolean isGameStarted() throws RemoteException;
}
