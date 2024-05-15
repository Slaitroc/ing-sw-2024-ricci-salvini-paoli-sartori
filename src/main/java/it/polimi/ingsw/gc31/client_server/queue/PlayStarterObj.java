package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

public class PlayStarterObj implements QueueObject {

    Player player;
    GameModel model;

    public PlayStarterObj(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    @Override
    public void execute() {
        player.playStarter();
        model.checkStartGame();
    }
}