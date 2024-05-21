package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class ReadyStatusObj extends ServerQueueObject {
    boolean ready;
    String username;

    public ReadyStatusObj(boolean ready, String username) {
        this.ready = ready;
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {
        try {
            gameController.setReadyStatus(ready, username);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalStateOperationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void execute(Controller controller) {
    }

    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
