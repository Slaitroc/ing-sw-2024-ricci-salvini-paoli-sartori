package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class JoinGameObj extends ServerQueueObject {
    private final String username;
    private final Integer idGame;
    private final VirtualClient client;

    public JoinGameObj(String username, int idGame) {
        this.username = username;
        this.idGame = idGame;
        this.client = null;
    }

    public JoinGameObj(VirtualClient client, String username) {
        this.username = username;
        this.client = client;
        this.idGame = -1;
    }

    @Override
    public void execute(GameController gameController) {
        try {
            gameController.joinGame(username, client);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void execute(Controller controller) {
        try {
            controller.joinGame(username, idGame);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void execute(RmiServer server) {
    }

}
