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

public class Controller implements ControllerInterface{
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

        gameController.addPlayer(username);
        System.out.println("[SERVER-Controller] Added player: "+username);

        gson = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();

    }
    private void initGame() {
        this.players = gameController.initGame();
        System.out.println("[SERVER-Controller] Partita iniziata, i player in gioco sono: "+players.keySet().stream().toList());
        System.out.println("[SERVER-Controller] Distribuzione carte...");
        gameController.dealCard();
    }
    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }
    public int getCurrentNumberPlayers() {
        return clients.size();
    }
    @Override
    public void joinGame(String username, VirtualView client) throws RemoteException {
        gameController.addPlayer(username);
        System.out.println("Added players: "+username);
        clients.put(username, client);
        if (maxNumberPlayers == clients.size()) {
            this.initGame();
        }
    }

    @Override
    public void getHand(String username) throws RemoteException {
        List<PlayableCard> hand = players.get(username).getHand();
        List<String> res = new ArrayList<>();
        for (PlayableCard card: hand) {
            res.add(gson.toJson(card, PlayableCard.class));
        }
        clients.get(username).showHand(res);
    }

    @Override
    public void drawGold(String username) throws RemoteException {
        this.players.get(username).drawGold();

        // TODO da rivedere
        for (var c: clients.entrySet()) {
            c.getValue().reportError(username + "draw a gold Card");
        }
    }
}
