package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.client_server.tcp.TCPServer;
import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class receives the inputs from the virtual socket server, executes the
 * methods requested by the client
 * sends the data, that need to be updated and showed to other clients, to the
 * server
 */
public class SocketClientHandler implements VirtualClient {
    final IController controller;
    private IGameController gameController;
    private String username;
    private IPlayerController playerController;
    private Integer idGame;
    //private Map<String, Runnable> commandsMap;

    private final TCPServer server;
    private final BufferedReader input;
    private final PrintWriter output;

    /**
     * This method is the constructor of the client handler
     * 
     * @param controller is the IController of the specific client
     * @param server     is the TCPServer linked to that client handler
     * @param input      is the reference to the input stream of the socket
     *                   connection
     * @param output     is the reference to the output stream of the socket
     *                   connection
     */
    public SocketClientHandler(IController controller, TCPServer server, BufferedReader input, PrintWriter output) {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
    //    initializeMap();
    }


    /*
    private void initializeMap(){
        this.commandsMap = new HashMap<>();
        this.commandsMap.put("create game", this::runCreateGame);
    }

    private void runCreateGame() throws IOException {
        int maxNumberPlayer = Integer.parseInt(input.readLine());
        gameController = controller.createGame(username, maxNumberPlayer);
        output.println(this.idGame);
        output.flush();
    }
    */

    /**
     * This method is invoked on the client handler creation (by a Thread), read in
     * an infinite loop
     * the commands sent by the TCPClient and invokes the methods based on the
     * client messages
     * 
     * @throws IOException if an error occurs reading on the socket input stream
     */
    public void runVirtualView() throws IOException {
        String line;
        while ((line = input.readLine()) != null) {

            switch (line) {
                case "connect": {
                    line = input.readLine();
                    try {
                        controller.connect(this, line);
                        this.username = line;
                        output.println("username set");
                        output.flush();
                        server.TCPserverWrite("New client connected: " + username);
                    } catch (PlayerNicknameAlreadyExistsException e) {
                        output.println("username already exists");
                        output.flush();
                    }
                    break;
                }
                case "create game": {
                    int maxNumberPlayer = Integer.parseInt(input.readLine());
                    gameController = controller.createGame(username, maxNumberPlayer);
                    output.println(this.idGame);
                    output.flush();
                    break;
                }
                case "get game list": {
                    try {
                        controller.getGameList(username);
                    } catch (NoGamesException e) {
                        output.println("no game exception");
                        output.flush();
                    }
                    break;
                }
                case "join game": {
                    int idGame = Integer.parseInt(input.readLine());

                    gameController = controller.joinGame(username, idGame);

                    break;
                }
                case "draw gold": {
                    playerController.drawGold();

                    break;
                }
            }
        }
    }

    /**
     * This method sets the gameID
     * 
     * @param gameID is the value that needs to be set
     * @throws RemoteException
     */
    @Override
    public void setGameID(int gameID) throws RemoteException {
        this.idGame = gameID;
    }

    /**
     * This methods should send a message to the TCPClient with the serialization
     * of the player's hand
     * 
     * @param hand is the list that contains all the card held by the player
     * @throws RemoteException
     */
    @Override
    public void showHand(List<String> hand) throws RemoteException {
        output.println("start of list");
        for (String s : hand){
            output.println(s);
        }
        output.println("end of list");
        output.flush();
    }

    /**
     * This method should send to the tcp client the list of games created.
     * In order to do so the method writes on the socket stream every String
     * that represents a game
     * 
     * @param listGame is the list with every String representing a game
     * @throws RemoteException
     */
    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        output.println("ok");
        // listGame.forEach(output::println);
        for (String s : listGame)
            output.println(s);
        output.println("game list finished");
        output.flush();
    }

    /**
     * This method should set the playerController with the one got as parameter
     * 
     * @param playerController is the playerController that needs to be set
     */
    @Override
    public void setPlayerController(IPlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void showMessage(String msg) throws RemoteException {

    }
}