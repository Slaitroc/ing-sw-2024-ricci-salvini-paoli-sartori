package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;

public class ChooseSecretObjectiveObj implements ServerQueueObject {

    private final GameModel model;
    int choice;
    String username;

    public ChooseSecretObjectiveObj(String username, GameModel model, int index) {
        this.model = model;
        this.username = username;
        this.choice = index;
    }

    @Override
    public void execute(GameController gameController) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void execute(Controller controller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}