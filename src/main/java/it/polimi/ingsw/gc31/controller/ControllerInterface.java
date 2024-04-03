package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.rmi.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControllerInterface extends Remote {
    public void joinGame(String username, VirtualView client) throws RemoteException;
    public void getHand(String username) throws RemoteException;
    public void drawGold(String username) throws RemoteException;
}
