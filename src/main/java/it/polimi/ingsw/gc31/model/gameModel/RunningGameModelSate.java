package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.Map;

public class RunningGameModelSate implements GameModelState {
    public RunningGameModelSate() {
        System.out.println("Game changed to RUNNING");
    }
    @Override
    public Map<String, Player> initGame(GameModel model, Map<String, VirtualClient> clients) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void chooseSecretObjective(GameModel model, String username, Integer index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(GameModel model, String username) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void play(GameModel model, String username, Point point) throws IllegalStateOperationException {
        model.getPlayers().get(username).play(point);
    }

    @Override
    public void drawGold(GameModel model, String username, int index) throws IllegalStateOperationException {
        if (model.getPlayers().get(username).drawGold(index)) {
            model.setNextPlayingPlayer();
        }
    }
}
