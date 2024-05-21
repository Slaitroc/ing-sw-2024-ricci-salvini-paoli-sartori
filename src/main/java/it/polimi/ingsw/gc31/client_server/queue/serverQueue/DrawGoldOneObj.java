package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class DrawGoldOneObj extends ServerQueueObject {

    private String username;

    public DrawGoldOneObj(String username) {
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {

    }

    @Override
    public void execute(Controller controller) {
    }

    @Override
    public void execute(RmiServer server) {
    }

}
