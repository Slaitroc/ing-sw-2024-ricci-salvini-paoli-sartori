package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class ReconnectObj extends ServerQueueObject {

    private boolean esito;
    private String username;
    private int token;

    public ReconnectObj(boolean esito, String username, int token) {
        this.esito = esito;
        this.token = token;
        this.username = username;

    }

    @Override
    public void execute(Controller controller) {

        controller.rejoin(username, token, esito);
    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(RmiServer server) {
    }

}
