package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

import java.util.Map;

public class CreateReMatch extends ServerQueueObject{
    private final Map<String, VirtualClient> players;

    public CreateReMatch(Map<String, VirtualClient> players) {
        this.players = players;
    }

    @Override
    public void execute(GameController gameController) {

    }

    @Override
    public void execute(Controller controller) {
//        controller.createReMatch(players);
    }

    @Override
    public void execute(RmiServer server) {

    }
}
