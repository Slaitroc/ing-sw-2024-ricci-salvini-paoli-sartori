package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class CreateGameObj extends ServerQueueObject {

    private final String username;
    private final int maxNumPlayer;

    public CreateGameObj(String username, int maxNumberPlayer) {
        this.username = username;
        this.maxNumPlayer = maxNumberPlayer;
    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(Controller controller) {
        try {
            controller.createGame(username, maxNumPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(RmiServer server) {
    }

}
