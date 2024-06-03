package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SetupGameModelState implements GameModelState{
    public SetupGameModelState() {
        System.out.println("Game changed to SETUP");
    }
    @Override
    public Map<String, Player> initGame(GameModel model, LinkedHashMap<String, VirtualClient> clients) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void chooseSecretObjective(GameModel model, String username, Integer index) throws IllegalStateOperationException {
        model.getPlayers().get(username).chooseSecretObjective(index);
    }

    @Override
    public void playStarter(GameModel model, String username) throws IllegalStateOperationException, ObjectiveCardNotChosenException {
        model.getPlayers().get(username).playStarter();

        boolean allPlayersPlayedStarter = true;
        for (Player player: model.getPlayers().values()) {
            if (player.infoState().equals("start")) {
                allPlayersPlayedStarter = false;
            }
        }

        if (allPlayersPlayedStarter) {
            model.setGameState(new RunningGameModelSate());
            model.setNextPlayingPlayer();
        }
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
        model.getPlayers().get(username).changeStarterSide();
    }

    @Override
    public void detectEndGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void endGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
}
