package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

public interface VirtualClient extends Remote {

    public boolean isReady() throws RemoteException;

    public void setGameID(int gameID) throws RemoteException;

    public void showListGame(List<String> listGame) throws RemoteException;

    // public void startGame() throws RemoteException;

    public ShowUpdate getUI() throws RemoteException;

    public void sendCommand(ClientQueueObject obj) throws RemoteException;

}
