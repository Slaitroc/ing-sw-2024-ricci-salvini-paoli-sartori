package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import java.rmi.RemoteException;

public class DrawResObj extends ServerQueueObject {

    private final String username;
    private final int index;

    public DrawResObj(String username, int index) {
        this.username = username;
        this.index = index;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.drawResource(username, index);
    }

    @Override
    public void execute(Controller controller) {
    }

    @Override
    public void execute(RmiServer server) {
    }

}
