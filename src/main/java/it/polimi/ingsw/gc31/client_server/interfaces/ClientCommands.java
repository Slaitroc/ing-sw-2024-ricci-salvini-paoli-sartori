package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    int getGameID() throws RemoteException;

    void setUsername(String username) throws IOException, PlayerNicknameAlreadyExistsException;

    void createGame(int maxNumberPlayer) throws RemoteException, IOException;

    void drawGold() throws RemoteException;

    void drawResource() throws RemoteException;

    void joinGame(int gameId) throws RemoteException;

    void getGameList() throws RemoteException, NoGamesException, IOException;

    void setUI(UI ui);

    void setReady(boolean ready) throws RemoteException;

    String getUsername();
}
