package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.view.tui.TUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.io.*;
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
    @SuppressWarnings("unused")
    private VirtualClient client;
    private Integer idGame;
    private Map<String, Runnable> commandsMap;

    private final TCPServer server;
    private final BufferedReader input;
    private final PrintWriter output;
    private boolean ready = false;

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
    public SocketClientHandler(IController controller, TCPServer server, BufferedReader input, PrintWriter output, ObjectInputStream inputObject) {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
        initializeMap();
    }

    /**
     * This method initializes the commandsMap which contains a string
     * and the associated method that the clientHandler wants to execute when a
     * specific string is received from the tcpClient
     */
    private void initializeMap() {
        this.commandsMap = new HashMap<>();
        this.commandsMap.put("connect", this::runConnect);
        this.commandsMap.put("create game", this::runCreateGame);
        this.commandsMap.put("get game list", this::runGetGameList);
        this.commandsMap.put("join game", this::runJoinGame);
        this.commandsMap.put("draw gold", this::runDrawGold);
        this.commandsMap.put("draw resource", this::runDrawResource);
        this.commandsMap.put("ready", this::runSetReady);
    }

    /**
     * This method try to connect the new client to the server and sets it's username
     */
    private void runConnect() {
        String line = null;
        try {
            line = input.readLine();
            controller.connect(this, line);
            this.username = line;
            output.println("username set");
            output.flush();
            server.TCPserverWrite("New client connected: " + username);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (PlayerNicknameAlreadyExistsException p) {
            output.println("Username already exists!");
            output.flush();
        }
    }

    /**
     * This method requests the gameController to create a new game. Also write to the client
     * the idGame associated with the newly created match
     */
    private void runCreateGame() {
        try {
            int maxNumberPlayer = Integer.parseInt(input.readLine());
            gameController = controller.createGame(username, maxNumberPlayer);
            output.println(this.idGame);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method requests the list of all the games available from the controller
     */
    private void runGetGameList() {
        try {
            controller.getGameList(username);
        } catch (NoGamesException e) {
            output.println("no game exception");
            output.flush();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method asks the controller to join a specific game, associated to the
     * idGame received from the client
     */
    private void runJoinGame() {
        Integer idGame = null;
        try {
            idGame = Integer.parseInt(input.readLine());
            gameController = controller.joinGame(username, idGame);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method requests the gameController to draw a gold card
     */
    private void runDrawGold() {
        try {
            gameController.drawGold(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void runDrawResource(){
        try{
            gameController.drawResource(username);
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }
    /**
     * This method sets the value of ready to the value received from the client.
     * After the new value is set is invoked the method checkReady of the gameController
     */
    private void runSetReady(){
        try{
            ready = (input.readLine()).equals("true");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ready) {
            try {
                gameController.checkReady();
            } catch (RemoteException | IllegalStateOperationException e) {
                e.printStackTrace();
            }
        }
    }

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
            commandsMap.get(line).run();
        }
    }

    /**
     * This method sets the gameID
     * @param gameID is the value that needs to be set
     */
    @Override
    public void setGameID(int gameID){
        this.idGame = gameID;
    }

    /**
     * This method requests to the client to show the hand of a specific player
     * @param username is the username associated with the specific player
     * @param hand is the list of all the cards that are held by the player
     */
    @Override
    public void show_handPlayer(String username, List<String> hand){
        output.print("show hand player");
        output.print(username);
        for (String s : hand) {
            output.println(s);
        }
        output.println("end list");
        output.flush();
    }

    /**
     * This method requests the client to show the score of a specific player
     * @param username is the specific player
     * @param score is the score associated with the username passed
     */
    @Override
    public void show_scorePlayer(String username, Integer score){
        output.println("show score player");
        output.println(username);
        output.println(score);
        output.flush();
    }

    /**
     * This method requests to the client to show a starterCard
     * @param starterCard is the starterCard to show
     */
    @Override
    public void show_starterCard(String starterCard){
        output.println("show starter card");
        output.println(starterCard);
        output.flush();
    }

    /**
     * This method requests to the client to show the objectiveCard
     * @param objectiveCard is the objectiveCard to show
     */
    @Override
    public void show_objectiveCard(String objectiveCard){
        output.println("show objective card");
        output.println(objectiveCard);
        output.flush();
    }

    /**
     * This method sends to the tcpClient the request to show the playArea
     * with the related infos
     * @param username is the username related to the playArea
     * @param playArea is the playArea to be shown
     * @param achievedResources is the map containing the infos about the resources achieved in the specific playArea
     */
    @Override
    public void show_playArea(String username, String playArea, String achievedResources){
        output.println("show play area");
        output.println(username);
        output.println(playArea);
        output.println(achievedResources);
        output.flush();
    }

    /**
     * this method writes to the tcpClient the request to show the gold deck, with the
     * related infos
     * @param firstCardDeck is the first card of the deck
     * @param card1 is the first discovered card
     * @param card2 is the second discovered card
     */
    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2){
        output.println("show gold deck");
        output.println(firstCardDeck);
        output.println(card1);
        output.println(card2);
        output.flush();
    }

    /**
     * this method writes to the tcpClient the request to show the resource deck, with the
     * related infos
     * @param firstCardDeck is the first card of the deck
     * @param card1 is the first discovered card
     * @param card2 is the second discovered card
     */
    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2){
        output.println("show resource deck");
        output.println(firstCardDeck);
        output.println(card1);
        output.println(card2);
        output.flush();
    }

    /**
     * this method writes to the tcpClient the request to show the objective deck, with the
     * related infos
     * @param firstCardDeck is the first card on the deck
     * @param card1 is the first discovered card
     * @param card2 is the second discovered card
     */
    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2){
        output.println("show objective deck");
        output.println(firstCardDeck);
        output.println(card1);
        output.println(card2);
        output.flush();
    }

    /**
     * This method should send to the tcp client the list of games created.
     * In order to do so the method writes on the socket stream every String
     * that represents a game
     *
     * @param listGame is the list with every String representing a game
     */
    @Override
    public void showListGame(List<String> listGame){
        output.println("show game list");
        // listGame.forEach(output::println);
        for (String s : listGame)
            output.println(s);
        output.println("game list finished");
        output.flush();
    }

    /**
     * This method returns the value of the attribute ready. It's used by the game controller
     * so it's ok that the method return the value and not writing it on the tcp connection
     * @return the value of the attribute ready
     */
    @Override
    public boolean isReady(){
        return ready;
    }

    /**
     * This method sends a message to the tcpClient requesting the start of the game
     */
    @Override
    public void startGame(){
        output.println("start game");
        output.flush();
    }

    /**
     * This method returns the ui utilized
     * @return the ui
     */
    //TODO Il metodo returna uno showUpdate, tuttavia io devo inviare la stringa al client
    // non ritorno nulla. Controllare che il metodo venga effettivamente utilizzato in questo modo
    // non è sicuro che questo metodo venga mantenuto tale
    @Override
    public ShowUpdate getUI(){
        // output.println("get ui");
        // output.flush();
        return null;
    }
}