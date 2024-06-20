package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LastTurnGameModelState implements GameModelState {
    public LastTurnGameModelState() {
        System.out.println("Game changed to LAST TURN");
    }

    @Override
    public Map<String, Player> initGame(GameModel model, LinkedHashMap<String, VirtualClient> clients)
            throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void chooseSecretObjective(GameModel model, String username, Integer index)
            throws IllegalStateOperationException {
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
            model.endTurn();
        }
    }

    @Override
    public void drawResource(GameModel model, String username, int index) throws IllegalStateOperationException {
        if (model.getPlayers().get(username).drawResource(index)) {
            model.endTurn();
        }
    }

    @Override
    public void setSelectCard(GameModel model, String username, int index) throws IllegalStateOperationException, WrongIndexSelectedCard {
        model.getPlayers().get(username).setSelectedCard(index);
    }

    @Override
    public void changeSide(GameModel model, String username) throws IllegalStateOperationException {
        model.getPlayers().get(username).changeSide();
    }

    @Override
    public void changeStarterSide(GameModel model, String username) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void detectEndGame(GameModel model) throws IllegalStateOperationException {
        if (model.getCurrIndexPlayer() == model.getPlayers().size() - 1) {
            model.setGameState(new EndGameModelState(model));
            model.endGame();
        }
    }

    @Override
    public void endGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void disconnectPlayer(GameModel model, String username) throws IllegalStateOperationException {
        model.disconnectPlayer(username);
    }
}
