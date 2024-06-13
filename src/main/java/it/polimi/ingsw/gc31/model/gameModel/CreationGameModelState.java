package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.*;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CreationGameModelState implements GameModelState {
    @Override
    public Map<String, Player> initGame(GameModel model, LinkedHashMap<String, VirtualClient> clients)
            throws IllegalStateOperationException {
        Map<String, Player> players = createPlayers(model, clients.keySet());

        for (Player player : players.values()) {
            player.setStarterCard();
            player.drawGold(0);
            player.drawResource(0);
            player.drawResource(0);

            player.drawChooseObjectiveCards();
        }

        // The GoldCard1 and GoldCard1 are drawn on the board
        model.getBoard().getDeckGold().refill();
        // THe ResourceCar1 and ResourceCard2 are drawn on the board
        model.getBoard().getDeckResource().refill();
        // The Secrete Objective 1 and the Secrete Objective 2 are drawn
        model.commonObjectives.add(model.getBoard().getDeckObjective().draw());
        model.commonObjectives.add(model.getBoard().getDeckObjective().draw());

        for (String username : clients.keySet()) {
            model.getBoard().updateScore(username, 0);
        }

        for (String username: clients.keySet()) {
            model.getListeners().put(username, new GameListenerHandler(username));
        }

        for (String username: clients.keySet()) {
            model.playerConnection.put(username, true);
        }

        // change the state of the gameModel
        model.setGameState(new SetupGameModelState(model));
        return players;
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
    public void detectEndGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void endGame(GameModel model) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
    private Map<String, Player> createPlayers(GameModel model, Set<String> setUsername) {
        Map<String, Player> players = new HashMap<>();
        for (String username : setUsername) {
            Player player = new Player(model.pawnAssignment(), username, model.getBoard());
            players.put(username, player);
        }
        return players;
    }
}
