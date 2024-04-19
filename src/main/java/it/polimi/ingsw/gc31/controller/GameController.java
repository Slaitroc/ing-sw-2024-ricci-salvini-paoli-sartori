package it.polimi.ingsw.gc31.controller;

import java.util.Map;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

/**
 * This class represents the game controller.
 * It interacts with the GameModel to manage the game logic.
 */
public class GameController {
    private GameModel model;
    // private final Map<String, Player> players;

    /**
     * Constructor for the GameController class.
     * It initializes the GameModel.
     */
    public GameController() {
        this.model = new GameModel();
        // this.players = new HashMap<>();
    }

    /**
     * This method initializes the game.
     * It creates the players and deals the cards.
     *
     * @return A map of usernames to Player objects.
     */
    public Map<String, Player> initGame() {
        Map<String, Player> players = model.createPlayers();
        model.dealCards();

        return players;
    }

    /**
     * This method adds a player to the game.
     *
     * @param username the username of the player to add.
     */
    public void addPlayer(String username) {
        model.addPlayer(username);
    }

    /**
     * This method deals cards to each player in the game.
     */
    public void dealCard() {
        model.dealCards();
    }
}
