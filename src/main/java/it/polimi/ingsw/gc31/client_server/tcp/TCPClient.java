package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.UI;

public class TCPClient implements ClientCommands {
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private String username;
    private Integer idGame;
    private UI ui;
    private final Queue<ClientQueueObject> callsList;

    /**
     * This method is the constructor of the TCPClient
     */
    @SuppressWarnings("resource")
    public TCPClient() throws IOException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        Socket serverSocket = new Socket("127.0.0.1", 1200);
        this.input = new ObjectInputStream(serverSocket.getInputStream());
        this.output = new ObjectOutputStream(serverSocket.getOutputStream());
        this.callsList = new LinkedBlockingQueue<>();
        clientHandler_reader();
        executor();

    }

    /**
     * This method writes the object taken as parameter after setting the right recipient for it
     *
     * @param obj is the object that needs to be sent to the server
     * @param recipient is the controller or the gameController based on who has to execute the particular object
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
     * This method checks continuously if there are object sent to the reader. If an object is found
     * the method adds the object red in the callsList, after the object is added the method notify that
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
     * This method takes the object in the callsList (acquiring the lock) and invokes the execute method of the object.
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
     * This method is invoked after the server send the result of the setUsername method
     *
     * @param username is the username set for the player server-side
     */
    @Override
    public void setUsernameResponse(String username) {
        this.username = username;
    }

    /**
     * This method returns the player's game idGame
     *
     * @return the idGame of the game
     */
    @Override
    public int getGameID() {
        return idGame;
    }

    /**
     * This method return the player's username
     * @return the player's username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * This method is the first called by the client, it sends the client handler
     * the request to execute the "connect" method. If the server has already this
     * username ane exception is launched
     *
     * @param username is the username set by the client
     * @throws IOException                          if there is an error reading the
     *                                              client handler messages
     */
    @Override
    public void setUsernameCall(String username) throws IOException {
        tcp_sendCommand(new ConnectObj(username), DefaultValues.RECIPIENT_CONTROLLER);
    }

    /**
     * This method sends to the client handler the string corresponding with the
     * create game request
     *
     * @param maxNumberPlayer is the max number of the players for the new game
     * @throws IOException if there is an error reading the server messages
     */
    @Override
    public void createGame(int maxNumberPlayer) throws IOException {
        tcp_sendCommand(new CreateGameObj(this.username, maxNumberPlayer), DefaultValues.RECIPIENT_CONTROLLER);
    }

    /**
     * This method send the string that identifies the join game request made
     * by the player
     *
     * @param gameId is the gameId of the particular game the player wants to join
     */
    @Override
    public void joinGame(int gameId) {
        tcp_sendCommand(new JoinGameObj(this.username, gameId), DefaultValues.RECIPIENT_CONTROLLER);
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
     * String in "list" and then call the method of the ui.
     *
     * @throws IOException      is launched if an error is occurred in the readLine
     *                          method
     * @throws NoGamesException is launched if there are no created games
     */
    @Override
    public void getGameList() throws IOException, NoGamesException {
            tcp_sendCommand(new GetGameListObj(this.username), DefaultValues.RECIPIENT_CONTROLLER);
    }

    /**
     * This method sends to the server a new ReadyStatusObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * @param ready is the new ready value of the player
     */
    @Override
    public void setReady(boolean ready) {
        tcp_sendCommand(new ReadyStatusObj(ready, this.username), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawGoldObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * select = 0 : drawing from the gold deck.
     * select = 1 : drawing the first gold card on the board.
     * select = 2 : drawing the second gold card on the board.
     */
    @Override
    public void drawGold() {
        tcp_sendCommand(new DrawGoldObj(this.username, 0), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawGoldObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * select = 0 : drawing from the gold deck.
     * select = 1 : drawing the first gold card on the board.
     * select = 2 : drawing the second gold card on the board.
     */
    @Override
    public void drawGoldCard1() {
        tcp_sendCommand(new DrawGoldObj(this.username, 1), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawGoldObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * select = 0 : drawing from the gold deck.
     * select = 1 : drawing the first gold card on the board.
     * select = 2 : drawing the second gold card on the board.
     */
    @Override
    public void drawGoldCard2() {
        tcp_sendCommand(new DrawGoldObj(this.username, 2), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawResObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * select = 0 : drawing from the resource deck.
     * select = 1 : drawing the first resource card on the board.
     * select = 2 : drawing the second resource card on the board.
     */
    @Override
    public void drawResource() {
        tcp_sendCommand(new DrawResObj(this.username, 0), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawResObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * select = 0 : drawing from the resource deck.
     * select = 1 : drawing the first resource card on the board.
     * select = 2 : drawing the second resource card on the board.
     */
    @Override
    public void drawResourceCard1() {
        tcp_sendCommand(new DrawResObj(this.username, 1), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new DrawResObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * select = 0 : drawing from the resource deck.
     * select = 1 : drawing the first resource card on the board.
     * select = 2 : drawing the second resource card on the board.
     */
    @Override
    public void drawResourceCard2() {
        tcp_sendCommand(new DrawResObj(this.username, 2), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new ChooseSecretObjectiveObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     * The index parameter specify if the objective card to choose is the first one or the second one.
     *
     * index = 0 : choose first secret objective card.
     * index = 1 : choose second secret objective card.
     */
    @Override
    public void chooseSecretObjective1() {
        tcp_sendCommand(new ChooseSecretObjectiveObj(this.username, 0), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends to the server a new ChooseSecretObjectiveObj object and the game controller as a recipient
     * using the tcp_sendCommand method.
     *
     * index = 0 : choose first secret objective card.
     * index = 1 : choose second secret objective card.
     */
    @Override
    public void chooseSecretObjective2() {
        tcp_sendCommand(new ChooseSecretObjectiveObj(this.username, 1), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }

    /**
     * This method sends the object that sends a message in the chat
     * @param username is the username of the player sending the message
     * @param message is the String the player wants to send in the chat
     */
    @Override
    public void sendChatMessage(String username, String message) {
        tcp_sendCommand(new ChatMessage(this.username, message), DefaultValues.RECIPIENT_GAME_CONTROLLER);
    }
}
