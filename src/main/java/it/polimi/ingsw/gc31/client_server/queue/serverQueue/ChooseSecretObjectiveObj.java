package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class ChooseSecretObjectiveObj implements ServerQueueObject {
    private final Integer choice;
    private final String username;

    public ChooseSecretObjectiveObj(String username, int index) {
        this.username = username;
        this.choice = index;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.chooseSecretObjective(username, choice);
    }

    @Override
    public void execute(Controller controller) {
    }
}