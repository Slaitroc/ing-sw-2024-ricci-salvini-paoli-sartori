package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.util.HashMap;
import java.util.Map;

public class GameControllerLobbyState implements GameControllerState{
    @Override
    public void addPlayer(GameController controller, String username) {
        Map<String, Player> players = controller.getPlayers();
        players.put(username, new Player(username));
    }

    @Override
    public Map<String, PlayerController> initGame(GameController controller, GameModel model) {
        model.addPlayers(controller.getPlayers());

        Map<String, PlayerController> ret = new HashMap<>();

        for (String user: controller.getPlayers().keySet()) {
            // devo prendere i player del model perchè sono diversi dai player di players
            ret.put(user, new PlayerController(model.getPlayer(user)));
        }

        controller.changeState(new GameControllerStartingState());
        return ret;
    }

    @Override
    public void dealCards(GameModel model) {

    }
}
