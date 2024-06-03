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

        createAllListeners(model, players, clients);
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
        model.secretObjectives.add(model.getBoard().getDeckObjective().draw());
        model.secretObjectives.add(model.getBoard().getDeckObjective().draw());

        for (String username : clients.keySet()) {
            model.getBoard().updateScore(username, 0);
        }

        // change the state of the gameModel
        model.setGameState(new SetupGameModelState());
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

    // FIXME non so quanto sia bello mettere questo metodo qua.
    // FIXME riguardare intermanet i listener
    // FIXME non bellissimo come l'ho fatto fino ad ora
    private void createAllListeners(GameModel model, Map<String, Player> players, Map<String, VirtualClient> clients) {
        List<PlayerHandListener> playerHandListenersList = new ArrayList<>();
        List<PlayerScoreListener> playerScoreListeners = new ArrayList<>();
        List<PlayAreaListener> playAreaListenerList = new ArrayList<>();
        List<GoldDeckListener> goldDeckListeners = new ArrayList<>();
        List<ResourceDeckListener> resourceDeckListeners = new ArrayList<>();
        List<ObjectiveDeckListener> objectiveDeckListeners = new ArrayList<>();
        List<PlayerTurnListener> playerTurnListeners = new ArrayList<>();

        for (String username : players.keySet()) {
            // create playerHandListener for all players
            playerHandListenersList.add(new PlayerHandListener(clients.get(username)));
            // create playerScoreListener for all players
            playerScoreListeners.add(new PlayerScoreListener(clients.get(username)));
            // create playAreaListener for all players
            playAreaListenerList.add(new PlayAreaListener(clients.get(username)));
            // create goldDeckListener for all players
            goldDeckListeners.add(new GoldDeckListener(clients.get(username)));
            // create resourceDeckListener for all players
            resourceDeckListeners.add(new ResourceDeckListener(clients.get(username)));
            // create objectiveDeckListener for all players
            objectiveDeckListeners.add(new ObjectiveDeckListener(clients.get(username)));
            // create playerTurnListener for all players
            playerTurnListeners.add(new PlayerTurnListener(clients.get(username)));
        }

        for (Player player : players.values()) {
            // add all playerHandListener to all player
            for (PlayerHandListener listener : playerHandListenersList) {
                player.addPlayerHandListener(listener);
            }
            // add all playAreaListener to all player
            for (PlayAreaListener listener : playAreaListenerList) {
                player.addPlayAreaListener(listener);
            }
            // add all playerTurnListner to all player
            for (PlayerTurnListener listener : playerTurnListeners) {
                player.addPlayerTurnListener(listener);
            }
            // add to the player its own playerStarterCardListener
            player.addPlayerStarterCardListener(new PlayerStarterCardListener(clients.get(player.getUsername())));

            // add to the player its own playerChooseObjectiveCardListener
            player.addPlayerChooseObjectiveCardListener(
                    new PlayerChooseObjectiveCardListener(clients.get(player.getUsername())));

            // add to the player its own playerObjectiveCardListener
            player.addPlayerObjectiveCardListener(
                    new PlayerObjectiveCardListener(clients.get(player.getUsername())));
        }

        // FIXME giusto?
        for (GoldDeckListener listener : goldDeckListeners) {
            model.getBoard().getDeckGold().addListener(listener);
        }
        for (ResourceDeckListener listener : resourceDeckListeners) {
            model.getBoard().getDeckResource().addListener(listener);
        }
        for (ObjectiveDeckListener listener : objectiveDeckListeners) {
            model.getBoard().getDeckObjective().addListener(listener);
        }
        for (PlayerScoreListener listener : playerScoreListeners) {
            model.getBoard().addListener(listener);
        }
    }
}
