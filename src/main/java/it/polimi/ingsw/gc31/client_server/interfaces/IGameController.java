package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.GameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote {

    void drawGold(String username) throws RemoteException;

    void drawGoldCard1(String username) throws RemoteException;

    void drawGoldCard2(String username) throws RemoteException;

    void drawResource(String username) throws RemoteException;

    void drawResourceCard1(String username) throws RemoteException;

    void drawResourceCard2(String username) throws RemoteException;

    void chooseSecretObjective1(String username) throws RemoteException;

    void chooseSecretObjective2(String username) throws RemoteException;

    void play(String username, int x, int y) throws RemoteException;

    void playStarter(String username) throws RemoteException;

    void changeSide(String username) throws RemoteException;

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

}
