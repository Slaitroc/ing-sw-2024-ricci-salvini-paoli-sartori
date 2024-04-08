package it.polimi.ingsw.gc31.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualPlayerController extends Remote {
    public void getHand() throws RemoteException;
    public void drawGold() throws RemoteException;
}
