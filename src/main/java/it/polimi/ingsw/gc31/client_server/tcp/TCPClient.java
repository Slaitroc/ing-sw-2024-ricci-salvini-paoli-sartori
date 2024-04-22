package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

import java.util.ArrayList;

/*
    La classe TCPClient in maniera simile all'RmiClient dovrebbe implementare i metodi di VirtualClient
    che vengono richiamati dalla TUI. Tuttavia, al contrario dell'RmiClient il TCPClient non dovrebbe
    avere il modo di richiamare i metodi del controller ma dovrebbe invece inoltrare il comando richiesto
    al server grazie al VirtualSocketServer
 */
public class TCPClient implements ClientCommands {
    private final BufferedReader input;
    private final PrintWriter output;
    private String username;
    private Integer idGame;
    private UI ui;

    /**
     * This method is the constructor of the TCPClient
     * 
     * @throws IOException
     */
    public TCPClient() throws IOException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        Socket serverSocket = new Socket("127.0.0.1", 1200);
        this.input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.output = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        // run();
    }

    /**
     * Method disabled. It's only purpose was to launch the runVirtualServer method
     * (below)
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
     * This method is disabled because the messages sent by the server are red
     * in every method corresponding with the specific request launched by the
     * client
     * 
     * @throws RemoteException
     */
    public void runVirtualServer() throws RemoteException {
        Scanner scan = new Scanner(input);
        String line;
        while (true) {
            line = scan.nextLine();
            switch (line) {
                case "show list game": {
                    List<String> list = new ArrayList<>();
                    list.add("ciao");
                    ui.showListGame(list);
                }
                // default:
                // System.out.println(line);
            }
        }
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
    public void setUsername(String username) throws IOException, PlayerNicknameAlreadyExistsException {
        output.println("connect");
        output.println(username);
        output.flush();

        String line = input.readLine();
        if (line.equals("username already exists"))
            throw new PlayerNicknameAlreadyExistsException();
        else if (line.equals("username set"))
            this.username = username;
    }

    /**
     * This method send to the client handler the string corresponding with the
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
        ui.show_gameCreated();
    }

    @Override
    public void drawGold() throws RemoteException {
        output.println("draw gold");
        output.flush();
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
        ui.showListGame(list);
    }

    /**
     * NOTE: The TCPClient doesn't have any controller, the controllers are held in
     * the client handler
     * the setter is invoked by the GameController (server-side). It's useless to
     * have this method here.
     * This method sets a specific IPlayerController for the controller of the
     * TCPClient
     * 
     * @param playerController is the specific controller to be assigned
     * @throws RemoteException
     */
    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {

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

    /**
     * This method set the UI for the TCPClient
     * 
     * @param ui is the concrete UI that needs to be assigned for this TCPClient
     */
    @Override
    public void setUI(UI ui) {
        this.ui = ui;
    }
}
