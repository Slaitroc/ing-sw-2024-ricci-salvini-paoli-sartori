package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.rmi.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualController extends Remote{
    // TODO non sono sicuro
    public void connect(VirtualView client, String username) throws RemoteException, PlayerNicknameAlreadyExistsException;
    public void getGameList(String username) throws RemoteException;
    public VirtualMainGameController createGame(String username, int maxNumberPlayers) throws RemoteException;
    public VirtualMainGameController joinGame(String username, Integer idGame) throws RemoteException;
}
