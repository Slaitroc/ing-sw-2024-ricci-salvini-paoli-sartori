package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;

public class ReadyStatusObj extends ServerQueueObject {
    boolean ready;
    String username;

    public ReadyStatusObj(boolean ready, String username) {
        this.ready = ready;
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.setReadyStatus(ready, username);
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
