package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.player.Player;

public class DrawGoldObj implements ServerQueueObject {

    private final String username;

    public DrawGoldObj(String username) {
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.drawGold(username);
    }

    @Override
    public void execute(Controller controller) {

    }
}
