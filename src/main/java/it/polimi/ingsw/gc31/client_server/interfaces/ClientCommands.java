package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.Token;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.NoTokenException;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientCommands {

    void setUI(UI ui);

    /**
     * Creates a new {@link ConnectObj} with {@link Token#getTempToken()} and
     * {@link Token#getToken()} and sends it to the {@link Controller}(socket) or
     * {@link RmiServer}(rmi) to connect to the server
     * 
     * @param username username chosen by the user
     * @throws IOException
     */
    void setUsernameCall(String username) throws IOException;

    /**
     * Sets the username of the current user
     * 
     * 
     * @param username
     */
    void setUsernameResponse(String username);

    void setUsername(String username);

    void createGame(int maxNumberPlayer) throws RemoteException;

    void joinGame(int gameId) throws RemoteException;

    void quitGame() throws RemoteException;

    /**
     * Command used to receive the list of games from the server
     * Answer is received from {@link }
     *
     * @throws RemoteException  if a generic connection error occurs
     * @throws NoGamesException if there are no games available on the server
     */
    void getGameList() throws RemoteException, NoGamesException;

    /**
     * Method used to notify the change the ready status of the player to the server
     * 
     * @param ready true if the player is ready, false if player is not ready
     * @throws RemoteException if a generic connection error occurs
     */
    void setReady(boolean ready) throws RemoteException;

    /**
     * Draws a gold card directly from the goldDeck and adds it to the player's
     * hand.
     *
     * @param index index=0 to draw from top of the deck. index=1,2 to draw
     *              card1,card2
     * @throws RemoteException if a generic connection error occurs
     */
    void drawGold(int index) throws RemoteException;

    /**
     * Draws a resource card directly from the goldDeck and adds it to the player's
     * hand.
     *
     * @param index index=0 to draw from top of the deck. index=1,2 to draw
     *              card1,card2
     * @throws RemoteException if a generic connection error occurs
     */
    void drawResource(int index) throws RemoteException;

    /**
     * Chooses secret objective 1 between the two possible secret objectives
     * 
     * @throws RemoteException if a generic connection error occurs
     */
    void chooseSecretObjective1() throws RemoteException;

    /**
     * Chooses secret objective 2 between the two possible secret objectives
     * 
     * @throws RemoteException if a generic connection error occurs
     */
    void chooseSecretObjective2() throws RemoteException;

    /**
     * Plays the starter card on the current starter card side
     * 
     * @throws RemoteException if a generic connection error occurs
     */
    void playStarter() throws RemoteException;

    /**
     * Plays the current selected card in hand on the current selected card side
     *
     * @param point contains the coordinates where to place the card in the PlayArea
     *              map.
     *              (Notice: starter card placed in [0;0])
     * @throws RemoteException if a generic connection error occurs
     */
    void play(Point point) throws RemoteException;

    /**
     * Set the selected card in hand
     *
     * @param index {@index index} of card in hand from 0 to 2
     * @throws RemoteException if a generic connection error occurs
     */
    void selectCard(int index) throws RemoteException;

    /**
     * Change side of the current selected card in hand (Notice: True=Front,
     * False=Back)
     * 
     * @throws RemoteException if a generic connection error occurs
     */
    void changeSide() throws RemoteException;

    /**
     * Change side of starter card (Notice: True=Front, False=Back)
     * 
     * @throws RemoteException if a generic connection error occurs
     */
    void changeStarterSide() throws RemoteException;

    /**
     * If the player is in a match the method will notify the current game ID
     *
     * @return Current game ID
     * @throws RemoteException if a generic connection error occurs
     */
    int getGameID() throws RemoteException;

    /**
     * Used to obtain current Client username
     *
     * @return Current user username
     */
    String getUsername();

    /**
     * Send a public message to all players in the same game
     *
     * @param username Username of the current user, which is sending the message
     * @param message  Content of the message that is being sent
     * @throws RemoteException if a generic connection error occurs
     */
    void sendChatMessage(String username, String message) throws RemoteException;

    /**
     * Send a private message to one specific player in your same game
     *
     * @param fromUsername Username of the current user, which is sending the
     *                     message
     * @param toUsername   Username of the player the current user is sending the
     *                     message to
     * @param message      Content of the message that is being sent
     * @throws RemoteException if a generic connection error occurs
     */
    void sendChatMessage(String fromUsername, String toUsername, String message) throws RemoteException;

    // TODO
    void setToken(int token, boolean temporary);

    // TODO
    boolean hasToken();

    // TODO
    Token getToken();

    // TODO
    int readToken() throws NumberFormatException, NoTokenException;

    // TODO
    void reconnect(boolean reconnect) throws RemoteException;

    // TODO
    void anotherMatchResponse(Boolean wantsToRematch) throws RemoteException;
}
