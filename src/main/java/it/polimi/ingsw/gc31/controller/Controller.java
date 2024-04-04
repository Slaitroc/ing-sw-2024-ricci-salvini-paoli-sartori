package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.rmi.VirtualView;
import it.polimi.ingsw.gc31.utility.DeepCopy;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;
import javafx.scene.layout.CornerRadii;

import java.awt.Color;

//NOTE creazione GameController x la creazione del match
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller

// gestisce l'interazione con i client

public class Controller{
    // gson per serializzare i dati da inviare al client
    private Gson gson;
    private final GameController gameController;
    private final Map<String, VirtualView> clients;
    private Map<String, PlayerController> players;
    private final int maxNumberPlayers;

    public Controller(String username, VirtualView client, int maxNumberPlayers) {
        this.gameController = new GameController();
        this.maxNumberPlayers = maxNumberPlayers;
        this.clients = new HashMap<>();
        this.clients.put(username, client);
        this.players = new HashMap<>();

        gameController.addPlayer(username);
        System.out.println("[SERVER-Controller] Added player: " + username);

        gson = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();
    }

    private void initGame() {
        Map<String, Player> playerList = gameController.initGame();
        for (Map.Entry<String, Player> pl: playerList.entrySet()) {
            players.put(pl.getKey(), new PlayerController(pl.getValue()));
        }
        System.out.println("[SERVER-Controller] Partita iniziata, i player in gioco sono: " + players.keySet().stream().toList());
        System.out.println("[SERVER-Controller] Distribuzione carte...");
        gameController.dealCard();
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    public int getCurrentNumberPlayers() {
        return clients.size();
    }

    public void joinGame(String username, VirtualView client) throws RemoteException {
        gameController.addPlayer(username);
        System.out.println("Added players: " + username);
        clients.put(username, client);
        if (maxNumberPlayers == clients.size()) {
            this.initGame();
        }
    }
    public void getHand(String username) throws RemoteException {
        List<PlayableCard> hand = players.get(username).getHand();
        List<String> res = new ArrayList<>();
        for (PlayableCard card: hand) {
            res.add(gson.toJson(card, PlayableCard.class));
        }
        clients.get(username).showHand(res);
    }
    public void drawGold(String username) throws RemoteException {
        this.players.get(username).drawGold();

        // TODO da rivedere
        for (var c: clients.entrySet()) {
            c.getValue().reportError(username + "draw a gold Card");
        }
    }

}

// public class Controller implements Cloneable, DeepCopy<Controller> {

// private final List<GameController> gamesList; // NOTE meglio Set o List?
// private final Set<String> globalUsernameSet;

// public Controller() {
// this.gamesList = new ArrayList<GameController>();
// this.globalUsernameSet = new HashSet<String>();

// }

// /**
// *
// * @param username : player's nickname
// * @return true if the player's nickname already exists, false otherwise
// * @autor Slaitroc
// */
// private boolean doesNameAlreadyExist(String username) {
// if (globalUsernameSet.contains(username))
// return true;
// else
// return false;

// }

// /**
// * Add the new GameController to the Controller gameList
// *
// * @param username player's nickname
// * @return the gamePosition of the GameController in gameSet
// */
// public int createGameController(String username, int numPlayers) {
// gamesList.add(new GameController(numPlayers, username));
// return gamesList.size() - 1;
// }

// /**
// * @apiNote add the player's nickname to the globalPlayersSet
// * @param username
// * @throws PlayerNicknameAlreadyExistsException if the nickname is already in
// * use
// */
// public void addPlayerUsername(String username) throws
// PlayerNicknameAlreadyExistsException {
// if (!doesNameAlreadyExist(username))
// globalUsernameSet.add(username);
// else
// throw new PlayerNicknameAlreadyExistsException();
// }

// public GameController getGameController(int gameID) {
// return gamesList.get(gameID);
// }

// public Set<String> getGlobalUsernameSet() {
// return globalUsernameSet;
// }

// @Override
// public Controller deepCopy() {
// Controller cloned = new Controller();
// for (GameController gc : gamesList) {
// cloned.gamesList.add(gc.deepCopy());
// }
// for (String nickname : globalUsernameSet) {
// cloned.globalUsernameSet.add(new String(nickname));
// }
// return cloned;
// }

// }