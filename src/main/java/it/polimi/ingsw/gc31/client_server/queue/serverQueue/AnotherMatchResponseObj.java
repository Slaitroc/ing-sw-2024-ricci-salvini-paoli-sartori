package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class AnotherMatchResponseObj extends ServerQueueObject{
    String username;
    Boolean wantsToRematch;
    public AnotherMatchResponseObj(String username, Boolean wantsToRematch){
        this.username = username;
        this.wantsToRematch = wantsToRematch;
    }
    @Override
    public void execute(GameController gameController) {
        gameController.anotherMatch(username, wantsToRematch);
    }

    @Override
    public void execute(Controller controller) {
    }

    @Override
    public void execute(RmiServer server) {
    }
}
