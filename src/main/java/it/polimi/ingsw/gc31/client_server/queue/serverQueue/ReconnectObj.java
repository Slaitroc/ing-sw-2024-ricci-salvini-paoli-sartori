package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class ReconnectObj extends ServerQueueObject {

    private boolean esito;
    private int token;
    private int tempToken;

    public ReconnectObj(boolean esito, String username,int tempToken, int token) {
        this.esito = esito;
        this.token = token;
        this.tempToken = tempToken;

    }

    @Override
    public void execute(Controller controller) {

        controller.rejoin(tempToken, token, esito);
    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(RmiServer server) {
    }

}
