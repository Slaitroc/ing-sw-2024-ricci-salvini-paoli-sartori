package it.polimi.ingsw.gc31.model;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.player.Player;

public class GameModel {
    private final Board board;
    private final Map<String, Player> players;
    private static int pawnSelector = 0;

    public GameModel() {
        this.board = new Board();
        this.players = new HashMap<>();
    }

    public void addPlayer(String username) {
        players.put(username, null);
    }

    public Map<String, Player> createPlayers() {
        for (String pls : players.keySet()) {
            players.put(pls, new Player(pawnAssignment(), pls, board));

        }

        return players;
    }

    public Player getPlayer(String username) {
        return players.get(username);
    }

    public void dealCards() {
        for (Map.Entry<String, Player> pl : players.entrySet()) {
            pl.getValue().drawGold();
            pl.getValue().drawResource();
            pl.getValue().drawResource();
        }
    }

    /**
     * Assigns the right pawn Color to the player
     *
     * @return Color object
     * @author Slaitroc
     */
    private Color pawnAssignment() {
        Color color;
        // WARN alternativa allo switch??
        // FIX lo facciamo scegliere al player?
        switch (pawnSelector) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            default:
                color = Color.NOCOLOR;// NOTE piú per debugging che per utilitá
        }
        pawnSelector++;
        return color;
    }

}
