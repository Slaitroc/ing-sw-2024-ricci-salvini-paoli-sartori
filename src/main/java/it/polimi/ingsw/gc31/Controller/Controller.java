package it.polimi.ingsw.gc31.Controller;

import java.util.Set;

import it.polimi.ingsw.gc31.Model.Exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.Model.Player.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

//NOTE creazione GameController x la creazione del match
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller 

public class Controller {
    private List<GameController> gamesList;
    private Set<String> globalUsernameSet;

    // NOTE potrebbe essere utile anche usare una mappa dove ad ogni controller
    // collego un ID del game per trovarlo piú agevolmente

    public Controller() {
        this.gamesList = new ArrayList<GameController>();
        this.globalUsernameSet = new HashSet<String>();

    }


    /**
     * @param username player's nickname
     * @return the gamePosition in gameSet
     */
    public int createGame(String username) {
        gamesList.add(new GameController(4, username));
        return gamesList.size()-1;
    }

    /**
     * @apiNote add the player's nickname to the global players set
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

    public GameController getGameController(int gameID){
        return gamesList.get(gameID);
    }

}
