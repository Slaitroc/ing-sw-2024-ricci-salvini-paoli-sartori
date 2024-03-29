package it.polimi.ingsw.gc31.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.exceptions.GameModelAlreadyCreatedException;
import it.polimi.ingsw.gc31.model.exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNumberAlreadySetException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNumberNotReachedException;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.DeepCopy;
import it.polimi.ingsw.gc31.view.GameView;

//IDEA  nei controller voglio usare solo i nickname che possono anche tornare utili come identificativo esterno dei player del Model

public class GameController {
    private GameModel model;
    private GameControllerState gameState;
    private final Map<String, Player> players;
    public GameController() {
        this.model = new GameModel();
        this.players = new HashMap<>();
        gameState = new GameControllerLobbyState();
    }

    public Map<String, PlayerController> initGame() {
        return gameState.initGame(this, model);
    }
    public void addPlayer(String username) {
        gameState.addPlayer(this,username);
    }
    public void dealCard() {
        gameState.dealCards(model);
    }
    Map<String, Player> getPlayers() {
        return players;
    }

    void changeState(GameControllerState state) {
        this.gameState = state;
    }
}
