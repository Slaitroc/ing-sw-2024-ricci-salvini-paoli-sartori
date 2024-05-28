package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.UI;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    void setUI(UI ui);

    void setUsernameCall(String username) throws IOException;

    void setUsernameResponse(String username);

    void createGame(int maxNumberPlayer) throws RemoteException, IOException;

    void joinGame(int gameId) throws RemoteException;

    void getGameList() throws RemoteException, NoGamesException, IOException;

    void setReady(boolean ready) throws RemoteException;

    void drawGold(int index) throws RemoteException;

    void drawResource(int index) throws RemoteException;

    void chooseSecretObjective1() throws RemoteException;

    void chooseSecretObjective2() throws RemoteException;

    void playStarter() throws RemoteException;

    void play(Point point) throws RemoteException;

    void selectCard(int index) throws RemoteException;

    void changeSide() throws RemoteException;

    void changeStarterSide() throws RemoteException;

    int getGameID() throws RemoteException; // FIX serve?

    String getUsername();// FIX serve? TODO penso di si

    void sendChatMessage(String username, String message) throws RemoteException;

}
