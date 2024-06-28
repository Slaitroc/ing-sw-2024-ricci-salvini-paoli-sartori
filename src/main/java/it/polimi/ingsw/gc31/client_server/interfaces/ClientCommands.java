package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.Token;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.NoTokenException;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;

import java.awt.*;
import java.rmi.RemoteException;

public interface ClientCommands {

    void setUI(UI ui);

    /**
     * Creates a new {@link ConnectObj} with {@link Token#getTempToken()} and
     * {@link Token#getToken()} and sends it to the {@link Controller}(socket) or
     * {@link RmiServer}(rmi) to connect to the server
     * 
     * @param username username chosen by the user
     * @throws RemoteException  if a generic connection error occurs
     */
    void setUsernameCall(String username) throws RemoteException;

    /**
     * Sets the username of the current user
     * To use both when a reconnection is not detected and when a reconnection is refused
     *
     * @param username: name to save the current client
     */
    void setUsername(String username);

    /**
     * Method used to create a game with specified number of players
     *
     * @param maxNumberPlayer Maximum number of player allowed in a game (from 2 to 4).
     *                       It is also the minimum number of players needed to start the game
     * @throws RemoteException  if a generic connection error occurs
     */
    void createGame(int maxNumberPlayer) throws RemoteException;

    /**
     * Method used to attempt to join a specific game
     *
     * @param gameId Identifier of the game (Currently it is a progressive number starting from 0)
     * @throws RemoteException  if a generic connection error occurs
     */
    void joinGame(int gameId) throws RemoteException;

    /**
     * Method used to quit from the current game. Rejoin to the will not be allowed
     *
     * @throws RemoteException  if a generic connection error occurs
     */
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
     */
    int getGameID();

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

    void setToken(int token, boolean temporary);

    Token getToken();

    void reconnect(boolean reconnect) throws RemoteException;

//     TODO
//     boolean hasToken();
//    int readToken() throws NumberFormatException, NoTokenException;
//    void anotherMatchResponse(Boolean wantsToRematch) throws RemoteException;
}
