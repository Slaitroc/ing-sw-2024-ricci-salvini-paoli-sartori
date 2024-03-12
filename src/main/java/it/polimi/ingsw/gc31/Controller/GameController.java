package it.polimi.ingsw.gc31.Controller;

import java.util.List;
import java.util.ArrayList;


import it.polimi.ingsw.gc31.Model.GameModel;
import it.polimi.ingsw.gc31.Model.Player.Player;
import it.polimi.ingsw.gc31.View.GameView;

public class GameController {
    
    private GameModel gameModel;
    private GameView gameView;
    private List<Player> playerList;
    private int maxNumPlayer;

    public GameController(){
        playerList = new ArrayList<Player>();
        maxNumPlayer = 4; //TODO capire questa cosa
    }

    public GameController(int mNP, String username){
        this();
        playerList.add(new Player(Color color, String username));//dovrebbe averlo implementato Matte
    }

    //TODO come assegnamo il colore della pedina? ci starebbe assegnarlo randomicamente e checkare quelle già assegnate


    
}
