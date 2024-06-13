package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class QuitGameObj extends ServerQueueObject {

    private String username;
    private int id;

    public QuitGameObj(String username) {
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {
        // metodo quit del game controller
        try {
            gameController.quitGame(username);
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
