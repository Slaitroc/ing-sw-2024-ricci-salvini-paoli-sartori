package it.polimi.ingsw.gc31.model;

import java.awt.*;
import java.util.*;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.player.NotPlaced;
import it.polimi.ingsw.gc31.model.player.Player;

/**
 * This class represents the game model of the application.
 * It contains the game board and a map of players.
 * It also has a static variable pawnSelector for assigning pawn colors to players.
 *
 * @author Slaitroc
 */
public class GameModel {
    private final Board board;
    int playersInGame;
    int pawnSelector;
    private final List<Player> players;
    private int currPlayingPlayer = 0;
    private GameState gameState = GameState.SETUP;
    private ObjectiveCard objective1, objective2;
    private List<ObjectiveCard> secretObjectives;

    /**
     * Constructor for the GameModel class.
     * It initializes the board and the players map.
     */
    public GameModel() {
        pawnSelector = 0;
        this.board = new Board();
        this.players = new ArrayList<>();
    }

    //GAME SETUP STAGE METHODS:
    public Map<String, Player> createPlayers(Set<String> usernames) throws IllegalStateOperationException {
        if (this.gameState == GameState.SETUP) {
            HashMap<String, Player> players = new HashMap<>();
            for (String username : usernames) {
                Player player = new Player(pawnAssignment(), username, board);
                this.players.add(player);
                players.put(username, player);
            }
            playersInGame = players.size();
            return players;
        } else throw new IllegalStateOperationException();
    }

    public void initHands() throws IllegalStateOperationException {
        if (this.gameState == GameState.SETUP) {
            for (Player player : players) {
                player.drawResource();
                player.drawResource();
                player.drawGold();
            }
        } else throw new IllegalStateOperationException();
    }

    public void initCommonObj() throws IllegalStateOperationException {
        if (this.gameState == GameState.SETUP) {
            objective1 = board.getDeckObjective().draw();
            objective2 = board.getDeckObjective().draw();
        } else throw new IllegalStateOperationException();
    }

    public void initStarters() throws IllegalStateOperationException {
        if (this.gameState == GameState.SETUP) {
            for (Player player : players) {
                player.setStarterCard();
            }
        } else throw new IllegalStateOperationException();
    }

    public void initSecretObj() throws IllegalStateOperationException {
        if (this.gameState == GameState.SETUP) {
            secretObjectives = new ArrayList<>();
            for (Player player : players) {
                secretObjectives.add(board.getDeckObjective().draw());
                secretObjectives.add(board.getDeckObjective().draw());
            }
        } else throw new IllegalStateOperationException();
    }

    /**
     * This method assigns a pawn color to a player.
     * It uses the static variable pawnSelector to determine the color.
     * The pawnSelector is incremented after each assignment.
     *
     * @return The PawnColor assigned to the player.
     */
    private PawnColor pawnAssignment() {
        //TODO do we let the player choose his color?
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

    //GAME STAGES METHODS:
    public void checkStartGame() {
        for (Player player: players) {
            if (player.getPlayArea().getPlacedCards().get(new Point(0, 0)) == null) {
                return;
            }
        }
        this.gameState = GameState.RUNNING;
        System.out.println("Game has started!");
    }

    public void startShowdown() {
        this.gameState = GameState.SHOWDOWN;
    }

    public void startLastTurn() {
        this.gameState = GameState.LAST_TURN;
    }

    public void startEndGame() {
        this.gameState = GameState.END_GAME;
    }

    //SETTERS AND GETTERS:
    public ObjectiveCard getObjectives(int index) {
        if (index == 0) return objective1;
        if (index == 1) return objective2;
        return null;
    }

    public void setObjectives(){
        objective1 = board.getDeckObjective().draw();
        objective2 = board.getDeckObjective().draw();
    }

    public Player getCurrPlayingPlayer() {
        return this.players.get(currPlayingPlayer);
    }

    public void setNextPlayingPlayer() {
        this.currPlayingPlayer += 1;
        if (this.currPlayingPlayer == this.players.size()) {
            this.currPlayingPlayer = 0;
        }
        //System.out.println("IT'S NOW ACTUALLY " + getCurrPlayingPlayer().getUsername() + "'S TURN!");
    }

    public int getNumOfPlayers() {
        return playersInGame;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setPlayerObjective(String username, int index) {
        index --;
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                player.addObjectiveCard(secretObjectives.get(index));
                return;
            }
            else index += 2;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * This method is used to end the turn of a player.
     * It also
     */
    public void endTurn() {
        detectEndGame();
        setNextPlayingPlayer();
        getCurrPlayingPlayer().setInGameState(new NotPlaced());
        //System.out.println("PLAYER: " + getCurrPlayingPlayer().getUsername() + " IS NOW READY TO PLAY!");
    }

    /**
     * This method is used to detect when a player reaches 20 points.
     */
    private void detectEndGame() {
        synchronized (this){
            if (this.gameState == GameState.RUNNING & getCurrPlayingPlayer().getScore() >= 20) {
                startShowdown();
                System.out.println("Someone reached 20 points!");
            } else if (this.gameState == GameState.SHOWDOWN & getCurrPlayingPlayer() == players.getFirst()) {
                startLastTurn();
                System.out.println("Players has now one more turn to play!");
            } else if (this.gameState == GameState.LAST_TURN) {
                startEndGame();
                System.out.println("\n_____GAME HAS ENDED_____!\n");
                for (Player player : players) {
                    player.calculateObjectiveCard(objective1);
                    player.calculateObjectiveCard(objective2);
                    player.calculateObjectiveCard(player.getObjectiveCard());
                    System.out.println(player.getUsername() + " has " + player.getScore() + " points!");
                }
            }
        }
    }

}
