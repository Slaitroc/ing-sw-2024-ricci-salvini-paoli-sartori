package it.polimi.ingsw.gc31.Controller;

import java.util.List;
import java.util.ArrayList;

import it.polimi.ingsw.gc31.Model.GameModel;
import it.polimi.ingsw.gc31.Model.Player.Player;
import it.polimi.ingsw.gc31.View.GameView;
import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Exceptions.MaxPlayerNumberReachedException;

public class GameController {

    // TODO Game ID
    // private ID;
    private int pawnSelector;

    private GameModel gameModel;
    private GameView gameView;
    private List<Player> playerList;
    private int maxNumPlayer;

    public GameController() {
        playerList = new ArrayList<Player>();
        maxNumPlayer = 4;
        // TODO scegliere come gestire il colore
        // input dal player o assegnazione randomica? Opterei per questa
    }

    public GameController(int mNP, String username) {
        this();
        playerList.add(new Player(Color.RED, username)); // WARN per ora sto assegnando a tutti i player la pedina rossa
        maxNumPlayer = mNP;
        pawnSelector++;
    }

    public void addPlayer(String username) throws MaxPlayerNumberReachedException{
        if (maxNumPlayer == playerList.size()) throw new MaxPlayerNumberReachedException();
        Color color;
        // WARN alternativa allo switch??
        //FIX Spostalo nel Model -> le pedine le assegnamo durante la creazione del model quando giá tutti i player sono presenti
        switch (pawnSelector) {
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
        playerList.add(new Player(color, username));
        pawnSelector++;
    }

}
