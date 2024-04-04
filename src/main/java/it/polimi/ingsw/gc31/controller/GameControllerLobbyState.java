package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameControllerLobbyState implements GameControllerState{
    @Override
    public void addPlayer(GameModel model, String username) {
        model.addPlayer(username);
    }

    @Override
    public Map<String, PlayerController> initGame(GameController controller, GameModel model) {
        return null;
    }

    @Override
    public void dealCards(GameModel model) {
    }
}
