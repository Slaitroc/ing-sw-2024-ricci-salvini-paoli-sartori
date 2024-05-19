package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;
import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.PlayerHandListener;
import it.polimi.ingsw.gc31.client_server.listeners.GoldDeckListener;
import it.polimi.ingsw.gc31.client_server.listeners.PlayAreaListener;
import it.polimi.ingsw.gc31.client_server.listeners.PlayerScoreListener;
import it.polimi.ingsw.gc31.client_server.listeners.PlayerStarterCardListener;
import it.polimi.ingsw.gc31.client_server.listeners.PlayerObjectiveCardListener;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowReadyStatusObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.StartGameObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ChooseSecretObjectiveObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawGoldObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawGoldOneObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawGoldTwoObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawResObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawResOneObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawResTwoObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.FlipCardObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.FlipStarterCardObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.PlayObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.PlayStarterObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.SelectCardObj;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;

/**
 * This class is the controller of one single game.
 * It manages the gameModel and the game states.
 */
public class GameController extends UnicastRemoteObject implements IGameController {
    private final GameModel model;
    private Map<String, Player> playerList;
    private final Map<String, VirtualClient> clientList;
    @SuppressWarnings("unused")
    private final Gson gsonCard/* , gsonObjective */;
    private final int maxNumberPlayers;
    private final int idGame;
    private final LinkedBlockingQueue<ServerQueueObject> callsList;
    private final Map<String, Boolean> readyStatus;

