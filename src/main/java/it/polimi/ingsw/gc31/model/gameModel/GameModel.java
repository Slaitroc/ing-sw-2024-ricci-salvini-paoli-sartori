package it.polimi.ingsw.gc31.model.gameModel;

import java.awt.*;
import java.util.*;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.GameListenerHandler;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.player.NotPlaced;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.model.player.Waiting;

/**
 * This class represents the game model of the application.
 * It contains the game board and a map of players.
 * It also has a static variable pawnSelector for assigning pawn colors to
 * players.
 *
 * @author Slaitroc
 */
public class GameModel {
    private final Board board;
    private int pawnSelector;
    protected Map<String, Player> players;
    protected Map<String, VirtualClient> clients;
    protected List<ObjectiveCard> commonObjectives;
    // TODO rendere players Linked per usare quella per i turni
    protected List<String> turnPlayer;
    protected final Map<String, Boolean> playerConnection;
    private int currPlayingPlayer = 0;
    protected GameModelState gameState;
    private final Map<String, GameListenerHandler> listeners;
    private boolean isStarted = false;
    private final int idGame;

    /**
     * Constructor for the GameModel class.
     * It initializes the board and the players map.
     */
    public GameModel(int idGame) {
        this.idGame = idGame;
        pawnSelector = 0;
        this.board = new Board();
        this.players = new HashMap<>();
        this.turnPlayer = null;
        this.commonObjectives = new ArrayList<>();
        this.gameState = new CreationGameModelState();
        this.listeners = new HashMap<>();
        this.playerConnection = new HashMap<>();
    }

    public void initGame(LinkedHashMap<String, VirtualClient> clients) throws IllegalStateOperationException {
        this.clients = clients;
        players = gameState.initGame(this, clients);
        isStarted = true;
        notifyAllGameListeners();
    }

    protected void endGame() throws IllegalStateOperationException {
        gameState.endGame(this);
        listeners.values().forEach(listener -> listener.notifyPlayerScoreListener(this));

        for (String player: players.keySet()) {
            System.out.println("player: " + player + " has scored "+players.get(player).getScore());
        }
    }

    /**
     * Ends the current turn of the game.
     * This method detects if the game has ended, sets the next playing player, and
     * notifies player score listeners.
     *
     * @throws IllegalStateOperationException if the game is not in the right state
     */
    public void endTurn() throws IllegalStateOperationException {
        synchronized (playerConnection) {
            do {
                gameState.detectEndGame(this);
                setNextPlayingPlayer();

            } while (!playerConnection.get(getCurrPlayer().getUsername()));
        }
        listeners.values().forEach(listener -> listener.notifyPlayerScoreListener(this));
        listeners.values().forEach(listener -> listener.notifyTurnListener(this));
    }

    /**
     * Sets the next playing player.
     * If the turnPlayer is null, which means that it is the first time this method
     * is called, it initializes the turnPlayer list
     * with all the players and sets the currPlayingPlayer index to 0. Otherwise, it
     * increments the currPlayingPlayer index by 1
     * and wraps it around if it exceeds the number of players.
     * Disconnected players are skipped so the rest of the players can play
     * Once it finds the next playing player, sets the state of all players to
     * waiting, while the state of the new current player is set to not placed.
     */
    public void setNextPlayingPlayer() {
//        synchronized (playerConnection) {
//            do {
                if (turnPlayer == null) {
                    turnPlayer = new ArrayList<>();
                    turnPlayer.addAll(players.keySet());
                    currPlayingPlayer = 0;
                } else {
                    currPlayingPlayer = (currPlayingPlayer + 1) % players.size();
                }
//            } while (!playerConnection.get(getCurrPlayer().getUsername()));
//        }

        // set all players to waiting state
        for (Player player : players.values()) {
            player.setInGameState(new Waiting());
        }
        // set in game player to notPlaced state
        players.get(turnPlayer.get(currPlayingPlayer)).setInGameState(new NotPlaced());
//        listeners.values().forEach(listener -> listener.notifyTurnListener(this));
//        notifyAllGameListeners();
    }

    /**
     * Assigns a pawn color to a player.
     * It uses the static variable pawnSelector to determine the color.
     * The pawnSelector is incremented after each assignment.
     *
     * @return The PawnColor assigned to the player.
     */
    protected PawnColor pawnAssignment() {
        // TODO do we let the player choose his color?
        PawnColor color = switch (pawnSelector) {
            case 0 -> PawnColor.RED;
            case 1 -> PawnColor.BLUE;
            case 2 -> PawnColor.GREEN;
            case 3 -> PawnColor.YELLOW;
            default -> null;
        };
        pawnSelector++;
        return color;
    }

