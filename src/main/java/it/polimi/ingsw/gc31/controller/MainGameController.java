package it.polimi.ingsw.gc31.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.rmi.VirtualView;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainGameController extends UnicastRemoteObject implements VirtualMainGameController{
    private boolean isStarted;
    @Override
    public boolean isGameStarted() {
        return isStarted;
    }

    private Gson gson;
    private final GameController gameController;
    private final Map<String, VirtualView> clients;
    private final Map<String, PlayerController> players;
    private final int maxNumberPlayers;

    public MainGameController(String username, VirtualView client, int maxNumberPlayers) throws RemoteException{
        gson = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();
        this.gameController = new GameController();
        this.clients = new HashMap<>();
        this.players = new HashMap<>();
        this.maxNumberPlayers = maxNumberPlayers;
        this.isStarted = false;

        this.clients.put(username, client);
        gameController.addPlayer(username);
        System.out.println("[SERVER-MainGameController] Added player: "+username);
    }
    public void initGame() throws RemoteException {
        Map<String, Player> playerList = gameController.initGame();
        for (Map.Entry<String, Player> pl: playerList.entrySet()) {
            players.put(pl.getKey(), new PlayerController(pl.getValue(), clients.get(pl.getKey())));
        }

        for (Map.Entry<String, VirtualView> var: clients.entrySet()) {
            var.getValue().setPlayerController(players.get(var.getKey()));
        }

        System.out.println("[SERVER-MainGameController] Partita iniziata, i player in gioco sono: "+players.keySet().stream().toList());
        System.out.println("[SERVER-MainGameController] Distribuzione carte...");
        gameController.dealCard();

        this.isStarted = true;
    }


    public void joinGame(String username, VirtualView client) throws RemoteException {
        gameController.addPlayer(username);
        System.out.println("Added players: "+username);
        clients.put(username, client);

        if (maxNumberPlayers == this.getCurrentNumberPlayers()) {
            this.initGame();
        }
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }
    public int getCurrentNumberPlayers() {
        return clients.size();
    }
}
