package it.polimi.ingsw.gc31.model;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.player.Player;

/**
 * This class represents the game model of the application.
 * It contains the game board and a map of players.
 * It also has a static variable pawnSelector for assigning pawn colors to players.
 *
 * @author Slaitroc
 */
public class GameModel {
    private final Board board;
    private final Map<String, Player> players;
    private static int pawnSelector = 0;

    /**
     * Constructor for the GameModel class.
     * It initializes the board and the players map.
     */
    public GameModel() {
        this.board = new Board();
        this.players = new HashMap<>();
    }

    /**
     * This method adds a player to the players map.
     *
     * @param username the username of the player to add.
     */
    public void addPlayer(String username) {
        players.put(username, null);
    }

    /**
     * This method creates Player objects for each username in the players map.
     * It assigns a pawn color to each player and associates the player with the game board.
     * The method updates the players map by replacing the null value associated with each username with the newly created Player object.
     *
     * @return A map of usernames to Player objects.
     */
    public Map<String, Player> createPlayers() {
        for (String pls : players.keySet()) {
            players.put(pls, new Player(pawnAssignment(), pls, board));
        }
        return players;
    }

    /**
     * This method returns the Player object associated with the given username.
     *
     * @param username the username of the player to retrieve.
     * @return The Player object associated with the given username.
     */
    public Player getPlayer(String username) {
        return players.get(username);
    }

    /**
     * This method deals cards to each player in the game.
     * It calls the drawGold and drawResource methods of the Player class.
     * If the drawResource method throws an EmptyDeckException, it is caught and a RuntimeException is thrown.
     */
    public void dealCards() {
        for (Map.Entry<String, Player> pl : players.entrySet()) {
            try {
                pl.getValue().drawGold();
                pl.getValue().drawResource();
                pl.getValue().drawResource();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method assigns a pawn color to a player.
     * It uses the static variable pawnSelector to determine the color.
     * The pawnSelector is incremented after each assignment.
     *
     * @return The PawnColor assigned to the player.
     */
    private PawnColor pawnAssignment() {
        //TODO do we let the player choose his color?
        PawnColor color = switch (pawnSelector) {
            case 0 -> PawnColor.RED;
            case 1 -> PawnColor.BLUE;
            case 2 -> PawnColor.GREEN;
            case 3 -> PawnColor.YELLOW;
            default -> null;
        };
        pawnSelector++;
        return color;
    }

}
