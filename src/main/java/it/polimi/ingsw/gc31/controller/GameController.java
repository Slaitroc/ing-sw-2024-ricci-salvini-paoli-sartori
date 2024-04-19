package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class GameController extends UnicastRemoteObject implements IGameController {
    private GameModel model;
    private Map<String, IPlayerController> players;
    private Map<String, VirtualClient> clients;
    private int maxNumberPlayers;
    private int idGame;
    boolean isStarted = false;
    // private final Map<String, Player> players;

    public GameController(String username, VirtualClient client, int maxNumberPlayers, int idGame) throws RemoteException{
        this.model = new GameModel();
        this.maxNumberPlayers = maxNumberPlayers;
        this.idGame = idGame;
        this.players = new HashMap<>();
        this.clients = new HashMap<>();

        this.clients.put(username, client);
        this.model.addPlayer(username);

        // TODO mandare messaggio al client di avvenuta creazione della partita
    }

    public void initGame() throws RemoteException {
        Map<String, Player> playerList = model.createPlayers();
        model.dealCards();

        for (Map.Entry<String, Player> pl: playerList.entrySet()) {
            players.put(pl.getKey(), new PlayerController(pl.getValue(), clients.get(pl.getKey())));
        }
        for (Map.Entry<String, VirtualClient> entry : clients.entrySet()) {
            entry.getValue().setPlayerController(players.get(entry.getKey()));
        }

        // TODO mandare messaggio al client di inizio partita
//        for (VirtualClient player: clients.values()) {
//            player.sendMessage("[GameController] la partita è iniziata");
//        }
    }

    public void joinGame(String username, VirtualClient client) throws RemoteException{
        this.model.addPlayer(username);
        clients.put(username, client);

        // TODO mandare messaggio al client di connessione al server
        if (maxNumberPlayers == this.clients.size()) {
            gameControllerWrite("Il numero di giocatori per la partita "+idGame+" è stato raggiunto");
        }
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }
    public int getCurrentNumberPlayers() {
        return clients.size();
    }

    private void gameControllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_BLUE
                + DefaultValues.CONTROLLER_TAG + DefaultValues.ANSI_RESET + text);
    }
}
