package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote {

    void drawGoldCard1(String username) throws RemoteException;

    void drawGoldCard2(String username) throws RemoteException;

    void drawResource(String username) throws RemoteException;

    void drawResourceCard1(String username) throws RemoteException;

    void drawResourceCard2(String username) throws RemoteException;

    void changeSide(String username) throws RemoteException;

    void changeStarterSide(String username) throws RemoteException;

    void selectCard(String username, int index) throws RemoteException;

    /**
     * Use this for test purposes only
     * 
     * @deprecated
     * @return the GameModel
     * @throws RemoteException
     */
    GameModel getModel() throws RemoteException;

    void checkReady() throws RemoteException, IllegalStateOperationException;

    public void setReadyStatus(boolean ready, String username) throws RemoteException, IllegalStateOperationException;

    public void sendCommand(ServerQueueObject obj) throws RemoteException;

}
