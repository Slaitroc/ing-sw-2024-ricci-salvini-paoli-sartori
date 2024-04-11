package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IController extends Remote {
    // TODO non sono sicuro
    public void connect(VirtualClient client, String username)
            throws RemoteException, PlayerNicknameAlreadyExistsException;

    public void getGameList(String username) throws RemoteException;

    public IMainGameController createGame(String username, int maxNumberPlayers) throws RemoteException;

    public IMainGameController joinGame(String username, Integer idGame) throws RemoteException;
}
