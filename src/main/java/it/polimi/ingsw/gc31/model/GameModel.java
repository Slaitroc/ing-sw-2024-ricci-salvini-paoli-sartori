package it.polimi.ingsw.gc31.model;

import java.util.HashMap;
import java.util.Map;

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
        // for each player, deal one gold card and two resource cards
        for (Map.Entry<String, Player> pl : players.entrySet()) {
            pl.getValue().drawGold();
            pl.getValue().drawResource();
            pl.getValue().drawResource();
        }

        // after dealing the cards, two gold cards and two resource card are revealed on the table
        board.getDeckGold().refill();
        board.getDeckResource().refill();
    }

    /**
     * Assigns the right pawn Color to the player
     *
     * @return Color object
     * @author Slaitroc
     */
    private PawnColor pawnAssignment() {
        PawnColor pawnColor = null;
        // WARN alternativa allo switch??
        // FIX lo facciamo scegliere al player?
        switch (pawnSelector) {
            case 0:
                pawnColor = PawnColor.RED;
                break;
            case 1:
                pawnColor = PawnColor.BLUE;
                break;
            case 2:
                pawnColor = PawnColor.GREEN;
                break;
            case 3:
                pawnColor = PawnColor.YELLOW;
                break;
            //default:
            //    cardColor = CardColor.NOCOLOR;// NOTE piú per debugging che per utilitá
        }
        pawnSelector++;
        return pawnColor;
    }

}
