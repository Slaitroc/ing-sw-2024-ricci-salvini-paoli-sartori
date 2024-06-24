package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoTokenException;

public interface VirtualClient extends Remote {

    public boolean isReady() throws RemoteException;

    public void setGameID(int gameID) throws RemoteException;

    public void sendCommand(ClientQueueObject obj) throws RemoteException;

    public void setController(IController controller) throws RemoteException;

    public void setGameController(IGameController gameController) throws RemoteException;

    public void setRmiToken(int token) throws RemoteException;

}
