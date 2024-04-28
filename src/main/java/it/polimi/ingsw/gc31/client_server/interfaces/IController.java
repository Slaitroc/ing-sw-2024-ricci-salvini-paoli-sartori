package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

public interface IController extends Remote {
    // TODO non sono sicuro
    void connect(VirtualClient client, String username) throws RemoteException, PlayerNicknameAlreadyExistsException;

    IGameController createGame(String username, int maxNumberPlayers) throws RemoteException;

    IGameController joinGame(String username, int idGame) throws RemoteException;

    void getGameList(String username) throws RemoteException, NoGamesException;
}
