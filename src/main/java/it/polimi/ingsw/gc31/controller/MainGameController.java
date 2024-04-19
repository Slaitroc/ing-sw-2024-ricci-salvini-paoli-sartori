package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IMainGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the main game controller.
 * It manages the game logic and the interaction with the clients.
 * It extends UnicastRemoteObject and implements IMainGameController to allow remote method invocation.
 */
public class MainGameController extends UnicastRemoteObject implements IMainGameController {

    private boolean isStarted;
    private final GameController gameController;
    private final Map<String, VirtualClient> clients;
    private final Map<String, PlayerController> players;
    private final int maxNumberPlayers;
    private String gameID;

    /**
     * Constructor for the MainGameController class.
     * It initializes the game controller,
     * the clients map, the player's map, the maximum number of players, and the game ID.
     * It also adds the first player to the game.
     *
     * @param username         the username of the first player.
     * @param client           the client of the first player.
     * @param maxNumberPlayers the maximum number of players for the game.
     * @param gameID           the ID of the game.
     * @throws RemoteException if an RMI error occurs.
     */
    public MainGameController(String username, VirtualClient client, int maxNumberPlayers, String gameID)
            throws RemoteException {
        // gson = new GsonBuilder()
        // .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
        // .create();
        this.gameController = new GameController();
        this.clients = new HashMap<>();
        this.players = new HashMap<>();
        this.maxNumberPlayers = maxNumberPlayers;
        this.isStarted = false;
        this.gameID = gameID;

        this.clients.put(username, client);
        gameController.addPlayer(username);
        mgcWrite("Player joined: " + username);
    }

    /**
     * This method prints a message to the console with a specific format.
     *
     * @param text the message to print.
     */
    private void mgcWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_CYAN
                + DefaultValues.mainControllerTag(this.gameID) + DefaultValues.ANSI_RESET + text);
    }

    /**
     * This method initializes the game.
     * It creates the players and deals the cards.
     *
     * @throws RemoteException if an RMI error occurs.
     */
    private void initGame() throws RemoteException {
        Map<String, Player> playerList = gameController.initGame();
        for (Map.Entry<String, Player> pl : playerList.entrySet()) {
            players.put(pl.getKey(), new PlayerController(pl.getValue(), clients.get(pl.getKey())));
        }
        for (Map.Entry<String, VirtualClient> entry : clients.entrySet()) {
            entry.getValue().setPlayerController(players.get(entry.getKey()));
        }

        mgcWrite("Game started! Players: " + players.keySet().stream().toList());
        System.out.println("Distributing cards...");
        gameController.dealCard();
        this.isStarted = true;
    }

    /**
     * This method allows a player to join the game.
     *
     * @param username the username of the player who wants to join the game.
     * @param client   the client of the player who wants to join the game.
     * @throws RemoteException if an RMI error occurs.
     */
    public void joinGame(String username, VirtualClient client) throws RemoteException {
        gameController.addPlayer(username);
        mgcWrite("Player joined: " + username);
        clients.put(username, client);

        // if (maxNumberPlayers == this.getCurrentNumberPlayers()) {
        // this.initGame();
        // }
    }

    /**
     * @return the maximum number of players.
     */
    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    /**
     * @return the current number of players.
     */
    public int getCurrentNumberPlayers() {
        return clients.size();
    }

    /**
     * @return true if the game has started, false otherwise.
     */
    @Override
    public boolean isGameStarted() {
        return isStarted;
    }

    /**
     * This method checks if all players are ready.
     * If all players are ready, it initializes the game.
     *
     * @return true if all players are ready, false otherwise.
     * @throws RemoteException if an RMI error occurs.
     */
    @Override
    public boolean checkReady() throws RemoteException {
        int ready = 0;
        for (VirtualClient client : clients.values()) {
            if (client.isReady())
                ready++;
        }
        if (ready == maxNumberPlayers) {
            this.initGame();
            return true;
        }
        return false;
    }

    /**
     * This method starts the game for all clients.
     *
     * @throws RemoteException if an RMI error occurs.
     */
    @Override
    public void startGame() throws RemoteException {
        for (VirtualClient client : clients.values()) {
            client.startGame();
        }
    }
}
