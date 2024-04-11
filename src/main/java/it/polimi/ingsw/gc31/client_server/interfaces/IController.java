package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

public interface IController extends Remote {
    // TODO non sono sicuro
    public void connect(VirtualClient client, String username)
            throws RemoteException, PlayerNicknameAlreadyExistsException;

    public List<String> getGameList() throws RemoteException, NoGamesException;

    public IMainGameController createGame(String username, int maxNumberPlayers) throws RemoteException;

    public IMainGameController joinGame(String username, Integer idGame) throws RemoteException;
}
