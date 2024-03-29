package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.DeepCopy;

public class GameModel{
    private final Board board;
    private final Map<String, Player> players;

    public GameModel() {
        this.board = new Board();
        this.players = new HashMap<>();
    }
    public void addPlayers(Map<String, Player> players) {
        for (String user : players.keySet()) {
            // ricrea i player passandogli come parametro la board
            this.players.put(user, new Player(players.get(user), board));
        }
    }
    public Player getPlayer(String username) {
        return players.get(username);
    }
    public void dealCards() {
        for (Map.Entry<String, Player> pl: players.entrySet()) {
            pl.getValue().drawGold();
            pl.getValue().drawResource();
            pl.getValue().drawResource();
        }
    }


//    @Override
//    public GameModel deepCopy() {
//        GameModel clone = new GameModel();
//        for (Player player : this.players) {
//            Player playerClone = new Player(clone.pawnAssignment(), player.getName());
//            clone.players.add(playerClone);
//            if (playingPlayer == player)
//                clone.playingPlayer = playerClone;
//        }
//        clone.board = this.board.deepCopy();
//        return clone;
//    }
}
