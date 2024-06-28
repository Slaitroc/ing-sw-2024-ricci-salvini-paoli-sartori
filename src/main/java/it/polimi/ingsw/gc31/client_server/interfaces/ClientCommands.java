package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.client_server.Token;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;
import it.polimi.ingsw.gc31.view.gui.GUI;

import java.awt.*;
import java.rmi.RemoteException;

/**
 * Defines the methods executable by all the
 * {@link it.polimi.ingsw.gc31.Client}. These methods are involved with the
 * {@link UI} classes which directly communicate
 * with the user. Based on the input received from the user a specific method is
 * invoked by the{@link UI}.
 *
 * @see it.polimi.ingsw.gc31.view.tui.TUI
 * @see it.polimi.ingsw.gc31.view.gui.GUI
 */
public interface ClientCommands {
    /**
     * Sets the {@link it.polimi.ingsw.gc31.Client} attribute of the {@link UI} to
     * the reference received as a parameter.
     * Can be invoked by either the
     * {@link it.polimi.ingsw.gc31.Client#main(String[])} method or the
     * {@link GUI#runUI()} method.
     *
     * @param ui is the reference of the {@link UI} chosen by the client to be set
     *           as the new value of the client's attribute.
     * @see it.polimi.ingsw.gc31.view.tui.TUI
     * @see GUI
     */
    void setUI(UI ui);

    /**
     * Creates a new {@link ConnectObj} with {@link Token#getTempToken()} and
     * {@link Token#getToken()} and sends it to the {@link Controller}(socket) or
     * {@link RmiServer}(rmi) to connect to the server
     *
     * @param username username chosen by the user
     * @throws RemoteException if a generic connection error occurs
     */
    void setUsernameCall(String username) throws RemoteException;

    /**
     * Sets the username of the current user
     * To use both when a reconnection is not detected and when a reconnection is
     * refused
     *
     * @param username: name to save the current client
     */
    void setUsername(String username);

    /**
     * Method used to create a game with specified number of players
     *
     * @param maxNumberPlayer Maximum number of player allowed in a game (from 2 to
     *                        4).
     *                        It is also the minimum number of players needed to
     *                        start the game
     * @throws RemoteException if a generic connection error occurs
     */
    void createGame(int maxNumberPlayer) throws RemoteException;

    /**
     * Method used to attempt to join a specific game
     *
     * @param gameId Identifier of the game (Currently it is a progressive number
     *               starting from 0)
     * @throws RemoteException if a generic connection error occurs
     */
    void joinGame(int gameId) throws RemoteException;

    /**
     * Method used to quit from the current game. Rejoin to the will not be allowed
     *
     * @throws RemoteException if a generic connection error occurs
     * @throws RemoteException if a generic connection error occurs
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

    /**
     * Sets the private values of tempToken and token to the values received as a
     * parameter. Is implemented both in
     * {@link it.polimi.ingsw.gc31.client_server.rmi.RmiClient} and
     * {@link it.polimi.ingsw.gc31.client_server.tcp.TCPClient}.
     * If the temporary value is true the method only sets the value of tempToken.
     * If the temporary value is false the method sets both the values of token and
     * tempToken, also writes on the file
     * the new value of the token received.
     *
     * @param token     is the new value of the token to be set.
     * @param temporary indicates if the token to set is the temporary one or both
     *                  of them.
     *
     * @see Token
     * @see it.polimi.ingsw.gc31.client_server.rmi.RmiClient
     * @see it.polimi.ingsw.gc31.client_server.tcp.TCPClient
     */
    void setToken(int token, boolean temporary);

    // // TODO
    // boolean hasToken();

    /**
     * Return the reference of the {@link it.polimi.ingsw.gc31.Client}
     * {@link Token}. The {@link it.polimi.ingsw.gc31.Client}
     * can be either a {@link it.polimi.ingsw.gc31.client_server.rmi.RmiClient} or a
     * {@link it.polimi.ingsw.gc31.client_server.tcp.TCPClient}.
     *
     * @return the reference of the {@link Token} of the
     *         {@link it.polimi.ingsw.gc31.Client}.
     */
    Token getToken();

    // // TODO
    // int readToken() throws NumberFormatException, NoTokenException;

    /**
     * Sends a new {@link ReconnectObj} to the {@link Controller}. The boolean
     * parameter indicates if the user
     * wants to reconnect or not to the match where the user was before
     * disconnecting.
     *
     * @param reconnect indicates if the user wants to reconnect to the match or
     *                  not.
     * @throws RemoteException if an error occurs during the remote method call.
     */
    void reconnect(boolean reconnect) throws RemoteException;

    // TODO
    // boolean hasToken();
    // int readToken() throws NumberFormatException, NoTokenException;
    // void anotherMatchResponse(Boolean wantsToRematch) throws RemoteException;
}
