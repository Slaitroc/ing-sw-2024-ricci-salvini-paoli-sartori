package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.GameListenerHandler;
import it.polimi.ingsw.gc31.client_server.listeners.ListenerType;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.player.NotPlaced;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.model.player.Waiting;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents the game model of the application.
 * It contains the game board and a map of players.
 * It also has a static variable pawnSelector for assigning pawn colors to
 * players.
 *
 * @author Slaitroc
 */
public class GameModel {
    /**
     * It is the game board, where the decks are kept and where the players' scores
     * are kept track of
     */
    private final Board board;

    /**
     * Is used to assign the color of the pieces to the players
     */
    private int pawnSelector;

    /**
     * Represents a collection of players in a game.
     * This variable is used to store a mapping of player names to player objects.
     * The key of the map is the player name, and the value is the player object.
     */
    protected Map<String, Player> players;

    /**
     * A protected variable that represents the mapping of client identities to
     * their corresponding virtual clients.
     * <p>
     * The clients mapping is implemented as a Java HashMap, where the client
     * identity is used as the key
     * and the VirtualClient object is used as the value.
     */
    protected Map<String, VirtualClient> clients;

    /**
     * Lock object that is used to synchronize with the clientList map to avoid
     * concurrency problems
     */
    protected final Object clientListLock;

    /**
     * List of common objectives. At the end of the game these objectives assign the
     * corresponding score to the players who achieved them
     */
    protected List<ObjectiveCard> commonObjectives;

    /**
     * It is used to set the order of the players' turns
     */
    protected List<String> turnPlayer;

    /**
     * It is a map that associates player names with their connection status.
     * The connection status is represented by a Boolean value, where true indicates
     * that the player is currently connected, and false indicates that the player
     * is
     * currently disconnected.
     * <p>
     * It is initialized as ConcurrentMap to allow access and modification without
     * concurrency issues
     * <p>
     * If a player is disconnected then his boolean value is set to false
     */
    protected final Map<String, Boolean> playerConnection;

    /**
     * Index of the player who is playing
     */
    private int currPlayingPlayer = 0;

    /**
     * Represents the state of the game.
     */
    protected GameModelState gameState;

    /**
     * Contains the listeners of the players in the game and each
     * gameListenerHandler takes care of updates about a player.
     */
    private final Map<String, GameListenerHandler> listeners;

    /**
     * Indicates whether the game has been started or not.
     */
    private boolean isStarted = false;

    /**
     * The idGame variable represents the identification number of a game.
     */
    private final int idGame;

    /**
     * Constructor for the GameModel class.
     * It sets the state of the GameModel to {@link CreationGameModelState}.
     */
    public GameModel(Object clientListLock, int idGame) {
        this.clientListLock = clientListLock;
        this.idGame = idGame;
        pawnSelector = 0;
        this.board = new Board();
        this.players = new HashMap<>();
        this.turnPlayer = null;
        this.commonObjectives = new ArrayList<>();
        this.listeners = new HashMap<>();
        this.playerConnection = new ConcurrentHashMap<>();
        this.gameState = new CreationGameModelState();
    }

    /**
     * Initializes the game.
     *
     * @param clients a LinkedHashMap containing the virtual clients mapped with
     *                their usernames
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        to be initialized
     */
    public void initGame(Map<String, VirtualClient> clients, Object lock) throws IllegalStateOperationException {
        this.clients = clients;
        players = gameState.initGame(this, clients, lock);
        isStarted = true;
    }

    /**
     * Ends the game.
     *
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        to be ended
     */
    public void endGame(String lastPlayerConnected) throws IllegalStateOperationException {
        gameState.endGame(this, lastPlayerConnected);
    }

    /**
     * Ends the current turn of the game.
     * This method detects if the game has ended, sets the next playing player, and
     * notifies player score and player turn listeners.
     * Disconnected players are skipped so the rest of the players can play
     *
     * @throws IllegalStateOperationException if the game is not in the right state
     */
    public void endTurn() throws IllegalStateOperationException {
        boolean bothEmptyDeck = board.getDeckGold().isEmpty() && board.getDeckResource().isEmpty();
        synchronized (playerConnection) {
            do {
                gameState.detectEndGame(this, bothEmptyDeck);
                setNextPlayingPlayer();
            } while (!playerConnection.get(getCurrPlayer().getUsername()));
        }
    }

