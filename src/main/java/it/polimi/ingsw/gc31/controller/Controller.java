package it.polimi.ingsw.gc31.controller;

import java.util.Set;

import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.DeepCopy;
import javafx.scene.layout.CornerRadii;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

//NOTE creazione GameController x la creazione del match
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller 

public class Controller implements Cloneable, DeepCopy<Controller> {
    private final List<GameController> gamesList; // NOTE meglio Set o List?

    private final Set<String> globalUsernameSet;

    public Set<String> getGlobalUsernameSet() {
        return globalUsernameSet;
    }

    public Controller() {
        this.gamesList = new ArrayList<GameController>();
        this.globalUsernameSet = new HashSet<String>();

    }

    /**
     * @param username player's nickname
     * @return the gamePosition of the GameController in gameSet
     */
    public int createGameController(String username, int numPlayers) {
        gamesList.add(new GameController(numPlayers, username));
        return gamesList.size() - 1;
    }

    /**
     * @apiNote add the player's nickname to the globalPlayersSet
     * @param username
     * @throws PlayerNicknameAlreadyExistsException if the nickname is already in
     *                                              use
     */
    public void addPlayerUsername(String username) throws PlayerNicknameAlreadyExistsException {
        if (!doesNameAlreadyExist(username))
            globalUsernameSet.add(username);
        else
            throw new PlayerNicknameAlreadyExistsException();
    }

    /**
     * 
     * @param username : player's nickname
     * @return true if the player's nickname already exists, false otherwise
     * @autor Slaitroc
     */
    private boolean doesNameAlreadyExist(String username) {
        if (globalUsernameSet.contains(username))
            return true;
        else
            return false;

    }

    public GameController getGameController(int gameID) {
        return gamesList.get(gameID);
    }

    @Override
    public Controller deepCopy() {
        Controller cloned = new Controller();
        for (GameController gc : gamesList) {
            cloned.gamesList.add(gc.deepCopy());
        }
        for (String nickname : globalUsernameSet) {
            cloned.globalUsernameSet.add(new String(nickname));
        }
        return cloned;
    }

}
