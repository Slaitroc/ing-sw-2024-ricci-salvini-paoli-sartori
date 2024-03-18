package it.polimi.ingsw.gc31.Controller;

import java.util.List;
import java.util.ArrayList;

import it.polimi.ingsw.gc31.Model.GameModel;
import it.polimi.ingsw.gc31.View.GameView;
import it.polimi.ingsw.gc31.Model.Exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.Model.Exceptions.PlayerNumberAlreadySetException;
import it.polimi.ingsw.gc31.Model.Exceptions.PlayerNumberNotReachedException;

//IDEA  nei controller voglio usare solo i nickname che possono anche tornare utili come identificativo esterno dei player del Model

public class GameController {

    private int ID;

    private GameModel gameModel;
    private GameView gameView;
    private List<String> playerList;
    private int maxNumPlayer; 

    public GameController() {
        playerList = new ArrayList<String>();
        maxNumPlayer = 0; 
    }

    public GameController(int mNP, String username) {
        this();
        playerList.add(username);
        maxNumPlayer = mNP;
    }

    public void addPlayer(String username) throws MaxPlayerNumberReachedException {
        if (maxNumPlayer == playerList.size())
            throw new MaxPlayerNumberReachedException();
        else
            playerList.add(username);
    }

    public void createGameModel() throws PlayerNumberNotReachedException {
        if (playerList.size() < maxNumPlayer)
            throw new PlayerNumberNotReachedException();
        else
            gameModel = new GameModel(playerList);
    }

    public void setNumPlayers(int n) throws PlayerNumberAlreadySetException{
        if (maxNumPlayer == 0)
        this.maxNumPlayer = n;
        else throw new PlayerNumberAlreadySetException();
    }
}
