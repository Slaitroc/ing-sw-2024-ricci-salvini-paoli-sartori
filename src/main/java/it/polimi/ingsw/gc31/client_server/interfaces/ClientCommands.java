package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.UI;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    void setUI(UI ui);

    void setUsername(String username) throws IOException;

    void createGame(int maxNumberPlayer) throws RemoteException, IOException;

    void joinGame(int gameId) throws RemoteException;

    void getGameList() throws RemoteException, NoGamesException, IOException;

    void setReady(boolean ready) throws RemoteException;

    void drawGold() throws RemoteException;

    void drawGoldCard1() throws RemoteException;

    void drawGoldCard2() throws RemoteException;

    void drawResource() throws RemoteException;

    void drawResourceCard1() throws RemoteException;

    void drawResourceCard2() throws RemoteException;

    void chooseSecretObjective1() throws RemoteException;

    void chooseSecretObjective2() throws RemoteException;

    int getGameID() throws RemoteException;

    String getUsername();

}
