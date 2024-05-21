package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.UI;

import javax.swing.*;

public class TCPClient implements ClientCommands {
    private final BufferedReader input;
    private final PrintWriter output;
    private String username;
    private Integer idGame;
    private Map<String, Runnable> commandsMap;
    private UI ui;

    // attributes used for the serialization of the QueueObject
    ObjectOutputStream outputObject;

    //Classic setup methods

    /**
     * This method is the constructor of the TCPClient
     * @throws IOException if an error occurs during the setup of the tcp connection
     */
    @SuppressWarnings("resource")
    public TCPClient() throws IOException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        Socket serverSocket = new Socket("127.0.0.1", 1200);
        this.input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.output = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        initializeMap();

        // initialization of the specific object output stream used for the QueueObject
        this.outputObject = new ObjectOutputStream(serverSocket.getOutputStream());

        run();
    }

    /**
     * Method that initializes the map used to invoke the methods by the server. The map contains the methods used to show an update, the methods used to "throw"
     * exceptions and the methods used by the client handler as a response after a request from the client
     */
    private void initializeMap(){
        this.commandsMap = new HashMap<>();
        //methods invoked by the client handler
        this.commandsMap.put("show hand player", this::runShowHandPlayer);
        this.commandsMap.put("show score player", this::runShowScorePlayer);
        this.commandsMap.put("show starter card", this::runShowStarterCard);
        this.commandsMap.put("show objective card", this::runShowObjectiveCard);
        this.commandsMap.put("show gold deck", this::runShowGoldDeck);
        this.commandsMap.put("show resource deck", this::runShowResourceDeck);
        this.commandsMap.put("show objective deck", this::runShowObjectiveDeck);
        this.commandsMap.put("show game list", this::runShowGameList);
        this.commandsMap.put("start game", this::runStartGame);
        this.commandsMap.put("show play area", this::runShowPlayArea);

        //method invoked by the client handler when an exception occurs. Maybe this method can be implemented in the
        // "response methods" as a corner cases.
        this.commandsMap.put("username already exists", this::runUsernameAlreadyExists);

        //method invoked by the client handler as a response of a previous method request by the client
        this.commandsMap.put("create game response", this::runcreateGameResponse);
        this.commandsMap.put("set username response", this::runSetUsernameResponse);
        this.commandsMap.put("get game list response", this::runGetGameListResponse);
    }


    /**
     * Method invoked at the creation of the class. Create a thread that run
     * the runVirtualServer method. Activate it in the constructor method
     */
    private void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Shouldn't be a problem anymore
     * This method is disabled because the messages sent by the server are red
     * in every method corresponding with the specific request launched by the
     * client.
     * This method listen the messages coming from the server and invokes the
     * corresponding
     * method
     * @throws IOException if an error occurs reading the string from socket
     */
    public void runVirtualServer() throws IOException {
        @SuppressWarnings("unused")
        String line;
        while ((line = input.readLine()) != null) {
            commandsMap.get(line).run();
        }
    }

    //Method invoked by the server. Main purpose: show to the player the updates

    /**
     * Method invoked by the client handler that show the player's hand (also the
     * hands of the other players)
     */
    private void runShowHandPlayer(){
        String targetUsername = null;
        try {
             targetUsername = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        List<String> hand = new ArrayList<>();
        try {
            while (!(line = input.readLine()).equals("end list")) {
                hand.add(line);
            }
            ui.show_handPlayer(targetUsername, hand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the player's score
     */
    @SuppressWarnings("unused")
    private void runShowScorePlayer() {
        try {
            String targetUsername = input.readLine();
            Integer score = Integer.parseInt(input.readLine());
            ui.show_scorePlayer(targetUsername, score);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the starter card
     */
    private void runShowStarterCard(){
        try{
            ui.show_starterCard(input.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the objective card
     */
    private void runShowObjectiveCard(){
        try {
            ui.show_objectiveCard(input.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the gold deck
     */
    private void runShowGoldDeck() {
        try {
            System.out.println(input.readLine());
            System.out.println(input.readLine());
            System.out.println(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the resource deck
     */
    private void runShowResourceDeck() {
        try {
            System.out.println(input.readLine());
            System.out.println(input.readLine());
            System.out.println(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the objective deck
     */
    private void runShowObjectiveDeck() {
        try {
            System.out.println(input.readLine());
            System.out.println(input.readLine());
            System.out.println(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that shows the list of all the existing game
     */
    private void runShowGameList() {
        String line;
        List<String> list = new ArrayList<>();
        try {
            while (!(line = input.readLine()).equals("game list finised")) {
                list.add(line);
            }
            ui.show_listGame(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the client handler that starts a game
     */
    private void runStartGame(){
        ui.updateToPlayingState();
    }

    private void runShowPlayArea(){
        String username = null, playArea = null, achievedResources = null;
        try {
            username = input.readLine();
            playArea = input.readLine();
            achievedResources = input.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        try {
            ui.show_playArea(username, playArea, achievedResources);
        } catch (RemoteException e){
            e.printStackTrace();
        }

        /*
        try{
            ui.show_payArea(input.readLine(), input.readLine(), input.readLine());
        } catch (...) {
            ...
        } catch (...) {
            ...
        }
        */
    }

    /**
     * Method that handle the case where the username set by the client already exists
     */
    private void runUsernameAlreadyExists(){
//        ask username again...
//        setUsername();
    }


    //method invoked by the client handler as a response of a previous method request by the client

    /**
     * Method executed after the client handler has sent the response to the createGame method
     */
    private void runcreateGameResponse(){
        try {
            idGame = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.show_gameCreated();
    }

    /**
     * Method executed after the client handler has sent the response to the setUsername method
     */
    private void runSetUsernameResponse(){
        String line = null;
        try {
            line = input.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(line.equals("username already exists")){
            // invoco metodo della ui per l'eccezione
            runUsernameAlreadyExists();
        } else {
            try {
                line = input.readLine();
            } catch (IOException e){
                e.printStackTrace();
            }
            this.username = line;
        }
    }

    /**
     * Method executed after the client handler has sent the response to the getGameList method
     */
    private void runGetGameListResponse(){
        String line = null;
        List<String> list = new ArrayList<>();
        try {
            line = input.readLine();
            if(line.equals("no game exception")){
                //invoco metodo della ui opportuno o un metodo del client

            } else {
                while (!(line = input.readLine()).equals("game list finished"))
                    list.add(line);
            }
            ui.show_listGame(list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Method invoked by the UI, client-side

    /**
     * This method is the first called by the client, it sends the client handler
     * the request to execute the "connect" method. If the server has already this
     * username ane exception is launched
     * @param username is the username set by the client
     */
    @Override
    public void setUsername(String username){
        output.println("connect");
        output.println(username);
        output.flush();
        /*
        String line = input.readLine();
        if (line.equals("username already exists"))
            throw new PlayerNicknameAlreadyExistsException();
        else if (line.equals("username set"))
            this.username = username;
        */
    }



    /**
     * This method sends to the client handler the string corresponding with the
     * create game request
     * @param maxNumberPlayer is the max number of the players for the new game
     */
    @Override
    public void createGame(int maxNumberPlayer){
        output.println("create game");
        output.println(maxNumberPlayer);
        output.flush();

        // Se non dovesse ricevere la stringa corretta/ci fosse un errore lato server
        // cosa dovrei fare?
        // Leggo dal server il game ID della partita appena creata
//        String line = input.readLine();
//        this.idGame = Integer.parseInt(line);
//        ui.show_gameCreated();
    }

    /**
     * This method sends to the server a specific string, asking for a gold card draw
     * @throws RemoteException if a tcp connection error occurs
     */
    @Override
    public void drawGold() throws RemoteException {
        output.println("draw gold");
        output.flush();
    }

    /**
     * This method sends a specific string to the server, asking for a resource draw
     * @throws RemoteException if a tcp connection error occurs
     */
    @Override
    public void drawResource() throws RemoteException {
        output.println("draw resource");
        output.flush();
    }

    /**
     * This method send the string that identifies the join game request made
     * by the player
     * @param gameId is the gameId of the particular game the player wants to join
     * @throws RemoteException if a tcp connection error occurs
     */
    @Override
    public void joinGame(int gameId) throws RemoteException {
        output.println("join game");
        output.println(gameId);
        output.flush();
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
     */
    @Override
    public void getGameList(){
        output.println("get game list");
        output.flush();

//        String line = input.readLine();
//        if (line.equals("no game exception"))
//            throw new NoGamesException();
//        else if (line.equals("ok")) {
//            while (!(line = input.readLine()).equals("game list finished"))
//                list.add(line);
//        }
//        ui.show_listGame(list);
    }

    /**
     * This method returns the player's game idGame
     * @return the idGame of the game
     */
    @Override
    public int getGameID(){
        return idGame;
    }

    /**
     * Method getter of the username
     * @return the username of the client
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * This method set the UI for the TCPClient
     * @param ui is the concrete UI that needs to be assigned for this TCPClient
     */
    @Override
    public void setUI(UI ui) {
        this.ui = ui;
    }

    /**
     * This method send to the client handler the request to change the actual value of the "ready" attribute to the new value sent
     * @param ready is the boolean value that represents if the player is ready to play
     */
    @Override
    public void setReady(boolean ready){
        output.println("ready");
        if(ready)
            output.println("true");
        else
            output.println("false");
        output.flush();
    }
}
