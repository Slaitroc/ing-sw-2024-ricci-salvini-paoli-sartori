package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

public interface IController extends Remote {
    // TODO non sono sicuro
    public void connect(VirtualClient client, String username)
            throws RemoteException, PlayerNicknameAlreadyExistsException;

    public IGameController createGame(String username, int maxNumberPlayers) throws RemoteException;

    public IGameController joinGame(String username, int idGame) throws RemoteException;

    public void getGameList(String username) throws RemoteException, NoGamesException;
}
