package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.model.player.Player;

public class GameModel {
   
    private int pawnSelector = 0; // NOTE o meglio playerCount
    private Board board;
    private List<Player> players;
    private Player playingPlayer;

    public GameModel(List<String> userList) {
        players = new ArrayList<Player>();
        createPlayers(userList);
        playingPlayer = players.get(0);
        board = new Board();

    }
    // public void beginEndGame(){}
    // private void pick(Player p, Deck d){}
    // private boolean checkPoints(){return false;}

    /**
     * Create a new player for each nickname in the list
     * 
     * @param userList : List of usernames
     * @Slaitroc
     */
    private void createPlayers(List<String> userList) {
        for (String user : userList)
            players.add(new Player(pawnAssignment(), user));
    }

    /**
     * Assigns the right pawn Color to the player
     * 
     * @return Color object
     * @Slaitroc
     */
    private Color pawnAssignment() {
        Color color;
        // WARN alternativa allo switch??
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
            case 4:
                color = Color.PURPLE;
                break;
            case 5:
                color = Color.BLACK;
                break;
            default:
                color = Color.NOCOLOR;// NOTE piú per debugging che per utilitá
        }
        pawnSelector++;
        return color;
    }


}