    public Board getBoard() {
        return board;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    protected Player getCurrPlayer() {
        if (turnPlayer != null) {
            return players.get(turnPlayer.get(currPlayingPlayer));
        }
        return null;
    }

    protected int getCurrIndexPlayer() {
        return currPlayingPlayer;
    }

    public void setGameState(GameModelState gameState) {
        this.gameState = gameState;
    }

    public List<ObjectiveCard> getCommonObjectives() {
        return this.commonObjectives;
    }

    public Map<String, GameListenerHandler> getListeners() {
        return this.listeners;
    }

    public void chooseSecretObjective(String username, Integer index) throws IllegalStateOperationException {
        gameState.chooseSecretObjective(this, username, index);
        listeners.get(username).notifyObjectiveCardListener(this);
    }

    /**
     * Plays the starter card for the specified username.
     *
     * @param username the username of the player
     * @throws IllegalStateOperationException  if the game is not in the right state
     * @throws ObjectiveCardNotChosenException if the player has not chosen a secret
     *                                         objective card yet
     */
    public void playStarter(String username) throws IllegalStateOperationException, ObjectiveCardNotChosenException {
        gameState.playStarter(this, username);
        listeners.get(username).notifyPlayAreaListener(this);
    }

    /**
     * Plays a card on the game board for a specified player.
     *
     * @param username the username of the player
     * @param point    the location on the game board where the card will be placed
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        for playing a card
     * @throws IllegalPlaceCardException      if the card cannot be placed at the
     *                                        specified location
     */
    public void play(String username, Point point) throws IllegalStateOperationException, IllegalPlaceCardException {
        gameState.play(this, username, point);
        listeners.get(username).notifyPlayAreaListener(this);
        listeners.get(username).notifyHandListener(this);
        listeners.values().forEach(listener -> listener.notifyPlayerScoreListener(this));
        listeners.values().forEach(listener -> listener.notifyTurnListener(this));
    }

    /**
     * Draws a gold card from the deck for a specific player.
     *
     * @param username the username of the player
     * @param index    the index of the gold card to be drawn
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        for drawing a gold card
     */
    public void drawGold(String username, int index) throws IllegalStateOperationException {
        gameState.drawGold(this, username, index);
        listeners.values().forEach(listener -> listener.notifyGoldDeckListener(this));
        listeners.get(username).notifyHandListener(this);
    }

    /**
     * Draws a resource card from the deck for the specified player.
     *
     * @param username the username of the player
     * @param index    the index of the resource card to be drawn
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        for drawing a resource card
     */
    public void drawResource(String username, int index) throws IllegalStateOperationException {
        gameState.drawResource(this, username, index);
        listeners.values().forEach(listener -> listener.notifyResourcedDeckListener(this));
        listeners.get(username).notifyHandListener(this);
    }

    /**
     * Sets the selected card of the hand for the specified player.
     *
     * @param username the username of the player
     * @param index    the index of the selected card
     * @throws IllegalStateOperationException if the game is not in the right state
     * @throws WrongIndexSelectedCard         if the selected index is wrong
     */
    public void setSelectCard(String username, int index)
            throws IllegalStateOperationException, WrongIndexSelectedCard {
        gameState.setSelectCard(this, username, index);
        listeners.get(username).notifyHandListener(this);
    }

    public void changeSide(String username) throws IllegalStateOperationException {
        gameState.changeSide(this, username);
        listeners.get(username).notifyHandListener(this);
    }

    public void changStarterSide(String username) throws IllegalStateOperationException {
        gameState.changeStarterSide(this, username);
        listeners.get(username).notifyStarterCardListener(this);
    }

    /**
     * Disconnects a player from the game based on the game state.
     *
     * @param username The username of the player who disconnected
     */
    public void disconnectPlayer(String username) {
        this.gameState.disconnectPlayer(this, username);
    }

    public void reconnectPlayer(String username) {
        this.gameState.reconnectPlayer(this, username);
    }

    protected void executeReconnectPlayer(String username) {
        synchronized (playerConnection) {
            playerConnection.put(username, false);
        }
        ServerLog.gControllerWrite("The player " + username + " has rejoined game", idGame);
        notifyAllGameListeners();
    }

    /**
     * Executes the disconnection of a player by setting their connection status to
     * false in the playerConnection map.
     * If the disconnected player is the current turn player, it sets the next
     * playing player.
     * If the game is in the setup state (turnPlayer == null), it makes default
     * decisions for the secret objective card and for the placement of the starter
     * card.
     * If the disconnected player is not the current turn player, it does nothing.
     *
     * @param username The username of the player who disconnected
     */
    protected void executeDisconnectPlayer(String username) {
        synchronized (playerConnection) {
            playerConnection.put(username, false);
            if (turnPlayer == null) {
                try {
                    chooseSecretObjective(username, 0);
                    playStarter(username);
                } catch (IllegalStateOperationException | ObjectiveCardNotChosenException e) {

                }
                ServerLog.gControllerWrite("Default chooses for " + username, idGame);
            } else {
                if (getCurrPlayer().getUsername().equals(username)) {
                    try {
                        endTurn();
                    } catch (IllegalStateOperationException e) {

                    }
//                    setNextPlayingPlayer();
                }
            }
        }
        ServerLog.gControllerWrite("Player " + username + " has disconnected", idGame);
        notifyAllGameListeners();
    }

    /**
     * Notifies all game listeners.
     * This method invokes the notifyAllListeners method of each listener in the
     * listeners map.
     */
    public void notifyAllGameListeners() {
        listeners.values().forEach(listener -> listener.notifyAllListeners(this));
    }

    public Map<String, Boolean> getPlayerConnection() {
        return playerConnection;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public int getIdGame() {
        return idGame;
    }

    // Test methods
    GameModelState getGameState() {
        return gameState;
    }
}
