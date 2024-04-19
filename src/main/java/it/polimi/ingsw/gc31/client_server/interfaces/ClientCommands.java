package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

import java.rmi.RemoteException;

public interface ClientCommands {
    public void setUsername(String username);
    public void createGame(int maxNumberPlayer) throws RemoteException;
    public void drawGold() throws RemoteException;
    public void joinGame(int gameId) throws RemoteException;
    public void getGameList() throws RemoteException, NoGamesException;
    public void setPlayerController(IPlayerController playerController) throws RemoteException;
    public void run(UI ui, String username) throws RemoteException, PlayerNicknameAlreadyExistsException;
}
