package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IMainGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class MainGameController extends UnicastRemoteObject implements IMainGameController {

    private boolean isStarted;
    private final GameController gameController;
    private final Map<String, VirtualClient> clients;
    private final Map<String, PlayerController> players;
    private final int maxNumberPlayers;
    private String gameID;

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

    private void mgcWrite(String text) {
        System.out.println(DefaultValues.RMI_SERVER_TAG + DefaultValues.mainControllerTag(this.gameID) + text);
    }

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

    public void joinGame(String username, VirtualClient client) throws RemoteException {
        gameController.addPlayer(username);
        mgcWrite("Player joined: " + username);
        clients.put(username, client);

        // if (maxNumberPlayers == this.getCurrentNumberPlayers()) {
        // this.initGame();
        // }
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    public int getCurrentNumberPlayers() {
        return clients.size();
    }

    @Override
    public boolean isGameStarted() {
        return isStarted;
    }

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

    @Override
    public void startGame() throws RemoteException {
        for (VirtualClient client : clients.values()) {
            client.startGame();
        }
    }
}
