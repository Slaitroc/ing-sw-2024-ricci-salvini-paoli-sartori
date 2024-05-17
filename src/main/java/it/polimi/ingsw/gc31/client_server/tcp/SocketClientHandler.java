package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.queue.clientSide.ClientQueueObject;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;
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
    public SocketClientHandler(IController controller, TCPServer server, BufferedReader input, PrintWriter output) {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
        initializeMap();
    }

    private void initializeMap() {
        this.commandsMap = new HashMap<>();
        this.commandsMap.put("connect", this::runConnect);
        this.commandsMap.put("create game", this::runCreateGame);
        this.commandsMap.put("get game list", this::runGetGameList);
        this.commandsMap.put("join game", this::runJoinGame);
        this.commandsMap.put("draw gold", this::runDrawGold);
    }

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

    private void runJoinGame() {
        Integer idGame = null;
        try {
            idGame = Integer.parseInt(input.readLine());
            gameController = controller.joinGame(username, idGame);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void runDrawGold() {
        try {
            gameController.drawGold(username);
        } catch (RemoteException e) {
            e.printStackTrace();
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
            /*
             * switch (line) {
             * case "connect": {
             * commandsMap.get("connect");
             * 
             * line = input.readLine();
             * try {
             * controller.connect(this, line);
             * this.username = line;
             * output.println("username set");
             * output.flush();
             * server.TCPserverWrite("New client connected: " + username);
             * } catch (PlayerNicknameAlreadyExistsException e) {
             * output.println("username already exists");
             * output.flush();
             * }
             * break;
             * }
             * case "create game": {
             * commandsMap.get("create game");
             * 
             * int maxNumberPlayer = Integer.parseInt(input.readLine());
             * gameController = controller.createGame(username, maxNumberPlayer);
             * output.println(this.idGame);
             * output.flush();
             * break;
             * }
             * case "get game list": {
             * commandsMap.get("get game list");
             * 
             * try {
             * controller.getGameList(username);
             * } catch (NoGamesException e) {
             * output.println("no game exception");
             * output.flush();
             * }
             * break;
             * }
             * case "join game": {
             * commandsMap.get("join game");
             * 
             * int idGame = Integer.parseInt(input.readLine());
             * 
             * gameController = controller.joinGame(username, idGame);
             * 
             * break;
             * }
             * case "draw gold": {
             * gameController.drawGold(username);
             * commandsMap.get("draw gold");
             * break;
             * }
             * }
             */
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

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) throws RemoteException {
        /*
        output.print("show hand player");
        // username non viene utilizzato
        // output.print(username);
        for (String s : hand) {
            output.println(s);
        }
        output.println("end list");
        output.flush();
        */
    }

    @Override
    public void show_scorePlayer(String username, Integer score) throws RemoteException {
        output.println("show score");
        // username non viene utilizzato
        output.println(username);
        output.println(score);
        output.flush();
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        output.println("show starter card");
        output.println(starterCard);
        output.flush();
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
        output.println("show objective card");
        output.println(objectiveCard);
        output.flush();
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {

    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        output.println("show gold deck");
        output.println(firstCardDeck);
        output.println(card1);
        output.println(card2);
        output.flush();
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        output.println("show resource deck");
        output.println(firstCardDeck);
        output.println(card1);
        output.println(card2);
        output.flush();
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        output.println("show objective card");
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
     * @throws RemoteException
     */
    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        output.println("show game list");
        // listGame.forEach(output::println);
        for (String s : listGame)
            output.println(s);
        output.println("game list finished");
        output.flush();
    }

    @Override
    public boolean isReady() throws RemoteException {
        return ready;
    }

    @Override
    public void startGame() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public ShowUpdate getUI() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUI'");
    }

    @Override
    public void sendCommand(ClientQueueObject obj) throws RemoteException {

    }
}