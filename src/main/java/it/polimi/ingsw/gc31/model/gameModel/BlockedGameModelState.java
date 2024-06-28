package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.GameListenerHandler;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.Map;

/**
 * Represents the state of a game when it is blocked.
 */
public class BlockedGameModelState implements GameModelState{
    private final GameModelState savedGameState;
    public BlockedGameModelState(GameModel model, GameModelState savedGameState) {
        ServerLog.gControllerWrite("Game changed to BLOCKED", model.getIdGame());

        this.savedGameState = savedGameState;
        model.getListeners().values().forEach(GameListenerHandler::setDisabled);
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
    public void playStarter(GameModel model, String username) throws IllegalStateOperationException, ObjectiveCardNotChosenException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void play(GameModel model, String username, Point point) throws IllegalStateOperationException, IllegalPlaceCardException {
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
    public void setSelectCard(GameModel model, String username, int index) throws IllegalStateOperationException, WrongIndexSelectedCard {
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
    public void detectEndGame(GameModel model, Boolean bothEmptyDeck) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void endGame(GameModel model, String lastPlayerConnected) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void disconnectPlayer(GameModel model, String username) throws LastPlayerRemainedException {
    }

    @Override
    public void reconnectPlayer(GameModel model, String username) {
        model.setGameState(savedGameState);
        model.executeReconnectPlayer(username);
    }
}
