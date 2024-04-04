package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameModel;

import java.util.Map;

public interface GameControllerState {
    public void addPlayer(GameModel model, String username);
    public Map<String, PlayerController> initGame(GameController controller, GameModel model);
    public void dealCards(GameModel model);

}
