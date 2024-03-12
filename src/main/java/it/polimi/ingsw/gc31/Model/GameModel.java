package it.polimi.ingsw.gc31.Model;

import java.util.List;

import it.polimi.ingsw.gc31.Model.Player.Player;
import it.polimi.ingsw.gc31.Model.Deck.Deck;


public class GameModel {
    
    private Board board;
    private List<Player> players;
    private Player playingPlayer;

    public GameModel(List<String> nicknames){}
    /* public void beginEndGame(){} */
    private void pick(Player p, Deck d){}
    private boolean checkPoints(){return false;}


}
