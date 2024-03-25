package it.polimi.ingsw.gc31.controller;

import java.util.List;
import java.util.ArrayList;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.exceptions.GameModelAlreadyCreatedException;
import it.polimi.ingsw.gc31.model.exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNumberAlreadySetException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNumberNotReachedException;
import it.polimi.ingsw.gc31.utility.DeepCopy;
import it.polimi.ingsw.gc31.view.GameView;

//IDEA  nei controller voglio usare solo i nickname che possono anche tornare utili come identificativo esterno dei player del Model

public class GameController implements Cloneable, DeepCopy<GameController> {

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

    public void addPlayer(String username)
            throws MaxPlayerNumberReachedException, PlayerNicknameAlreadyExistsException {
        if (maxNumPlayer == playerList.size())
            throw new MaxPlayerNumberReachedException();
        if (doesNameAlreadyExist(username))
            throw new PlayerNicknameAlreadyExistsException();
        else
            playerList.add(username);
    }

    public void createGameModel() throws PlayerNumberNotReachedException, GameModelAlreadyCreatedException {
        if (gameModel != null)
            throw new GameModelAlreadyCreatedException();
        if (playerList.size() < maxNumPlayer)
            throw new PlayerNumberNotReachedException();
        else
            gameModel = new GameModel(playerList);
    }

    public void setNumPlayers(int n) throws PlayerNumberAlreadySetException {
        if (maxNumPlayer == 0)
            this.maxNumPlayer = n;
        else
            throw new PlayerNumberAlreadySetException();
    }

    // NOTE utility

    private boolean doesNameAlreadyExist(String username) {
        if (playerList.contains(username))
            return true;
        else
            return false;

    }
    // NOTE getters

    public List<String> getPlayerList() {
        ArrayList<String> listCopy = new ArrayList<String>();
        for (String string : playerList) {
            listCopy.add(new String(string));
        }
        return listCopy;
    }

    public GameModel getGameModel() {
        return gameModel.deepCopy();
    }

    @Override
    public GameController deepCopy() {
        GameController cloned = new GameController();
        try {
            cloned.setNumPlayers(this.maxNumPlayer);
        } catch (PlayerNumberAlreadySetException e) {
            e.printStackTrace();
        }
        cloned.gameModel = this.gameModel.deepCopy();
        cloned.gameView = new GameView(); // TODO da implementare clone()
        cloned.playerList = new ArrayList<String>();
        for (String string : this.playerList) {
            cloned.playerList.add(new String(string)); // TODO un metodo generico per le liste ???
        }
        return cloned;
    }
}
