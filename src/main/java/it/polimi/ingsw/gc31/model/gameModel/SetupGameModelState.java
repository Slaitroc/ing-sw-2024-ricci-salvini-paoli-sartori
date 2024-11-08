package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.*;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.LastPlayerRemainedException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.Map;

/**
 * Identifies the initial setup part of the match. Implements the {@link GameModelState} interface. Defines
 * which actions are executable during the setup of the match.
 */
public class SetupGameModelState implements GameModelState {
    public SetupGameModelState(GameModel model) {
        ServerLog.gControllerWrite("Game changed to SETUP", model.getIdGame());

        synchronized (model.clientListLock) {
            for (String username : model.getListeners().keySet()) {
                GameListenerHandler gameListener = model.getListeners().get(username);
                gameListener.addListener(ListenerType.CHOOSE_OBJECTIVE, new PlayerChooseObjectiveCardListener(model.clients));
                gameListener.addListener(ListenerType.COMMON_OBJECTIVE, new CommonObjectiveCardListener(model.clients));
                gameListener.addListener(ListenerType.GOLD_DECK, new GoldDeckListener(model.clients));
                gameListener.addListener(ListenerType.RESOURCE_DECK, new ResourceDeckListener(model.clients));
                gameListener.addListener(ListenerType.STARTER_CARD, new PlayerStarterCardListener(model.clients));
                gameListener.addListener(ListenerType.HAND, new PlayerHandListener(model.clients));
                gameListener.addListener(ListenerType.OBJECTIVE_CARD, new PlayerObjectiveCardListener(model.clients));
                gameListener.addListener(ListenerType.PLAYAREA, new PlayAreaListener(model.clients));
                gameListener.addListener(ListenerType.TURN,  new PlayerTurnListener(model.clients));
                gameListener.addListener(ListenerType.PLAYER_SCORE,  new PlayerTurnListener(model.clients));
            }
        }
    }

    @Override
    public Map<String, Player> initGame(GameModel model, Map<String, VirtualClient> clients, Object lock) throws IllegalStateOperationException {
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
        for (Player player : model.getPlayers().values()) {
            if (player.infoState().equals("start")) {
                allPlayersPlayedStarter = false;
            }
        }

        if (allPlayersPlayedStarter) {
            model.endTurn();
//            model.setNextPlayingPlayer();
            model.setGameState(new RunningGameModelSate(model));
            model.notifyAllGameListeners();
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
    public void setSelectCard(GameModel model, String username, int index) throws IllegalStateOperationException, WrongIndexSelectedCard {
        model.getPlayers().get(username).setSelectedCard(index);
    }

    @Override
    public void changeSide(GameModel model, String username) throws IllegalStateOperationException {
        model.getPlayers().get(username).changeSide();
    }

    @Override
    public void changeStarterSide(GameModel model, String username) throws IllegalStateOperationException {
        model.getPlayers().get(username).changeStarterSide();
    }

    @Override
    public void detectEndGame(GameModel model, Boolean bothEmptyDeck) {

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
