package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

import java.rmi.RemoteException;

public class ReJoinObj extends ServerQueueObject{
    private final String username;
    private final VirtualClient client;

    public ReJoinObj(VirtualClient client, String username) {
        this.username = username;
        this.client = client;
    }


    @Override
    public void execute(GameController gameController) {
        try {
            gameController.reJoinGame(client, username);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void execute(Controller controller) {
    }

    @Override
    public void execute(RmiServer server) {

    }
}
