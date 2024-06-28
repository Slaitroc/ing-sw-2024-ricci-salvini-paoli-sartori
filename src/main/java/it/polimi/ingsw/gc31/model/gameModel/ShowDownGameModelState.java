package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.Map;

/**
 * Indicates the final moments of a game. Implements the {@link GameModelState} interface. Defines which
 * actions are executable. The methods are implemented by the {@link GameModel} class.
 */
public class ShowDownGameModelState implements GameModelState {
    public ShowDownGameModelState(GameModel model) {
        ServerLog.gControllerWrite("Game changed to SHOWDOWN", model.getIdGame());
    }

    @Override
    public Map<String, Player> initGame(GameModel model, Map<String, VirtualClient> clients, Object lock) throws IllegalStateOperationException {
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
    public void play(GameModel model, String username, Point point) throws IllegalStateOperationException, IllegalPlaceCardException {
        model.getPlayers().get(username).play(point);
    }

    @Override
    public void drawGold(GameModel model, String username, int index) throws IllegalStateOperationException, EmptyDeckException {
        if (model.getPlayers().get(username).drawGold(index)) {
            model.endTurn();
        }
    }

    @Override
    public void drawResource(GameModel model, String username, int index) throws IllegalStateOperationException, EmptyDeckException {
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
    public void detectEndGame(GameModel model, Boolean bothEmptyDeck) {
        if (model.getCurrIndexPlayer() == model.getPlayers().size() - 1) {
            model.setGameState(new LastTurnGameModelState(model));
        }
    }

    @Override
    public void endGame(GameModel model, String lastPlayerConnected) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void disconnectPlayer(GameModel model, String username) throws LastPlayerRemainedException {
        model.executeDisconnectPlayer(username);
    }

    @Override
    public void reconnectPlayer(GameModel model, String username) {
        model.executeReconnectPlayer(username);
    }
}
