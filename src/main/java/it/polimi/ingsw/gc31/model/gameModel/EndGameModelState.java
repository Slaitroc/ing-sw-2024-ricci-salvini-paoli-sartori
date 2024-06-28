package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.GameListenerHandler;
import it.polimi.ingsw.gc31.client_server.listeners.ListenerType;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameIsOverObj;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * The EndGameModelState class represents the state of the game when it has ended.
 * It implements the GameModelState interface.
 */
public class EndGameModelState implements GameModelState {

    public EndGameModelState(GameModel model) {
        ServerLog.gControllerWrite("Game changed to END GAME", model.getIdGame());

        for (String username : model.getListeners().keySet()) {
            model.getListeners().get(username).removeListener(ListenerType.GOLD_DECK);
            model.getListeners().get(username).removeListener(ListenerType.RESOURCE_DECK);
        }
    }

    @Override
    public Map<String, Player> initGame(GameModel model, Map<String, VirtualClient> clients, Object lock)
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
    public void detectEndGame(GameModel model, Boolean bothEmptyDeck) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void endGame(GameModel model, String lastPlayerConnected) {
        if (lastPlayerConnected == null) {
            for (Player player : model.getPlayers().values()) {
                player.addPoints(player.calculateObjectiveCard());
                player.addPoints(player.calculateObjectiveCard(model.commonObjectives.get(0)));
                player.addPoints(player.calculateObjectiveCard(model.commonObjectives.get(1)));
            }
            String usernameWinner = null;
            int maxPoint = 0;
            int maxObjectivePoints = 0;
            for (Player player : model.getPlayers().values()) {
                if (player.getScore() > maxPoint) {
                    maxPoint = player.getScore();
                    usernameWinner = player.getUsername();
                    maxObjectivePoints = player.calculateObjectiveCard() + player.calculateObjectiveCard(model.commonObjectives.get(0)) + player.calculateObjectiveCard(model.commonObjectives.get(1));
                } else if (player.getScore() == maxPoint) {
                    if (player.calculateObjectiveCard() + player.calculateObjectiveCard(model.commonObjectives.get(0)) + player.calculateObjectiveCard(model.commonObjectives.get(1)) > maxObjectivePoints) {
                        maxPoint = player.getScore();
                        usernameWinner = player.getUsername();
                        maxObjectivePoints = player.calculateObjectiveCard() + player.calculateObjectiveCard(model.commonObjectives.get(0)) + player.calculateObjectiveCard(model.commonObjectives.get(1));
                    }
                }
            }

            model.getListeners().values().forEach(GameListenerHandler::setEnabled);

            synchronized (model.clientListLock) {
                for (String username : model.clients.keySet()) {
                    ServerLog.controllerWrite("sto mandando a " + username);
                    ClientQueueObject clientQueueObject1 = new GameIsOverObj(usernameWinner,
                            model.getBoard().getPlayersScore());
                    new Thread(
                            () -> {
                                try {
                                    model.clients.get(username).sendCommand(clientQueueObject1);
                                } catch (RemoteException ignored) {
                                }
                            }).start();
                }
            }
        } else {
            synchronized (model.clientListLock) {
                new Thread(
                        () -> {
                            try {
                                model.clients.get(lastPlayerConnected).sendCommand(
                                        new GameIsOverObj(lastPlayerConnected, model.getBoard().getPlayersScore()));
                            } catch (RemoteException ignored) {
                            }
                        }).start();
            }
        }
    }

    @Override
    public void disconnectPlayer(GameModel model, String username) {

    }

    @Override
    public void reconnectPlayer(GameModel model, String username) {

    }
}
