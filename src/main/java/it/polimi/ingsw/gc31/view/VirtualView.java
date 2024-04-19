package it.polimi.ingsw.gc31.view;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualView {
    public void showHand(List<String> hand) throws RemoteException;

    public void showListGame(List<String> listGame) throws RemoteException;

    public void showMessage(String msg) throws RemoteException;
}
