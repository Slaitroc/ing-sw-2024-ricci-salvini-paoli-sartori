package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    int getGameID() throws RemoteException;

    void setUsername(String username) throws IOException, PlayerNicknameAlreadyExistsException;

    void createGame(int maxNumberPlayer) throws RemoteException;

    void drawGold() throws RemoteException;

    void joinGame(int gameId) throws RemoteException;

    void getGameList() throws RemoteException, NoGamesException;

    void setUI(UI ui);
}
