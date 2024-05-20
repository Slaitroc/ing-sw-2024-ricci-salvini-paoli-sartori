package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ConnectObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

public class TCPClient implements ClientCommands {
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    @SuppressWarnings("unused")
    private String username;
    private Integer idGame;
    private UI ui;
    private final Queue<ClientQueueObject> callsList;

    /**
     * This method is the constructor of the TCPClient
     * 
     * @throws IOException
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

    @SuppressWarnings("unused")
    private void tcp_sendCommand(ServerQueueObject obj, String recipient) {
        try {
            obj.setRecipient(recipient);
            output.writeObject(obj);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientHandler_reader() {
        new Thread(() -> {
            ClientQueueObject obj = null;
            while (true) {
                try {
                    try {
                        obj = (ClientQueueObject) input.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
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
     * This method is the first called by the client, it sends the client handler
     * the request to execute the "connect" method. If the server has already this
     * username ane exception is launched
     * 
     * @param username is the username set by the client
     * @throws IOException                          if there is an error reading the
     *                                              client handler messages
     * @throws PlayerNicknameAlreadyExistsException if the username wrote by the
     *                                              client
     *                                              is already in the server
     *                                              database
     */
    @Override
    public void setUsernameCall(String username) throws IOException {
        tcp_sendCommand(new ConnectObj(username), DefaultValues.RECIPIENT_CONTROLLER);
    }

    @Override
    public void setUsernameResponse(String username) {
        this.username = username;
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

        // output.writeObject(new Cre);
        // output.flush();

        // Se non dovesse ricevere la stringa corretta/ci fosse un errore lato server
        // cosa dovrei fare?
        // Leggo dal server il game ID della partita appena creata
        // String line = input.readLine();
        // this.idGame = Integer.parseInt(line);
        // // ui.show_gameCreated();
    }

    /**
     * This method send the string that identifies the join game request made
     * by the player
     * 
     * @param gameId is the gameId of the particular game the player wants to join
     * @throws RemoteException
     */
    @Override
    public void joinGame(int gameId) throws RemoteException {
        // output.println("join game");
        // output.println(gameId);
        // output.flush();
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
        // List<String> list = new ArrayList<>();
        // output.println("get game list");
        // output.flush();

        // String line = input.readLine();
        // if (line.equals("no game exception"))
        // throw new NoGamesException();
        // else if (line.equals("ok")) {
        // while (!(line = input.readLine()).equals("game list finished"))
        // list.add(line);
        // }
        // ui.show_listGame(list);
    }

    @Override
    public void setReady(boolean ready) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void drawGold() throws RemoteException {
        // output.println("draw gold");
        // output.flush();
    }

    @Override
    public void drawGoldCard1() throws RemoteException {
        // output.println("draw gold card 1");
        // output.flush();
    }

    @Override
    public void drawGoldCard2() throws RemoteException {
        // output.println("draw gold card 2");
        // output.flush();
    }

    @Override
    public void drawResource() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawResource'");
    }

    @Override
    public void drawResourceCard1() throws RemoteException {
        // output.println("draw resource card 1");
        // output.flush();
    }

    @Override
    public void drawResourceCard2() throws RemoteException {
        // output.println("draw resource card 2");
        // output.flush();
    }

    @Override
    public void chooseSecretObjective1() throws RemoteException {
        // output.println("choose secret objective 1");
        // output.flush();
    }

    @Override
    public void chooseSecretObjective2() throws RemoteException {
        // output.println("choose secret objective 2");
        // output.flush();
    }

    /**
     * This method returns the player's game idGame
     *
     * @return the idGame of the game
     * @throws RemoteException
     */
    @Override
    public int getGameID() throws RemoteException {
        return idGame;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public void sendChatMessage(String username, String message) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendChatMessage'");
    }
}