    /**
     * Sets the next playing player.
     * If the turnPlayer is null, which means that it is the first time this method
     * is called, it initializes the turnPlayer list
     * with all the players and sets the currPlayingPlayer index to 0. Otherwise, it
     * increments the currPlayingPlayer index by 1
     * and wraps it around if it exceeds the number of players.
     * Once it finds the next playing player, sets the state of all players to
     * waiting, while the state of the new current player is set to not placed.
     */
    public void setNextPlayingPlayer() {
        if (turnPlayer == null) {
            turnPlayer = new ArrayList<>();
            turnPlayer.addAll(players.keySet());
            currPlayingPlayer = 0;
        } else {
            currPlayingPlayer = (currPlayingPlayer + 1) % players.size();
        }

        // set all players to waiting state
        for (Player player : players.values()) {
            player.setInGameState(new Waiting());
        }
        // set in game player to notPlaced state
        players.get(turnPlayer.get(currPlayingPlayer)).setInGameState(new NotPlaced());
    }

    /**
     * Assigns a pawn color to a player.
     * It uses the static variable pawnSelector to determine the color.
     * The pawnSelector is incremented after each assignment.
     *
     * @return The PawnColor assigned to the player.
     */
    protected PawnColor pawnAssignment() {
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

    /**
     * Chooses the secret objective for a player.
     *
     * @param username The username of the player
     * @param index    The index of the secret objective card to be chosen
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        to choose a secret objective
     */
    public void chooseSecretObjective(String username, Integer index) throws IllegalStateOperationException {
        gameState.chooseSecretObjective(this, username, index);
        listeners.get(username).removeListener(ListenerType.CHOOSE_OBJECTIVE);
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
        listeners.get(username).removeListener(ListenerType.STARTER_CARD);
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
    }

    /**
     * Draws a gold card from the deck for a specific player.
     *
     * @param username the username of the player
     * @param index    the index of the gold card to be drawn
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        for drawing a gold card
     */
    public void drawGold(String username, int index) throws IllegalStateOperationException, EmptyDeckException {
        gameState.drawGold(this, username, index);
    }

    /**
     * Draws a resource card from the deck for the specified player.
     *
     * @param username the username of the player
     * @param index    the index of the resource card to be drawn
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        for drawing a resource card
     */
    public void drawResource(String username, int index) throws IllegalStateOperationException, EmptyDeckException {
        gameState.drawResource(this, username, index);
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
    }

    /**
     * Changes the side of the selected card for the player with the given username.
     *
     * @param username the username of the player
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        to change the side
     */
    public void changeSide(String username) throws IllegalStateOperationException {
        gameState.changeSide(this, username);
    }

    /**
     * Changes the starter side for the player with the given username.
     *
     * @param username the username of the player
     * @throws IllegalStateOperationException if the game is not in the right state
     *                                        to change the starter side
     */
    public void changStarterSide(String username) throws IllegalStateOperationException {
        gameState.changeStarterSide(this, username);
    }

    /**
     * Disconnects a player from the game based on the game state.
     *
     * @param username The username of the player who disconnected
     */
    public void disconnectPlayer(String username) throws LastPlayerRemainedException {
        this.gameState.disconnectPlayer(this, username);
    }

    /**
     * Reconnects a player to the game based on the game state.
     *
     * @param username The username of the player to reconnect.
     */
    public void reconnectPlayer(String username) {
        this.gameState.reconnectPlayer(this, username);
    }

    /**
     * Executes the reconnection of a player by setting their connection status to
     * true in the playerConnection map. Also logs the reconnection information in
     * the server log.
     *
     * @param username The username of the player to reconnect.
     */
    protected void executeReconnectPlayer(String username) {
        playerConnection.put(username, true);

        ServerLog.gControllerWrite("The player " + username + " has rejoined game", idGame);
    }

    /**
     * Executes the disconnection of a player by setting their connection status to
     * false in the playerConnection map.
     * If the disconnected player is the current turn player, it draw a default card
     * if necessary and set the next
     * playing player.
     * If the game is in the setup state (turnPlayer == null), it makes default
     * decisions for the secret objective card and for the placement of the starter
     * card.
     * If the disconnected player is not the current turn player, it does nothing.
     *
     *
     * @param username The username of the player who disconnected
     * @throws LastPlayerRemainedException if the player is the last one in the
     *                                     game, set the status of the game to
     *                                     {@link BlockedGameModelState}
     *                                     and throws the exception.
     */
    protected synchronized void executeDisconnectPlayer(String username) throws LastPlayerRemainedException {
        playerConnection.put(username, false);

        int numberConnected = 0;
        String lastConnected = null;
        synchronized (playerConnection) {
            for (String user : playerConnection.keySet()) {
                if (playerConnection.get(user)) {
                    numberConnected++;
                    lastConnected = user;
                }
            }
        }

        if (turnPlayer == null) {
            try {
                chooseSecretObjective(username, 0);
                playStarter(username);
            } catch (IllegalStateOperationException | ObjectiveCardNotChosenException ignored) {

            }
            ServerLog.gControllerWrite("Default chooses for " + username, idGame);
        } else {
            if (getCurrPlayer().getUsername().equals(username)) {
                if (getPlayers().get(username).infoState().equals("placed") && !board.getDeckGold().hasBeenReplaced()) {
                    try {
                        drawGold(username, 0);
                    } catch (IllegalStateOperationException | EmptyDeckException ignored) {
                        ignored.printStackTrace();
                    }
                } else if (getPlayers().get(username).infoState().equals("placed")
                        && !board.getDeckResource().hasBeenReplaced()) {
                    try {
                        drawResource(username, 0);
                    } catch (IllegalStateOperationException | EmptyDeckException ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
            if (getCurrPlayer().getUsername().equals(username)) {
                try {
                    endTurn();
                } catch (IllegalStateOperationException ignored) {
                }
            }
        }
        ServerLog.gControllerWrite("Player " + username + " has disconnected", idGame);
        if (numberConnected == 1) {
            ServerLog.gControllerWrite("Player " + lastConnected + " is the last player connected left", idGame);
            setGameState(new BlockedGameModelState(this, gameState));
            throw new LastPlayerRemainedException(lastConnected);
        }
    }

    /**
     * Notifies all game listeners.
     * This method invokes the notifyAllListeners method of each listener in the
     * listeners map.
     */
    public void notifyAllGameListeners() {
        listeners.values().forEach(listener -> listener.notifyAllListeners(this));
    }

    /**
     * Returns a map representing the connection status of each player.
     *
     * @return a map where the keys are the usernames of the players and the values
     *         are booleans indicating their connection status.
     */
    public Map<String, Boolean> getPlayerConnection() {
        return playerConnection;
    }

    /**
     * Check if the game has started.
     *
     * @return true if the game has started, false otherwise.
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Retrieves the ID of the game.
     *
     * @return the ID of the game
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * Retrieves the game board.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieves the map of players in the game.
     *
     * @return a {@code Map<String, Player>} where the keys are the usernames of the
     *         players and the values are the Player objects.
     */
    public Map<String, Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves the player currently playing.
     * The current player is null if the turn has not yet started,
     * then the game has not yet been set to {@link RunningGameModelSate}.
     *
     * @return The current player, or null if there is no current player.
     */
    protected Player getCurrPlayer() {
        if (turnPlayer != null) {
            return players.get(turnPlayer.get(currPlayingPlayer));
        }
        return null;
    }

    /**
     * Retrieves the current index of the player who is currently playing.
     *
     * @return The current index of the playing player.
     */
    protected int getCurrIndexPlayer() {
        return currPlayingPlayer;
    }

    /**
     * Sets the game state of the GameModel.
     *
     * @param gameState The new game state to be set.
     */
    public void setGameState(GameModelState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the list of common objective cards in the game.
     *
     * @return A list of ObjectiveCard objects representing the common objectives.
     */
    public List<ObjectiveCard> getCommonObjectives() {
        return this.commonObjectives;
    }

    /**
     * Returns a map containing the listeners registered for the game.
     *
     * @return a map where the keys are the names of the listeners and the values
     *         are the corresponding {@link GameListenerHandler} objects.
     */
    public Map<String, GameListenerHandler> getListeners() {
        return this.listeners;
    }

    /**
     * Retrieves the current State of the game
     */
    GameModelState getGameState() {
        return gameState;
    }
}
