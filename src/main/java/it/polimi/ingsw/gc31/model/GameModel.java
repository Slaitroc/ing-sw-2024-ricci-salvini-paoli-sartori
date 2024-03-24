package it.polimi.ingsw.gc31.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.DeepCopy;

public class GameModel implements Cloneable, DeepCopy<GameModel> {

    private int pawnSelector; // NOTE o meglio playerCount
    private Board board;
    private List<Player> players;
    private Player playingPlayer;

    private GameModel() {
        pawnSelector = 0;
        players = new ArrayList<Player>();
        board = new Board();

    }

    public GameModel(List<String> userList) {
        this();
        createPlayers(userList);
        playingPlayer = players.get(0);
        cardsToHands();

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

    private void cardsToHands() {
        for (Player p : players) {
            p.addToHand(board.getDeckGold().draw());
            p.addToHand(board.getDeckGold().draw());
            p.addToHand(board.getDeckResource().draw());

            PlayableCard starterCard = board.getDeckStarer().draw();
            starterCard.changeSide();
            // TODO dare la possibilità di girarla prima di piazzarla, per ora who cares
            p.getPlayArea().placeStarter(starterCard);

        }
    }

    @Override
    public GameModel deepCopy() {
        GameModel clone = new GameModel();
        for (Player player : this.players) {
            Player playerClone = new Player(clone.pawnAssignment(), player.getName());
            clone.players.add(playerClone);
            if (playingPlayer == player)
                clone.playingPlayer = playerClone;
        }
        clone.board = this.board.deepCopy();
        return clone;
    }

}
