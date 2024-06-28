package it.polimi.ingsw.gc31.client_server.tcp;

import java.awt.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.gc31.client_server.Token;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.utility.DV;
import it.polimi.ingsw.gc31.utility.FileUtility;
import it.polimi.ingsw.gc31.view.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Is the {@link it.polimi.ingsw.gc31.Client} that uses the TCP paradigm to communicate with a {@link it.polimi.ingsw.gc31.Server},
 * the connection is handled by a {@link TCPServer}. After the bind with the server a specific {@link SocketClientHandler} is
 * created and associated with this {@link TCPClient}.
 */
public class TCPClient implements ClientCommands {
    /**
     * The ObjectInputStream associated with the specific client
     */
    private final ObjectInputStream input;
    /**
     * The ObjectOutputStream associated with the specific client
     */
    private final ObjectOutputStream output;
    /**
     * The username of the client
     */
    private String username;
    private int idGame;
    /**
     * The user interface used to communicate with the client
     */
    private UI ui;
    /**
     * The list containing all the object (sent by the server) the user needs to
     * execute
     */
    private final Queue<ClientQueueObject> callsList;
    /**
     * Is a specific object used to manage the connections and future re-connections
     * of the client
     */
    private final Token token;
    /**
     * A timer used to send a heart beat to the server. Used to check the
     * disconnection of a client by the server
     */
    private final Timer timer;

    /**
     * This method is the constructor of the TCPClient.
     * The timer is set as a daemon by the specific constructor in order to not
     */
    @SuppressWarnings("resource")
    public TCPClient(final String ipaddress) throws IOException {
        this.username = DV.DEFAULT_USERNAME;
        this.token = new Token();
        this.token.setTempToken(-1);
        Socket serverSocket = new Socket(ipaddress, DV.TCP_PORT);
        this.input = new ObjectInputStream(serverSocket.getInputStream());
        this.output = new ObjectOutputStream(serverSocket.getOutputStream());
        this.callsList = new LinkedBlockingQueue<>();
        this.timer = new Timer(true);

        clientHandler_reader();
        executor();
    }

