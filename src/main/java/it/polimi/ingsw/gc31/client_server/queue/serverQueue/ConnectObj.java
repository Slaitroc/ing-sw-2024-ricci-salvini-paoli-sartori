package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class ConnectObj extends ServerQueueObject {

    String username;

    public ConnectObj(String username) {
        this.username = username;

    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(Controller controller) {
        try {
            controller.connect(controller.getNewConnection(), username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(RmiServer server) {
        try {

            server.connect(server.getVirtualClient(), username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
