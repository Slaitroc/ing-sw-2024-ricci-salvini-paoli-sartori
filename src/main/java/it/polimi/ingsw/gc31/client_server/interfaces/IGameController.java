package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote {
    /**
     * Use this for test purposes only
     *
     * @return the GameModel
     * @throws RemoteException
     * @deprecated
     */
    GameModel getModel() throws RemoteException;

    // void checkReady() throws RemoteException, IllegalStateOperationException;

    // public void setReadyStatus(boolean ready, String username) throws
    // RemoteException, IllegalStateOperationException;

    void sendCommand(ServerQueueObject obj) throws RemoteException;

}
