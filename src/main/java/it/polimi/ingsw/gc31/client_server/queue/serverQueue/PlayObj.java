package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;

public class PlayObj extends ServerQueueObject {

    private Player player;
    @SuppressWarnings("unused")
    private GameModel model;
    private int x, y;

    public PlayObj(Player player, GameModel model, int x, int y) {
        this.player = player;
        this.model = model;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(GameController gameController) {
        player.play(new Point(x, y));
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
}
