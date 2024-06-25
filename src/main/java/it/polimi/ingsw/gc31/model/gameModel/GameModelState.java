package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.Map;

/*
 * @throws IllegalStateOperationException if model is in CreationGameModelState, SetupGameModelState, RunningGameModelState, LastTurnGameModelState, ShowDownGameModelState or EndGameModelState
 */
public interface GameModelState {

    /**
     * Initializes the game by setting up players, drawing initial cards, and updating the game state.
     * Usable only in CreateGameModelState state of the game
     *
     * <p>This method performs the following steps:</p>
     * <ol>
     *   <li>Creates players from the provided client usernames.</li>
     *   <li>Sets the starter card, draws initial gold and resource cards, and draws objective cards for each player.</li>
     *   <li>Refills the gold and resource decks on the board and draws common objective cards.</li>
     *   <li>Updates the initial scores of players on the board.</li>
     *   <li>Create a lock listener for every player</li>
     *   <li>Sets up listeners and connection statuses for each client.</li>
     *   <li>Changes the state of the game model to the setup state.</li>
     * </ol>
     *
     * @param model   The game model containing the current state of the game.
     * @param clients A LinkedHashMap of client usernames and their corresponding VirtualClient instances.
     * @return A map of player usernames to Player objects representing the initialized players.
     * @throws IllegalStateOperationException if model is in {@code SetupGameModelState}, {@code RunningGameModelState}, {@code LastTurnGameModelState}, {@code ShowDownGameModelState} or {@code EndGameModelState}
     */
    Map<String, Player> initGame(GameModel model, Map<String, VirtualClient> clients, Object lock) throws IllegalStateOperationException;

    /**
     * Assign a secret objective to the player between the two already saved in Player model
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player choosing the secret objective.
     * @param index    The index of the secret objective card to be chosen (1 or 2).
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code RunningGameModelState}, {@code LastTurnGameModelState}, {@code ShowDownGameModelState} or {@code EndGameModelState}
     */
    void chooseSecretObjective(GameModel model, String username, Integer index) throws IllegalStateOperationException;

    /**
     * Allows a player to play their starter card and checks if all players have played their starter card.
     *
     * <p>If all players have played are in the state 'start', the game state is advanced to the running state, the first player is set to play,
     * and all game listeners are notified.</p>
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player playing their starter turn.
     * @throws IllegalStateOperationException  if model is in {@code CreationGameModelState}, {@code SetupGameModelState}, {@code RunningGameModelState}, {@code LastTurnGameModelState}, {@code ShowDownGameModelState} or {@code EndGameModelState}
     * @throws ObjectiveCardNotChosenException if the player has not chosen an objective card before playing the starter turn.
     */
    void playStarter(GameModel model, String username) throws IllegalStateOperationException, ObjectiveCardNotChosenException;

    /**
     * Allows a player to play their turn by placing a card at a specified point on the game board.
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player playing their turn.
     * @param point    The point on the game board where the card is to be placed.
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code SetupGameModelState} or {@code EndGameModelState}
     * @throws IllegalPlaceCardException      if the card cannot be legally placed at the specified point.
     */
    void play(GameModel model, String username, Point point) throws IllegalStateOperationException, IllegalPlaceCardException;

    /**
     * Allows a player to draw a gold card and ends their turn if the draw is successful.
     *
     * @param model    The game model containing the current state of the game, including players and the game board.
     * @param username The username of the player drawing the gold card.
     * @param index    The index of the gold card to be drawn (0->deck, 1->Card1, 2->Card2).
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code SetupGameModelState} or {@code EndGameModelState}
     */
    void drawGold(GameModel model, String username, int index) throws IllegalStateOperationException;

    /**
     * Allows a player to draw a resource card and ends their turn if the draw is successful.
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player drawing the resource card.
     * @param index    The index of the resource card to be drawn (0->deck, 1->Card1, 2->Card2).
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code SetupGameModelState} or {@code EndGameModelState}
     */
    void drawResource(GameModel model, String username, int index) throws IllegalStateOperationException;

    /**
     * Allows a player to select a card from their hand.
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player selecting the card.
     * @param index    The index of the card to be selected (from 0 to 2).
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState} or {@code EndGameModelState}
     * @throws WrongIndexSelectedCard         if the specified index is invalid or out of bounds.
     */
    void setSelectCard(GameModel model, String username, int index) throws IllegalStateOperationException, WrongIndexSelectedCard;

    /**
     * Allows a player to change the side of their selected card in the hand.
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player changing the side.
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState} or {@code EndGameModelState}
     */
    void changeSide(GameModel model, String username) throws IllegalStateOperationException;

    /**
     * Allows a player to change the side of their starter card before placing it.
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player changing the side.
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code RunningGameModelState}, {@code LastTurnGameModelState}, {@code ShowDownGameModelState} or {@code EndGameModelState}
     */
    void changeStarterSide(GameModel model, String username) throws IllegalStateOperationException;

    /**
     * Detects if the game should transition to the end game state based on the current player's score and deck status.
     *
     * <p>The game transitions to different states depending on the conditions:</p>
     * <ul>
     *   <li>If the current player's score is greater than or equal to the game points threshold or both decks are empty
     *   the game status should transitions to the next status of the game.</li>
     *   <li>If the current player is the last player in the turn order, the game transitions to {@code LastTurnGameModelState}
     *   otherwise it transitions to {@code ShowDownGameModelState}.</li>
     * </ul>
     *
     * @param model         The game model containing the current state of the game.
     * @param bothEmptyDeck A boolean indicating whether both decks are empty.
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code SetupGameModelState} or {@code EndGameModelState}
     */
    void detectEndGame(GameModel model, Boolean bothEmptyDeck) throws IllegalStateOperationException;

    /**
     * Ends the game and calculates the final scores for all players.
     *
     * <p>This method performs the following steps:</p>
     * <ol>
     *   <li>Calculates the scores for each player's individual and common objective cards.</li>
     *   <li>Determines the player with the highest score.</li>
     *   <li>Sends a command to all clients indicating that the game is over and providing the final scores.</li>
     * </ol>
     *
     * @param model The game model containing the current state of the game.
     * @throws IllegalStateOperationException if model is in {@code CreationGameModelState}, {@code SetupGameModelState}, {@code RunningGameModelState}, {@code LastTurnGameModelState} or {@code ShowDownGameModelState}
     */
    void endGame(GameModel model) throws IllegalStateOperationException;

    // TODO forse non necessario

    /**
     * Executes the disconnection of a player.
     * If the disconnected player is not the current turn player, it does nothing.
     * See GameModel executeDisconnectPlayer
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player who disconnected
     */
    void disconnectPlayer(GameModel model, String username);

    /**
     * Executes the reconnection of a player.
     * See GameModel executeReconnectPlayer
     *
     * @param model    The game model containing the current state of the game.
     * @param username The username of the player who disconnected
     */
    void reconnectPlayer(GameModel model, String username);
}
