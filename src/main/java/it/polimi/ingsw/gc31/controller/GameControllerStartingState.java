package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameModel;

import java.util.Map;

public class GameControllerStartingState implements GameControllerState{
    @Override
    public void addPlayer(GameModel model, String username) {

    }

    @Override
    public Map<String, PlayerController> initGame(GameController controller, GameModel model) {
        return null;
    }

    @Override
    public void dealCards(GameModel model) {
        model.dealCards();
    }
}
