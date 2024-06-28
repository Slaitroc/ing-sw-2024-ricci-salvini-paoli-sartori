package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualClient extends Remote {

    boolean isReady() throws RemoteException;

    void setGameID(int gameID) throws RemoteException;

    void sendCommand(ClientQueueObject obj) throws RemoteException;

    void setController(IController controller) throws RemoteException;

    void setGameController(IGameController gameController) throws RemoteException;
}
