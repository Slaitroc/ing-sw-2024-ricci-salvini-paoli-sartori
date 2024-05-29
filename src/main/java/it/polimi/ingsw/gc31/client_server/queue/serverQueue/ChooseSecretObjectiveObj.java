package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class ChooseSecretObjectiveObj extends ServerQueueObject {

    private final int choice;
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
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}