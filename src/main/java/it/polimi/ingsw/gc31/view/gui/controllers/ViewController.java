package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.gui.GUIApplication;
import it.polimi.ingsw.gc31.view.gui.GameInstance;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ViewController {

    protected GUIApplication app;
    protected ClientCommands client;
    private int countPrivateMessage = 0;

    protected ObservableList<GameInstance> gamesList;

    protected abstract void initialize();

    public void setGUIApplication(GUIApplication app) {
        this.app = app;
    }

    public void setClient(ClientCommands client) {
        this.client = client;
    }

    public ClientCommands getClient() {
        return this.client;
    }

    /**
     * Main way to handle Remote Exceptions. Shows a PopUp windows to display error message
     * and ask you to restart the application
     */
    protected void show_ServerCrashWarning(String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error 505");
        alert.setHeaderText("Server Error");
        alert.setContentText("Server has crashed. Please restart application \n\n\n" + details);

        alert.showAndWait();
    }

    /**
     * Method necessary to set up UI elements otherwise not
     * editable from the initiate method. Called right after scene is created
     */
    public void setUp() {
    }

    /**
     * Used generally to display a warning sign directly in the GUI.
     * Implemented by those controllers that support a warning sign in the Scene
     *
     * @param message Message to be shown
     */
    public void setMessage(String message) {
    }

    /**
     * Method called by the server.
     * Transform the prefix in "username: "
     * then calls populate chat to show the message log correctly
     *
     * @param username The username of the player that sent the message.
     * @param message  The message.
     */
    public void updateChat(String username, String message) {
    }

    /**
     * Transform the prefix of the received message in [From: “fromUsername"]: or [To: "toUsername"]:
     * based on the client username or ignores the message if he is not involved
     * then calls populate chat to show the message log correctly
     *
     * @param fromUsername The username of the player that sent the message.
     * @param toUsername   The username of the player that will receive the message.
     * @param message      The message.
     */
    public void updateChat(String fromUsername, String toUsername, String message) {
    }

    /**
     * Method implemented in the LobbyController only
     * Updates the ready status of players in the lobby.
     * This method sets the ready status text ("Ready" or "Not Ready") for each player in the lobby
     * based on their current status. It checks which player has changed their status and updates
     * the corresponding UI label accordingly.
     * The method performs the following actions:
     * 1. Retrieves the player's position in the list from the application.
     * 2. Compares the given username with the usernames in the player list.
     * 3. Sets the ready status text for the corresponding player based on the provided status.
     *
     * @param username the username of the player whose status has changed.
     * @param status   the current ready status of the player (true if ready, false otherwise).
     */
    public void showReady(String username, boolean status) {
    }

    /**
     * Method implemented in the InGameController only
     * Updates the UI to display the top cards of the gold deck.
     *
     * <p>This method sets the images of the provided {@link PlayableCard} objects
     * to their corresponding UI components for the gold deck. Specifically, it sets
     * the image of the first card in the deck and two other cards from the gold deck.</p>
     *
     * @param firstCardDeck The top card of the gold deck to be displayed.
     * @param card1         The first card from the gold deck to be displayed.
     * @param card2         The second card from the gold deck to be displayed.
     */
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
    }

    /**
     * Updates the UI to display the top cards of the resource deck.
     *
     * @param firstCardDeck The top card of the resource deck to be displayed.
     * @param card1         The first card from the resource deck to be displayed.
     * @param card2         The second card from the resource deck to be displayed.
     */
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
    }

    /**
     * Displays the hand of a specified player.
     *
     * @param username The username of the player whose hand is to be displayed.
     * @param hand     The list of {@link PlayableCard} objects representing the player's hand.
     */
    public void show_handPlayer(String username, List<PlayableCard> hand) {
    }

    /**
     * Displays the scores of all players and if it is their turn to play
     *
     * @param scores A {@link LinkedHashMap} where the keys are usernames and the values are scores.
     */
    public void show_scorePlayer(LinkedHashMap<String, Pair<Integer, Boolean>> scores) {
    }

    /**
     * Displays the starter card for the current player.
     *
     * @param username    The username of the player whose starter card is to be displayed.
     * @param starterCard The {@link PlayableCard} object representing the player's starter card.
     */
    public void show_starterCard(String username, PlayableCard starterCard) {
    }

    /**
     * Updates the UI to display the play area and achieved resources of a specified player
     * by calling {@code updateGrid} and {@code updateResources}
     *
     * @param username          The username of the player whose play area is to be displayed.
     * @param playArea          A {@link Map} representing the play area with {@link Point} keys and {@link PlayableCard} values.
     * @param achievedResources A {@link Map} representing the achieved resources with {@link Resources} keys and their quantities.
     */
    public void show_playArea(String username, Map<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {
    }

    /**
     * Displays the options for choosing an objective card to the current player.
     *
     * @param username             The username of the player who is choosing the objective card.
     * @param secretObjectiveCard1 The first {@link ObjectiveCard} option.
     * @param secretObjectiveCard2 The second {@link ObjectiveCard} option.
     */
    public void show_chooseObjectiveCard(String username, ObjectiveCard secretObjectiveCard1, ObjectiveCard secretObjectiveCard2) {
    }

    /**
     * Displays the common objective cards without checking the name of the player
     *
     * @param commonObjectiveCard1 The first common {@link ObjectiveCard}.
     * @param commonObjectiveCard2 The second common {@link ObjectiveCard}.
     */
    public void show_commonObjectives(ObjectiveCard commonObjectiveCard1, ObjectiveCard commonObjectiveCard2) {
    }

    /**
     * Displays the objective card for the current player.
     *
     * @param username      The username of the player whose objective card is to be displayed.
     * @param objectiveCard The {@link ObjectiveCard} to be displayed.
     */
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
    }

    public void playerStateInfo(String username, String info) {
    }

    /**
     * Displays the winner of the game.
     * Different for the winner
     *
     * @param username The username of the player who won the game.
     */
    public void showWinner(String username) {
    }

    /**
     * This method is implemented in GameList and MainMenu controllers to just set the player list, in Lobby
     * controller to modify the player list and update the lobby
     * and in InGamePlayer to show disconnected and surrendered players
     */
    public void handleInGamePlayers(LinkedHashMap<String, Boolean> players) {
    }


    /**
     * Handles sending messages and tab completion in a chat text field.
     *
     * <p>This method listens for key events on a text field to handle message sending when the Enter key is pressed
     * and handles tab completion for player names when the Tab key is pressed.</p>
     *
     * <p>If the Enter key is pressed and the text field is not empty, the method checks if the message is directed
     * to a specific player (indicated by a prefix "/playerName"). If it is, the method extracts the player's name
     * and the message, sends the message to the specified player, and updates the text field. If the message is
     * not directed to a specific player, it is sent as a general chat message.</p>
     *
     * <p>If the Tab key is pressed, the method autocompletes the player name based on the prefix entered in the
     * text field. If the prefix matches a player's name, the method updates the text field with the full player
     * name and retains any additional text entered by the user.</p>
     *
     * @param event     The key event that triggers the message send or tab completion.
     * @param textField The text field where the user types the message.
     */
    protected void sendMessage(KeyEvent event, MFXTextField textField) {
        if (event.getCode() == KeyCode.ENTER && !textField.getText().isEmpty()) {
            String toUsername, message;
            for (String player : app.getPlayerList().keySet()) {
                if (textField.getText().startsWith("/" + player)) {
                    toUsername = player;
                    message = textField.getText().substring(player.length() + 1).trim();
                    if (message.isEmpty()) return;
                    sendText(toUsername, message);
                    System.out.println("I sent a message: " + message);
                    textField.setText("/" + player);
                    textField.positionCaret(textField.getText().length());
                    return;
                }
            }

            sendText(textField.getText());
            textField.clear();
        }
        if (event.getCode() == KeyCode.TAB) {
            event.consume();
            String tmpText = textField.getText();

            //If the string has prefix: "/playerName"
            for (String playerName : app.getPlayerList().keySet().stream().toList()) {
                if (textField.getText().startsWith("/" + playerName)) {
                    //tmpText is assigned to the suffix in which there is the text written by hand
                    tmpText = textField.getText().substring(playerName.length() + 1);
                    countPrivateMessage = app.getPlayerList().keySet().stream().toList().indexOf(playerName);
                    break;
                }
            }

            tmpText = tmpText.trim();

            if (countPrivateMessage < app.getPlayerList().size() - 1)
                countPrivateMessage++;
            else countPrivateMessage = 0;

            //If the next player is me do not set suffix "/myName" but return to all chat deleting suffix
            if (!app.getPlayerList().keySet().stream().toList().get(countPrivateMessage).equals(app.getUsername()))
                textField.setText("/" + app.getPlayerList().keySet().stream().toList().get(countPrivateMessage) + " " + tmpText);
            else textField.setText(tmpText);
            textField.positionCaret(textField.getText().length());
        }
    }

    /**
     * Sends a public chat message.
     * If a {@link RemoteException} occurs the default server crash warning is shown
     * and get the exception stack trace
     *
     * @param message The message to send.
     */
    private void sendText(String message) {
        try {
            client.sendChatMessage(client.getUsername(), message);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    /**
     * Sends a private chat message.
     * If a {@link RemoteException} occurs the default server crash warning is shown
     * and get the exception stack trace
     *
     * @param toUsername username of the person to contact with a private message
     * @param message    The message to send.
     */
    private void sendText(String toUsername, String message) {
        try {
            client.sendChatMessage(client.getUsername(), toUsername, message);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    public void showCountDown(Integer secondsLeft) {
    }

    /**
     * Method to differentiate the loading of inGameScene from conventional ways
     * to the reconnection way
     */
    public void playerRejoined() {
    }

    public void showPing() {
    }
}