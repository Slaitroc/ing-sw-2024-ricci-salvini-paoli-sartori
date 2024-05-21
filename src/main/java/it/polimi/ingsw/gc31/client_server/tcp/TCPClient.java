package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

public class TCPClient implements ClientCommands {
    private final BufferedReader input;
    private final PrintWriter output;
    @SuppressWarnings("unused")
    private String username;
    private Integer idGame;
    private Map<String, Runnable> commandsMap;
    private UI ui;

    /**
     * This method is the constructor of the TCPClient
     * 
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public TCPClient() throws IOException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        Socket serverSocket = new Socket("127.0.0.1", 1200);
        this.input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.output = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        initializeMap();
        // run();
    }

    /**
     * Method that initializes the map used to invoke the methods by the server
     */
    private void initializeMap() {
        this.commandsMap = new HashMap<>();
        this.commandsMap.put("show hand", this::runShowHandPlayer);
        this.commandsMap.put("show score", this::runShowScorePlayer);
        this.commandsMap.put("show starter card", this::runShowStarterCard);
        this.commandsMap.put("show objective card", this::runShowObjectiveCard);
        this.commandsMap.put("show gold deck", this::runShowGoldDeck);
        this.commandsMap.put("show resource deck", this::runShowResourceDeck);
        this.commandsMap.put("show objective deck", this::runShowObjectiveDeck);
        this.commandsMap.put("show game list", this::runShowGameList);
    }

    /**
     * Method invoked by the server that show the player's hand (also the
     * hands of the other players)
     */
    private void runShowHandPlayer() {
        // username non viene utilizzato
        // String targetUsername = input.readLine();
        String line;
        List<String> hand = new ArrayList<>();
        try {
            while (!(line = input.readLine()).equals("end list")) {
                hand.add(line);
            }
            // ui.showHand(hand);//TODO da capire
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the server that shows the player's score
     */
    @SuppressWarnings("unused")
    private void runShowScorePlayer() {
        try {
            String targetUsername = input.readLine();
            Integer score = Integer.parseInt(input.readLine());
            // probabile metodo da implementare all'interno della ui
            // ui.showScore(targetUsername, score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked by the server that shows the starter card
     */
    private void runShowStarterCard() {
        /*
         * try{
         * //ui.showStarterCard(input.readLine());
         * } catch (IOException e){
         * e.printStackTrace();
         * }
         * 
         */
    }

    /**
     * Method invoked by the server that shows the objective card
     */
    private void runShowObjectiveCard() {
        /*
         * try {
         * //ui.showObjectiveCard(input.readLine());
         * } catch (IOException e){
         * e.printStackTrace();
         * }
         * 
         */
    }

    /**
     * Method invoked by the server that shows the gold deck
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
     * Method invoked by the server that shows the resource deck
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
     * Method invoked by the server that shows the objective deck
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
     * Method invoked by the server that shows the list of all the existing game
     */
    private void runShowGameList() {
        String line;
        List<String> list = new ArrayList<>();
        try {
            while (!(line = input.readLine()).equals("game list finished")) {
                list.add(line);
            }
            ui.show_listGame(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method invoked at the creation of the class. Create a thread that run
     * the runVirtualServer method
     */
    public void run() {
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
     * client
     *
     * This method listen the messages coming from the server and invokes the
     * corresponding
     * method
     * 
     * @throws RemoteException
     */
    public void runVirtualServer() throws RemoteException {
        @SuppressWarnings("unused")
        Scanner scan = new Scanner(input);
        String line;
        try {
            while ((line = input.readLine()) != null) {
                commandsMap.get(line).run();

                /*
                 * switch (line) {
                 * case "show list game": {
                 * List<String> list = new ArrayList<>();
                 * list.add("ciao");
                 * ui.show_listGame(list);
                 * }
                 * //default:
                 * // System.out.println(line);
                 * }
                 */
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    public void setUsername(String username) throws IOException {
        output.println("connect");
        output.println(username);
        output.flush();

        String line = input.readLine();
        if (line.equals("username already exists"))
            ;// FIX
        else if (line.equals("username set"))
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
        output.println("create game");
        output.println(maxNumberPlayer);
        output.flush();

        // Se non dovesse ricevere la stringa corretta/ci fosse un errore lato server
        // cosa dovrei fare?
        // Leggo dal server il game ID della partita appena creata
        String line = input.readLine();
        this.idGame = Integer.parseInt(line);
        //ui.show_gameCreated();
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
     *
     * @throws IOException      is launched if an error is occurred in the readLine
     *                          method
     * @throws NoGamesException is launched if there are no created games
     */
    @Override
    public void getGameList() throws IOException, NoGamesException {
        List<String> list = new ArrayList<>();
        output.println("get game list");
        output.flush();

        String line = input.readLine();
        if (line.equals("no game exception"))
            throw new NoGamesException();
        else if (line.equals("ok")) {
            while (!(line = input.readLine()).equals("game list finished"))
                list.add(line);
        }
        ui.show_listGame(list);
    }

    @Override
    public void setReady(boolean ready) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void drawGold() throws RemoteException {
        output.println("draw gold");
        output.flush();
    }

    @Override
    public void drawGoldCard1() throws RemoteException {
        output.println("draw gold card 1");
        output.flush();
    }

    @Override
    public void drawGoldCard2() throws RemoteException {
        output.println("draw gold card 2");
        output.flush();
    }

    @Override
    public void drawResource() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawResource'");
    }

    @Override
    public void drawResourceCard1() throws RemoteException {
        output.println("draw resource card 1");
        output.flush();
    }

    @Override
    public void drawResourceCard2() throws RemoteException {
        output.println("draw resource card 2");
        output.flush();
    }

    @Override
    public void chooseSecretObjective1() throws RemoteException {
        output.println("choose secret objective 1");
        output.flush();
    }

    @Override
    public void chooseSecretObjective2() throws RemoteException {
        output.println("choose secret objective 2");
        output.flush();
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

    }
}
