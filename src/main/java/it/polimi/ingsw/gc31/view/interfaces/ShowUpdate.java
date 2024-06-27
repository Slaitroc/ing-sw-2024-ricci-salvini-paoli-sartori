package it.polimi.ingsw.gc31.view.interfaces;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import javafx.util.Pair;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ShowUpdate {

    /**
     * Notify the player the game has started
     */
    void update_ToPlayingState();

    /**
     * Provide the client with the gold Deck cards
     *
     * @param firstCardDeck the first card in the deck
     * @param card1 the second card in the deck
     * @param card2 the third card in the deck
     */
    void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2);

    /**
     * Provide the client with the resource Deck cards
     *
     * @param firstCardDeck the first card in the deck
     * @param card1 the second card in the deck
     * @param card2 the third card in the deck
     */
    void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2);

    /**
     * Provide the client with the common objective cards
     *
     * @param card1 the first common objective card
     * @param card2 the second common objective card
     */
    void show_commonObjectiveCard(ObjectiveCard card1, ObjectiveCard card2);

    /**
     * Provide the starter card for a specific player.
     *
     * @param username the username of the player
     * @param starterCard the starter card to be displayed
     */
    void show_starterCard(String username, PlayableCard starterCard);

    /**
     * Provide the secret objective card for a specific player.
     *
     * @param username the username of the player
     * @param objectiveCard the objective card to be displayed
     */
    void show_objectiveCard(String username, ObjectiveCard objectiveCard);

    /**
     * Provide the choice of objective cards for a specific player.
     *
     * @param username the username of the player
     * @param objectiveCard1 the first objective card
     * @param objectiveCard2 the second objective card
     */
    void show_chooseObjectiveCard(String username, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2);

    /**
     * Provide the play area and achieved resources for a specific player.
     *
     * @param username the username of the player
     * @param playArea the play area map
     * @param achievedResources the resources achieved by the player
     */
    void show_playArea(String username, LinkedHashMap<Point, PlayableCard> playArea,
            Map<Resources, Integer> achievedResources);

    /**
     * Provide the cards in hand for a specific player.
     *
     * @param username the username of the player
     * @param hand the list of cards in the player's hand
     * @param selectedCard the index of the selected card
     */
    void show_handPlayer(String username, List<PlayableCard> hand, int selectedCard);

    void show_scorePlayer(LinkedHashMap<String, Pair<Integer, Boolean>> scores);

    /**
     * Provide a list of available games.
     *
     * @param listGame the list of game names or IDs
     */
    void show_listGame(List<String> listGame);

    /**
     * Notify that a game has been created.
     *
     * @param gameID the ID of the created game
     */
    void show_gameCreated(int gameID);

    /**
     * Notify that a username is valid.
     *
     * @param username the valid username
     */
    void show_validUsername(String username);

    /**
     * Notify that a username is invalid.
     *
     * @param username the invalid username
     */
    void show_wrongUsername(String username);

    /**
     * Notify that a player has joined a game.
     *
     * @param id the game ID
     * @param maxNumberOfPlayers the maximum number of players in the game
     */
    void show_joinedToGame(int id, int maxNumberOfPlayers);

    /**
     * Notify that a player has quit the game.
     *
     * @param username the username of the player who quit
     */
    void show_quitFromGame(String username);

    /**
     * Notify that a game you tried to enter is full.
     *
     * @param id the game ID
     */
    void show_gameIsFull(int id);

    /**
     * Update the ready status of a player.
     *
     * @param username the username of the player
     * @param status the ready status of the player
     */
    void show_readyStatus(String username, boolean status);

    /**
     * Display a chat message.
     *
     * @param username the username of the player who sent the message
     * @param message the chat message
     */
    void show_chatMessage(String username, String message);

    /**
     * Display a private chat message.
     *
     * @param fromUsername the username of the sender
     * @param toUsername the username of the recipient
     * @param message the private chat message
     */
    void show_privateChatMessage(String fromUsername, String toUsername, String message);

    /**
     * Notify that a game does not exist.
     */
    void show_gameDoesNotExist();

    /**
     * Notify that the game size is invalid.
     */
    void show_wrongGameSize();

    void show_playerTurn(String username, String info);

    /**
     * Display the in-game players and their statuses.
     * Sent both when a player join and when a player quit
     *
     * @param players a map of player usernames to their statuses
     */
    void show_inGamePlayers(LinkedHashMap<String, Boolean> players);

    /**
     * Notify of an invalid action with a message.
     *
     * @param message the message describing the invalid action
     */
    void show_invalidAction(String message);

    /**
     * At the end of the match show the name of the winner and the scores of the in game players
     * @param username String containing the name of the winner
     * @param playersScore Map of Players names with their relatives score
     */
    void show_GameIsOver(String username, Map<String, Integer> playersScore);

    /**
     * Method used to allow the client to save his temporary token on the first response with the server
     * or the game token, used to identify the game the player was disconnected from
     *
     * @param token number of the token received
     * @param temporary if the token received is temporary or not
     */
    void receiveToken(int token, boolean temporary);

    /**
     * Show a response from the server to the client heartbeat. Could be used to show the ping
     */
    void show_heartBeat();

    /**
     * Response from the server to an already connected player doing a new connection
     * Client is supposed to be asked if they want to reconnect or not upon receiving this method
     * @param username Old username retrieved from server
     */
    void show_wantReconnect(String username);

    /**
     * Upon Client command of rejoin or not this method notify the client of the result of the operation
     * providing the list of the players in game in case the result is true. if the result is false
     * the client will be asked to log in again
     *
     * @param result true if client wants to reconnect to server, false otherwise
     * @param players List of players username (Used in gui to load the Game Scene)
     */
    void show_rejoined(boolean result, List<String> players);

    /**
     * Unimplemented method to ask players if they want to rejoin a new match after the current one is ended
     */
    void show_anotherMatch();

    /**
     * A countDown is shown if it is detected that only one player is left in the match
     * If the countdown reach zero, the last player is the winner. Method is called
     * repeatedly showing how may seconds are left
     * @param secondsLeft Integer representing the seconds left (on the server countdown)
     */
    void show_timerLastPlayerConnected(Integer secondsLeft);

    /**
     *
     */
    void show_unableToReconnect();

    /**
     * Unimplemented method to show players responses to the new match request
     */
    void show_requestAnotherMatch();
}