    /**
     * Constructor for the GameController class.
     * It initializes the game model, players, clientList, and game states.
     *
     * @param username         the username of the player.
     * @param client           the client of the player.
     * @param maxNumberPlayers the maximum number of players.
     * @param idGame           the id of the game.
     */
    public GameController(String username, VirtualClient client, int maxNumberPlayers, int idGame)
            throws RemoteException {
        this.model = new GameModel();
        this.callsList = new LinkedBlockingQueue<>();
        this.maxNumberPlayers = maxNumberPlayers;
        this.idGame = idGame;
        this.playerList = new HashMap<>();
        this.clientList = new HashMap<>();
        this.clientList.put(username, client);
        this.readyStatus = new HashMap<>();
        this.readyStatus.put(username, false);
        /*
         * gsonObjective = new GsonBuilder()
         * .registerTypeAdapter(ObjectiveCard.class, new ObjectiveCardAdapter())
         * .create();
         */
        gsonCard = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();

        new Thread(this::executor).start();
    }

    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
        while (true) {
            ServerQueueObject action;
            synchronized (callsList) {
                while (callsList.isEmpty()) {
                    try {
                        callsList.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                action = callsList.poll();
            }
            if (action != null) {
                action.execute(this);
            }
        }
    }

    /**
     * Allows a player to join the game.
     *
     * @param username the username of the player.
     * @param client   the client of the player.
     */
    public void joinGame(String username, VirtualClient client) throws RemoteException {
        clientList.put(username, client);
        readyStatus.put(username, false);
        if (maxNumberPlayers == this.clientList.size()) {
            gameControllerWrite("The number of players for the game " + maxNumberPlayers + " has been reached");
        }
    }

    @Override
    public void setReadyStatus(boolean ready, String username) throws RemoteException, IllegalStateOperationException {
        readyStatus.replace(username, ready);
        clientList.get(username).sendCommand(new ShowReadyStatusObj(ready));
        checkReady();

    }

    @Override
    public void checkReady() throws RemoteException, IllegalStateOperationException {
        int counter = 0;
        for (Boolean status : readyStatus.values()) {
            if (status == true) {
                counter++;
            }
        }
        if (counter == maxNumberPlayers) {
            for (VirtualClient clients : clientList.values()) {
                clients.sendCommand(new StartGameObj());
            }
            initGame();
        }
    }

    /**
     * Initializes the game by creating players and dealing cards.
     * It also sets the player controller for each client.
     */
    public void initGame() throws RemoteException, IllegalStateOperationException {
        if (model.getGameState() == GameState.SETUP) {
            playerList = model.createPlayers(clientList.keySet()); // Here the players are created and added to the
            // playerList
            model.setObjectives(); // Here the common goals are initialized
            model.initSecretObj(); // Here the secret goals are drawn

            createAllListeners();
            for (Player player : playerList.values()) {
                player.setStarterCard(); // Here the starter cards are drawn
                player.drawResource();
                player.drawResource();
                player.drawGold(); // Here the player hands are initialized
                // showHand(player); // Here the player's hands are shown
                // showObjectives(playerList.get(player)); // Here the common goals are supposed
                // to be shown :)
            }
            model.getBoard().getDeckGold().refill(); // Here the GoldCard1 and GoldCard2 are drawn on the board
            model.getBoard().getDeckResource().refill(); // Here the ResourceCard1 and ResourceCard2 are drawn on the
            // board
        } else {
            System.out.println("Failed to initialize game.");
            throw new RemoteException();
        }

        // TODO mandare messaggio al client di inizio partita
        // for (VirtualClient player: clientList.values()) {
        // player.sendMessage("[GameController] la partita è iniziata");
        // }
    }

    public void sendChatMessage(NewChatMessage message) {
        for (VirtualClient client : clientList.values()) {
            try {
                client.sendCommand(message);
            } catch (RemoteException e) {
                e.printStackTrace(); // TODO da gestire
            }

        }
    }

    /*
     * TODO Do I use this and create all the initStarters etc. methods in the
     * GameController,
     * or do I use the methods in the GameModel and call them from the
     * GameController?
     */

    /**
     * @return the maximum number of players.
     */
    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    /**
     * @return the current number of players.
     */
    public int getCurrentNumberPlayers() {
        return clientList.size();
    }

    /*
     * public Player getPlayer(String username) {
     * return playerList.get(username);
     * }
     */

    /**
     * Writes a message to the game controller.
     *
     * @param text the message to write.
     */
    private void gameControllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_PURPLE
                + DefaultValues.gameControllerTag(String.valueOf(idGame)) + DefaultValues.ANSI_RESET + text);
    }

    // WARNING: methods receive username in input, instead of using
    // model.currPlayingPlayer.drawGold() etc.
    // because otherwise clients could play the turn of others

    /**
     * Draws a gold card from the deck for the player and then shows the player's
     * hand.
     *
     */
    public void drawGold(String username){
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN || model.getGameState() == GameState.SHOWDOWN) {
            if (playerList.get(username).drawGold()) {
                model.endTurn();
            }
        } else {
            gameControllerWrite("The game is not in the right state to draw");
        }
    }

    /**
     * Draws the first gold card for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawGoldCard1(String username) throws RemoteException {
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN
                || model.getGameState() == GameState.SHOWDOWN) {
            //addQueueObj(new DrawGoldOneObj(playerList.get(username), model));
        } else {
            gameControllerWrite("The game is not in the right state to draw");
        }
    }

    /**
     * Draws the second gold card for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawGoldCard2(String username) throws RemoteException {
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN
                || model.getGameState() == GameState.SHOWDOWN) {
            addQueueObj(new DrawGoldTwoObj(playerList.get(username), model));
        } else {
            gameControllerWrite("The game is not in the right state to draw");
        }
    }

    /**
     * Draws a resource card from the deck for the player and then shows the
     * player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawResource(String username) throws RemoteException {
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN
                || model.getGameState() == GameState.SHOWDOWN) {
            addQueueObj(new DrawResObj(playerList.get(username), model));
        } else {
            gameControllerWrite("The game is not in the right state to draw");
        }
    }

    /**
     * Draws the first resource card for the player and then shows the player's
     * hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawResourceCard1(String username) throws RemoteException {
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN
                || model.getGameState() == GameState.SHOWDOWN) {
            addQueueObj(new DrawResOneObj(playerList.get(username), model));
        } else {
            gameControllerWrite("The game is not in the right state to draw");
        }
    }

    /**
     * Draws the second resource card for the player and then shows the player's
     * hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawResourceCard2(String username) throws RemoteException {
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN
                || model.getGameState() == GameState.SHOWDOWN) {
            addQueueObj(new DrawResTwoObj(playerList.get(username), model));
        } else {
            gameControllerWrite("The game is not in the right state to draw");
        }
    }

    public void chooseSecretObjective(String username, Integer index) {
        if (model.getGameState() == GameState.SETUP) {
            model.setPlayerObjective(username, index);
        } else
            gameControllerWrite("The game is not in the right state to choose secret objective");
    }

    @Override
    public void play(String username, int x, int y) {
        if (model.getGameState() == GameState.RUNNING || model.getGameState() == GameState.LAST_TURN
                || model.getGameState() == GameState.SHOWDOWN) {
            addQueueObj(new PlayObj(playerList.get(username), model, x, y));
        } else {
            gameControllerWrite("The game is not in the right state to play");
        }

    }

    @Override
    public void playStarter(String username) {
        if (model.getGameState() == GameState.SETUP) {
            addQueueObj(new PlayStarterObj(playerList.get(username), model));
        } else {
            gameControllerWrite("The game is not in the right state to play starter");
        }
    }

    @Override
    public void changeSide(String username) {
        addQueueObj(new FlipCardObj(playerList.get(username)));
    }

    @Override
    public void changeStarterSide(String username) {
        addQueueObj(new FlipStarterCardObj(playerList.get(username)));
    }

    @Override
    public void selectCard(String username, int index) {
        addQueueObj(new SelectCardObj(playerList.get(username), index));
    }

    /*
     * @Override
     * public void play(String username, Point point) {
     * Player player = playerList.get(username);
     * player.play(point);
     * }
     */

    /*
     * @Override
     * public void selectCard(String username, int index) {
     * Player player = playerList.get(username);
     * player.setSelectedCard(index);
     * }
     */

    // PRIVATE METHODS:

    public GameModel getModel() {
        return model;
    }

    public void createAllListeners() {
        List<PlayerHandListener> playerHandListenersList = new ArrayList<>();
        List<PlayerScoreListener> playerScoreListeners = new ArrayList<>();
        List<PlayAreaListener> playAreaListenerList = new ArrayList<>();

        List<GoldDeckListener> goldDeckListeners = new ArrayList<>();

        // TODO creare funzione per la creazione di tutti i listener
        for (String username : playerList.keySet()) {
            // create playerHandListener for all players
            playerHandListenersList.add(new PlayerHandListener(clientList.get(username)));
            // create playerScoreListener for all players
            playerScoreListeners.add(new PlayerScoreListener(clientList.get(username)));
            // create playAreaListener for all players
            playAreaListenerList.add(new PlayAreaListener(clientList.get(username)));

            goldDeckListeners.add(new GoldDeckListener(clientList.get(username)));
        }

        for (Player player : playerList.values()) {
            // add all playerHandListener to all player
            for (PlayerHandListener listener : playerHandListenersList) {
                player.addPlayerHandListener(listener);
            }
            // add all playerScoreListener to all player
            for (PlayerScoreListener listener : playerScoreListeners) {
                player.addPlayerScoreListener(listener);
            }
            // add all playAreaListener to all player
            for (PlayAreaListener listener : playAreaListenerList) {
                player.addPlayAreaListener(listener);
            }
            // add to the player its own playerStarterCardListener
            player.addPlayerStarterCardListener(new PlayerStarterCardListener(clientList.get(player.getUsername())));

            // add to the player its own playerObjectiveCardListener
            player.addPlayerObjectiveCardListener(
                    new PlayerObjectiveCardListener(clientList.get(player.getUsername())));
        }

        for (GoldDeckListener listener : goldDeckListeners) {
            model.getBoard().getDeckGold().addListener(listener);
        }
    }

}
