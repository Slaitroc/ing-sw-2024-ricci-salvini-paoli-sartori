package it.polimi.ingsw.gc31.model.gameModel;

import java.awt.*;
import java.util.*;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayAreaObj;
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
    int pawnSelector;
    protected Map<String, Player> players;
    protected List<ObjectiveCard> secretObjectives;
    protected List<String> turnPlayer;
    private int currPlayingPlayer = 0;
    private GameModelState gameState;

    /**
     * Constructor for the GameModel class.
     * It initializes the board and the players map.
     */
    public GameModel() {
        pawnSelector = 0;
        this.board = new Board();
        this.players = new HashMap<>();
        this.turnPlayer = null;
        this.secretObjectives = new ArrayList<>();
        this.gameState = new CreationGameModelState();
    }

    public void initGame(LinkedHashMap<String, VirtualClient> clients) throws IllegalStateOperationException {
        players = gameState.initGame(this, clients);
    }
    protected void endGame() throws IllegalStateOperationException {
        gameState.endGame(this);
    }
    /**
     * This method assigns a pawn color to a player.
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

    public Board getBoard() {
        return board;
    }

    /**
     * This method is used to end the turn of a player.
     * It also
     */
    public void endTurn() throws IllegalStateOperationException {
        gameState.detectEndGame(this);
        setNextPlayingPlayer();
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

    protected void setGameState(GameModelState gameState) {
        this.gameState = gameState;
    }

    public void chooseSecretObjective(String username, Integer index) throws IllegalStateOperationException {
        gameState.chooseSecretObjective(this, username, index);
    }

    public void playStarter(String username) throws IllegalStateOperationException, ObjectiveCardNotChosenException {
        gameState.playStarter(this, username);
    }

    public void play(String username, Point point) throws IllegalStateOperationException {
        gameState.play(this, username, point);
    }

    public void drawGold(String username, int index) throws IllegalStateOperationException {
        gameState.drawGold(this, username, index);
    }

    public void drawResource(String username, int index) throws IllegalStateOperationException {
        gameState.drawResource(this, username, index);
    }

    public void setSelectCard(String username, int index) throws IllegalStateOperationException, WrongIndexSelectedCard {
        gameState.setSelectCard(this, username, index);
    }

    public void changeSide(String username) throws IllegalStateOperationException {
        gameState.changeSide(this, username);
    }

    public void changStarterSide(String username) throws IllegalStateOperationException {
        gameState.changeStarterSide(this, username);
    }

    // Test methods
    GameModelState getGameState() {
        return gameState;
    }
}
