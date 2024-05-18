package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;

public class PlayObj implements ServerQueueObject {

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
    public void execute() {
        player.play(new Point(x, y));
    }
}
