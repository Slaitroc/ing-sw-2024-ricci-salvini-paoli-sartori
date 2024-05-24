package it.polimi.ingsw.gc31.model.gameModel;

import java.awt.*;
import java.util.*;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
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
    private Map<String, Player> players;
    private ObjectiveCard objective1, objective2;
    private List<ObjectiveCard> secretObjectives;
    private List<String> turnPlayer;
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
        this.gameState = new CreationGameModelState();
    }

    public void initGame(Map<String, VirtualClient> clients) throws IllegalStateOperationException {
        players = gameState.initGame(this, clients);
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
        }
        currPlayingPlayer = (currPlayingPlayer + 1) % players.size();

        // pongo tutti i giocatori a waiting
        for (Player player : players.values()) {
            player.setInGameState(new Waiting());
        }
        // pongo il giocatore in gioco a notPlaced
        players.get(turnPlayer.get(currPlayingPlayer)).setInGameState(new NotPlaced());

    }

    public Board getBoard() {
        return board;
    }
    /**
     * This method is used to end the turn of a player.
     * It also
     */
    public void endTurn() {
        // FIXME gestire qua questa eccezione?
        try {
            gameState.detectEndGame(this);
            setNextPlayingPlayer();
        } catch (IllegalStateOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    // FIXME non penso serva sincronizzare il metodo
    /**
     * This method is used to detect when a player reaches 20 points.
     */
    private void detectEndGame() {
//        synchronized (this) {
//            if (this.gameState == GameState.RUNNING & getCurrPlayingPlayer().getScore() >= 20) {
//                startShowdown();
//                System.out.println("Someone reached 20 points!");
//            } else if (this.gameState == GameState.SHOWDOWN & getCurrPlayingPlayer() == players.getFirst()) {
//                startLastTurn();
//                System.out.println("Players has now one more turn to play!");
//            } else if (this.gameState == GameState.LAST_TURN && getCurrPlayingPlayer() == players.getLast()) {
//                startEndGame();
//                System.out.println("\n_____GAME HAS ENDED_____!\n");
//                for (Player player : players) {
//                    player.calculateObjectiveCard(objective1);
//                    player.calculateObjectiveCard(objective2);
//                    player.calculateObjectiveCard(player.getObjectiveCard());
//                    System.out.println(player.getUsername() + " has " + player.getScore() + " points!");
//                }
//            }
//        }
    }
    public Map<String, Player> getPlayers() {
        return players;
    }
    public Player getCurrPlayer() {
        return players.get(turnPlayer.get(currPlayingPlayer));
    }
    public int getCurrIndexPlayer() {
        return currPlayingPlayer;
    }
    public void setGameState(GameModelState gameState) {
        this.gameState = gameState;
    }


    public void chooseSecretObjective(String username, Integer index) throws IllegalStateOperationException {
        gameState.chooseSecretObjective(this, username, index);
    }

    public void playStarter(String username) throws IllegalStateOperationException {
        gameState.playStarter(this, username);
    }

    public void play(String username, Point point) throws IllegalStateOperationException {
        gameState.play(this, username, point);
    }

    public void drawGold(String username, int index) throws IllegalStateOperationException {
        gameState.drawGold(this, username, index);
    }

    public void setSelectCard(String username, int index) throws IllegalStateOperationException {
        gameState.setSelectCard(this, username, index);
    }

    public void changeSide(String username) throws IllegalStateOperationException {
        gameState.changeSide(this, username);
    }

    public void changStarterSide(String username) throws IllegalStateOperationException {
        gameState.changeStarterSide(this, username);
    }
}
