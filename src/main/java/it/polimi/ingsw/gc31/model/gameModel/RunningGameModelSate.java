package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.GameListenerHandler;
import it.polimi.ingsw.gc31.client_server.listeners.PlayerScoreListener;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.DV;

import java.awt.*;
import java.util.Map;

public class RunningGameModelSate implements GameModelState {
    public RunningGameModelSate(GameModel model) {
        ServerLog.gControllerWrite("Game changed to RUNNING", model.getIdGame());

        model.getListeners().values().forEach(GameListenerHandler::removeStarterCardListener);
        model.getListeners().values().forEach(GameListenerHandler::removeChooseObjectiveListener);
        model.getListeners().values().forEach(listener -> listener.addPlayerScoreListener(new PlayerScoreListener(model.clients)));
//        for (String username: model.getListeners().keySet()) {
//            GameListenerHandler gameListener = model.getListeners().get(username);
//            model.getListeners().get(username).removeStarterCardListener();
//            model.getListeners().get(username).removeChooseObjectiveListener();
//
//
//            gameListener.addPlayerScoreListener(new PlayerScoreListener(model.clients));
//        }
//        model.notifyAllGameListeners();
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
    public void detectEndGame(GameModel model, Boolean bothEmptyDeck) throws IllegalStateOperationException {
        if ((model.getCurrPlayer().getScore() >= DV.GamePoints || bothEmptyDeck) && model.getCurrIndexPlayer() == model.getPlayers().size()-1) {
            model.setGameState(new LastTurnGameModelState(model));
        } else if (model.getCurrPlayer().getScore() >= DV.GamePoints || bothEmptyDeck){
            model.setGameState(new ShowDownGameModelState(model));
        }
    }

    @Override
    public void endGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void disconnectPlayer(GameModel model, String username) {
        model.executeDisconnectPlayer(username);
    }

    @Override
    public void reconnectPlayer(GameModel model, String username) {
        model.executeReconnectPlayer(username);
    }
}
