package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.Token;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.NoTokenException;
import it.polimi.ingsw.gc31.view.UI;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    void setUI(UI ui);

    void setUsernameCall(String username) throws IOException;

    void setUsernameResponse(String username);

    void setUsername(String username);

    void createGame(int maxNumberPlayer) throws RemoteException, IOException;

    void joinGame(int gameId) throws RemoteException;

    void quitGame() throws RemoteException;

    void getGameList() throws RemoteException, NoGamesException, IOException;

    void setReady(boolean ready) throws RemoteException;

    /**
     * Draws a gold card directly from the goldDeck and adds it to the player's
     * hand. index=0 to draw from top of the deck. index=1,2 to draw card1,card2
     */
    void drawGold(int index) throws RemoteException;

    /**
     * Draws a resource card directly from the goldDeck and adds it to the player's
     * hand. index=0 to draw from top of the deck. index=1,2 to draw card1,card2
     */
    void drawResource(int index) throws RemoteException;

    /**
     * Chooses secret objective 1 between the two possible secret objectives
     */
    void chooseSecretObjective1() throws RemoteException;

    /**
     * Chooses secret objective 2 between the two possible secret objectives
     */
    void chooseSecretObjective2() throws RemoteException;

    /**
     * Plays the starter card on the current starter card side
     */
    void playStarter() throws RemoteException;

    /**
     * Plays the current selected card in hand on the current selected card side
     *
     * @param point contains the coordinates where to place the card in the PlayArea map
     */
    void play(Point point) throws RemoteException;

    /**
     * Set the selected card (from 0 to 2) from the hand
     */
    void selectCard(int index) throws RemoteException;

    /**
     * Change side of the current selected card in hand (True=Front, False=Back)
     */
    void changeSide() throws RemoteException;

    /**
     * Change side of starter card (True=Front, False=Back)
     */
    void changeStarterSide() throws RemoteException;

    /**
     * @return Current game ID
     */
    int getGameID() throws RemoteException; // FIX serve?

    /**
     * @return Current user username
     */
    String getUsername();// FIX serve? TODO penso di si

    /**
     * Send a public message to all players in the same game
     *
     * @param username Username of the current user, which is sending the message
     * @param message  Content of the message that is being sent
     */
    void sendChatMessage(String username, String message) throws RemoteException;

    /**
     * Send a private message to one specific player in your same game
     *
     * @param fromUsername Username of the current user, which is sending the message
     * @param toUsername   Username of the player the current user is sending the message to
     * @param message      Content of the message that is being sent
     */
    void sendChatMessage(String fromUsername, String toUsername, String message) throws RemoteException;

    void setToken(int token, boolean temporary);

    boolean hasToken();

    Token getToken();

    int readToken() throws NumberFormatException, NoTokenException;

    void reconnect(boolean reconnect) throws RemoteException;

    void anotherMatchResponse(Boolean wantsToRematch) throws RemoteException;
}
