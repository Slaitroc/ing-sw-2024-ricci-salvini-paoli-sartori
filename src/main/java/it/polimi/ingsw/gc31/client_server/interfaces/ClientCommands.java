package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    public int getGameID() throws RemoteException;

    public void setUsername(String username) throws IOException, PlayerNicknameAlreadyExistsException;

    public void createGame(int maxNumberPlayer) throws IOException;

    public void drawGold() throws RemoteException;

    public void joinGame(int gameId) throws RemoteException;

    public void getGameList() throws IOException, NoGamesException;

    public void setUI(UI ui);
}
