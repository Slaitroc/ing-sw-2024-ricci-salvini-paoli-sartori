package it.polimi.ingsw.gc31.controller;

import java.util.Map;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class GameController {
    private GameModel model;
    // private final Map<String, Player> players;

    public GameController() {
        this.model = new GameModel();
        // this.players = new HashMap<>();
    }

    public Map<String, Player> initGame() {
        Map<String, Player> players = model.createPlayers();
        model.dealCards();

        return players;
    }

    public void addPlayer(String username) {
        model.addPlayer(username);
    }

    public void dealCard() {
        model.dealCards();
    }
}
