package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameIsOverObj;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EndGameModelState implements GameModelState {
    public EndGameModelState() {
        System.out.println("Game changed to END GAME");
    }
    @Override
    public Map<String, Player> initGame(GameModel model, LinkedHashMap<String, VirtualClient> clients) throws IllegalStateOperationException {
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
        throw new IllegalStateOperationException();
    }

    @Override
    public void drawGold(GameModel model, String username, int index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void drawResource(GameModel model, String username, int index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void setSelectCard(GameModel model, String username, int index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void changeSide(GameModel model, String username) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void changeStarterSide(GameModel model, String username) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void detectEndGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void endGame(GameModel model){
        for (Player player: model.getPlayers().values()) {
            player.calculateObjectiveCard();
            player.calculateObjectiveCard(model.commonObjectives.get(0));
            player.calculateObjectiveCard(model.commonObjectives.get(1));
        }

        for (String username: model.clients.keySet()) {
            try {
                model.clients.get(username).sendCommand(new GameIsOverObj());
            } catch (RemoteException e) {
                System.out.println("Error sending game is over");
            }
        }
    }
}
