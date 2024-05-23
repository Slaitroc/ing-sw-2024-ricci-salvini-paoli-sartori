package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import org.jline.terminal.impl.LineDisciplineTerminal;

public class DrawGoldObj extends ServerQueueObject {

    private final String username;
    private final Integer index;

    public DrawGoldObj(String username, Integer index) {
        this.username = username;
        this.index = index;
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

    public void execute(GameController gameController) {
        gameController.drawGold(username, index);
    }

}
