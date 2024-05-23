package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import java.rmi.RemoteException;

public class DrawResObj extends ServerQueueObject {

    private final String username;
    private final int select;

    public DrawResObj(String username, int select) {
        this.username = username;
        this.select = select;
    }

    @Override
    public void execute(GameController gameController) {
        try {
            gameController.drawResource(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Controller controller) {
    }

    @Override
    public void execute(RmiServer server) {
    }

}
