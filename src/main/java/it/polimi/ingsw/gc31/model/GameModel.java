package it.polimi.ingsw.gc31.model;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
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
     * Assigns the right pawn Color to the player
     *
     * @return Color object
     * @author Slaitroc
     */
    private PawnColor pawnAssignment() {
        PawnColor color = null;
        // WARN alternativa allo switch??
        // FIX lo facciamo scegliere al player?
        switch (pawnSelector) {
            case 0:
                color = PawnColor.RED;
                break;
            case 1:
                color = PawnColor.BLUE;
                break;
            case 2:
                color = PawnColor.GREEN;
                break;
            case 3:
                color = PawnColor.YELLOW;
                break;
            //default:
        }
        pawnSelector++;
        return color;
    }

}
