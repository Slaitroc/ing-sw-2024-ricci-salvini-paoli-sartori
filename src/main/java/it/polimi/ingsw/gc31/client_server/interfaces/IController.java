package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public interface IController extends Remote {
    // TODO non sono sicuro
    boolean connect(VirtualClient client, String username) throws RemoteException;

    void createGame(String username, int maxNumberPlayers) throws RemoteException;

    void joinGame(String username, int idGame) throws RemoteException;

    void getGameList(String username) throws RemoteException, NoGamesException;

    void sendCommand(ServerQueueObject obj) throws RemoteException;

    VirtualClient getCorrectConnection(int token) throws RemoteException;

    void updateHeartBeat(VirtualClient client) throws RemoteException;
}
