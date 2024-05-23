package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;

public class DrawGoldObj extends ServerQueueObject {

    private final String username;
    private final int select;

    public DrawGoldObj(String username, int select) {
        this.username = username;
        this.select = select;
    }

    @Override
    public void execute(Controller controller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    //FIXME Modificare il metodo in modo che drawGold accetti anche il valore "select"
    // unico metodo per tutti e 3 i casi, modificare metodo di gameController
    public void execute(GameController gameController) {
        gameController.drawGold(username);
    }

}
