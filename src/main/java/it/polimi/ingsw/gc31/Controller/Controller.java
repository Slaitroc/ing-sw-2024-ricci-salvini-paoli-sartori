package it.polimi.ingsw.gc31.Controller;

import java.util.Set;

import it.polimi.ingsw.gc31.Model.Exceptions.PlayerNicknameAlreadyExistsException;

import java.util.HashSet;

/* DOMANDE */
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller 

public class Controller {
    private Set<GameController> gamesSet;
    private Set<String> globalPlayersSet;

    public Controller() {
        this.gamesSet = new HashSet<GameController>();
        this.globalPlayersSet = new HashSet<String>();
        
    }
    
    private void createGame(String username){
        gamesSet.add(new GameController());
        //TODO sei rimasto qui
        
    }


    /**
     * @apiNote add the player's nickname to the global players set
     * @param username
     * @throws PlayerNicknameAlreadyExistsException if the nickname is already in use 
     */
    public void addPlayerUsername(String username) throws PlayerNicknameAlreadyExistsException {
        if (!doesNameAlreadyExist(username))
            globalPlayersSet.add(username);
        else throw new PlayerNicknameAlreadyExistsException();
    }

    /**
     * 
     * @param username : player's nickname
     * @return true if the player's nickname already exists, false otherwise
     * @autor Slaitroc
     */
    private boolean doesNameAlreadyExist(String username) {
        if (globalPlayersSet.contains(username))
            return true;
        else
            return false;

    }

}