    /**
     * This method writes the object taken as parameter after setting the right
     * recipient for it
     *
     * @param obj       is the object that needs to be sent to the server
     * @param recipient is the controller or the gameController based on who has to
     *                  execute the particular object
     */
    private void tcp_sendCommand(ServerQueueObject obj, String recipient) {
        try {
            obj.setRecipient(recipient);
            output.writeObject(obj);
            output.reset();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks continuously if there are object sent to the reader. If an
     * object is found
     * the method adds the object red in the callsList, after the object is added
     * the method notify that
     * an object has been added
     */
    private void clientHandler_reader() {
        new Thread(() -> {
            ClientQueueObject obj = null;
            while (true) {
                try {
                    obj = (ClientQueueObject) input.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                if (obj != null) {
                    synchronized (callsList) {
                        callsList.add(obj);
                        callsList.notify();
                    }
                }
            }
        }).start();
    }

    /**
     * This method takes the object in the callsList (acquiring the lock) and
     * invokes the execute method of the object.
     * If there are no object in the list the thread wait for a new one
     */
    private void executor() {
        new Thread(() -> {
            while (true) {
                ClientQueueObject action;
                synchronized (callsList) {
                    while (callsList.isEmpty()) {
                        try {
                            callsList.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    action = callsList.poll();
                }
                if (action != null) {
                    action.execute(ui);
                }
            }
        }).start();
    }

    /**
     * This method set the UI for the TCPClient
     *
     * @param ui is the concrete UI that needs to be assigned for this TCPClient
     */
    @Override
    public void setUI(UI ui) {
        this.ui = ui;
    }

    /**
     * Sets the username of the current user. To be used both when a reconnection is
     * not detected
     * and when a reconnection is refused
     *
     * @param username: name to save the current client
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
        startHeartBeat();
    }

    /**
     * This method return the player's username
     *
     * @return the player's username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * This method is the first called by the client, it sends the client handler
     * the request to execute the "connect" method. If the server has already this
     * username an exception is launched
     *
     * @param username is the username set by the client
     */
    @Override
    public void setUsernameCall(String username) {
        tcp_sendCommand(new ConnectObj(username, this.token.getTempToken(), this.token.getToken()),
                DV.RECIPIENT_CONTROLLER);
    }

    /**
     * This method sends to the client handler the string corresponding with the
     * create game request
     *
     * @param maxNumberPlayer is the max number of the players for the new game
     */
    @Override
    public void createGame(int maxNumberPlayer) {
        tcp_sendCommand(new CreateGameObj(this.username, maxNumberPlayer), DV.RECIPIENT_CONTROLLER);
    }

    /**
     * This method send the string that identifies the join game request made
     * by the player
     *
     * @param gameId is the gameId of the particular game the player wants to join
     */
    @Override
    public void joinGame(int gameId) throws RemoteException {
        tcp_sendCommand(new JoinGameObj(this.username, gameId), DV.RECIPIENT_CONTROLLER);
    }

    /**
     * This method should write on the user terminal the list of games already
     * created
     * Firstly it send to the client handler the request to execute the specified
     * method.
     * After that the method waits for the client handler's response, the response
     * can be an exception
     * (in this case the method launches the exception to the TUI) or the message
     * "ok" otherwise.
     * In this case the method reads every String sent by the client handler,
     * collect every
     * String in “list” and then call the method of the ui.
     *
     * @throws NoGamesException is launched if there are no created games
     */
    @Override
    public void getGameList() throws NoGamesException {
        tcp_sendCommand(new GetGameListObj(this.token.getToken()), DV.RECIPIENT_CONTROLLER);
    }

    /**
     * This method sends to the server a new ReadyStatusObj object and the game
     * controller as a recipient
     * using the tcp_sendCommand method.
     *
     * @param ready is the new ready value of the player
     */
    @Override
    public void setReady(boolean ready) {
        tcp_sendCommand(new ReadyStatusObj(ready, this.username), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawGoldObj object and the game
     * controller as a recipient
     * using the tcp_sendCommand method
     * <p>
     * index = 0 : drawing from the gold deck.
     * index = 1 : drawing the first gold card on the board.
     * index = 2 : drawing the second gold card on the board.
     */
    @Override
    public void drawGold(int index) throws RemoteException {
        tcp_sendCommand(new DrawGoldObj(this.username, index), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawResObj object and the game
     * controller as a recipient
     * using the tcp_sendCommand method.
     * <p>
     * index = 0 : drawing from the resource deck.
     * index = 1 : drawing the first resource card on the board.
     * index = 2 : drawing the second resource card on the board.
     */
    @Override
    public void drawResource(int index) throws RemoteException {
        tcp_sendCommand(new DrawResObj(this.username, index), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new ChooseSecretObjectiveObj object and the
     * game controller as a recipient
     * using the tcp_sendCommand method.
     * <p>
     * index = 0 : choose first secret objective card.
     * index = 1 : choose second secret objective card.
     */
    @Override
    public void chooseSecretObjective1() {
        tcp_sendCommand(new ChooseSecretObjectiveObj(this.username, 0), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new ChooseSecretObjectiveObj object and the
     * game controller as a recipient
     * using the tcp_sendCommand method.
     * <p>
     * index = 0 : choose first secret objective card.
     * index = 1 : choose second secret objective card.
     */
    @Override
    public void chooseSecretObjective2() {
        tcp_sendCommand(new ChooseSecretObjectiveObj(this.username, 1), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object that plays the starter card
     */
    @Override
    public void playStarter() throws RemoteException {
        tcp_sendCommand(new PlayStarterObj(this.username), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object that plays a card at a specified location
     *
     * @param point is the point where the player wants to play the card
     */
    @Override
    public void play(Point point) throws RemoteException {
        tcp_sendCommand(new PlayObj(this.username, point.x, point.y), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object that select a card at the specified hand
     * location
     *
     * @param index is the position in the hand of the card the player wants to
     *              select
     */
    @Override
    public void selectCard(int index) {
        tcp_sendCommand(new SelectCardObj(this.username, index), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object that flips the card selected for the player
     */
    @Override
    public void changeSide() throws RemoteException {
        tcp_sendCommand(new FlipCardObj(this.username), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object that flips the starter card
     */
    @Override
    public void changeStarterSide() throws RemoteException {
        tcp_sendCommand(new FlipStarterCardObj(this.username), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method returns the idGame
     *
     * @return the idGame
     */
    @Override
    public int getGameID() {
        return idGame;
    }

    /**
     * This method sends the object that sends a message in the chat
     *
     * @param username is the username of the player sending the message
     * @param message  is the String the player wants to send in the chat
     */
    @Override
    public void sendChatMessage(String username, String message) {
        tcp_sendCommand(new ChatMessageObj(this.username, message), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends a private message to a specific player
     *
     * @param fromUsername Username of the current user, which is sending the
     *                     message
     * @param toUsername   Username of the player the current user is sending the
     *                     message to
     * @param message      Content of the message that is being sent
     */
    @Override
    public void sendChatMessage(String fromUsername, String toUsername, String message) {
        tcp_sendCommand(new ChatMessageObj(this.username, toUsername, message), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object, which quit the specific client from the game
     * lobby, to the server
     */
    @Override
    public void quitGame() throws RemoteException {
        tcp_sendCommand(new QuitGameObj(this.username), DV.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method starts the procedure of the heart beat.
     * It creates a task that is executed periodically.
     * In particular every 5 seconds a HeartBeatObj is created and sent to the
     * clientHandler with the specific recipient.
     * The first execution is done at the invocation of this method,
     * all the others execution are performed every 5 seconds
     */
    private void startHeartBeat() {
        long sendTime = (DV.testHB) ? DV.sendTimeTest : DV.sendTime;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                tcp_sendCommand(new HeartBeatObj(username), DV.RECIPIENT_HEARTBEAT);
            }
        }, 0, sendTime);
    }

    // Metodi per token

    /**
     * This method set the token of the client to the value received by parameter
     *
     * @param token is the value to be set as the token of the client
     */
    @Override
    public void setToken(int token, boolean temporary) {
        if (!temporary) {
            this.token.setToken(token);
            this.token.setTempToken(token);
            if (this.token.rewriteTokenFile())
                ui.show_GenericClientResponse("File precedente eliminato");
            ui.show_GenericClientResponse("Token salvato correttamente nel percorso: ");
            ui.show_GenericClientResponse(FileUtility.getCodexTokenFilePath().toString());
        } else {
            this.token.setTempToken(token);
        }

    }

    /**
     * This method sends the ReconnectObj to the Controller
     *
     * @param reconnect is true if the player wants to reconnect, false otherwise
     */
    @Override
    public void reconnect(boolean reconnect) throws RemoteException {
        tcp_sendCommand(new ReconnectObj(reconnect, token.getTempToken(), token.getToken()),
                DV.RECIPIENT_CONTROLLER);
    }

    /**
     * This method returns the entire Token object of the client
     *
     * @return the Token of the client
     */
    @Override
    public Token getToken() {
        return this.token;
    }

    // TODO
    // /**
    // // * Method invoked by the ui with the response of the user regarding
    // // * if the player wants to play another match with the same players
    // // *
    // // * @param wantsToRematch is the response of the player (true: wants to
    // rematch,
    // // * false otherwise)
    // // */
    // @Override
    // public void anotherMatchResponse(Boolean wantsToRematch) {
    // tcp_sendCommand(new AnotherMatchResponseObj(username, wantsToRematch),
    // DV.RECIPIENT_GAME_CONTROLLER);
    // }
    //
    // /**
    // * Method that checks if the token of the client exists
    // *
    // * @return true if the token exists, false otherwise
    // */
    // @Override
    // public boolean hasToken() {
    // return token.doesTokenExists();
    // }
    //
    // /**
    // * Method that read the value of the token in the specific file created by the
    // * program
    // *
    // * @return the value of the token in the file
    // * @throws NumberFormatException if the value red is not a number
    // * @throws NoTokenException if the token doesn't exists
    // */
    // @Override
    // public int readToken() throws NumberFormatException, NoTokenException {
    // return Integer.parseInt(token.getTokenLine());
    // }
}
