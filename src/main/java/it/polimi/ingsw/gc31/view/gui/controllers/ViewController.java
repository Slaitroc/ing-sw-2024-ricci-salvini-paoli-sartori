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
     * Handling for Remote Exceptions. Shows a PopUp windows to display error message
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
     * Used generally to display a warning sign.
     *
     * @param message Message to be shown
     */
    public void setMessage(String message) {
    }

    /**
     * Updates the chat with a new message. Color Usernames.
     * ScrollPane is updated to keep the last message visible.
     * Only lobbyScene and inGameScene implements this method
     *
     * @param username The username of the sender.
     * @param message  The message.
     */
    public void updateChat(String username, String message) {
    }

    public void updateChat(String fromUsername, String toUsername, String message) {
    }

    public void showReady(String username, boolean status) {
    }

    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
    }

    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
    }

    public void show_handPlayer(String username, List<PlayableCard> hand) {
    }

    public void show_scorePlayer(LinkedHashMap<String, Integer> scores) {
    }

    public void show_starterCard(String username, PlayableCard starterCard) {
    }

    public void show_playArea(String username, Map<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {
    }

    public void show_chooseObjectiveCard(String username, ObjectiveCard secretObjectiveCard1, ObjectiveCard secretObjectiveCard2) {
    }

    public void show_commonObjectives(ObjectiveCard commonObjectiveCard1, ObjectiveCard commonObjectiveCard2) {
    }

    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
    }

    public void playerStateInfo(String username, String info) {
    }

    public void showWinner(String username) {
    }

    /**
     * This method is implemented in GameList and MainMenu controllers to just set the player list, in Lobby
     * controller to modify the player list and update the lobby
     * and in InGamePlayer to show disconnected and surrendered players
     */
    public void handleInGamePlayers(LinkedHashMap<String, Boolean> players) {
    }

    protected void sendMessage(KeyEvent event, MFXTextField textField){
        if (event.getCode() == KeyCode.ENTER && !textField.getText().isEmpty()) {
            String toUsername, message;
            for(String player : app.getPlayerList().keySet()){
                if (textField.getText().startsWith("/" + player))
                {
                    toUsername = player;
                    message = textField.getText().substring(player.length()+1).trim();
                    if (message.isEmpty()) return;
                    sendText(toUsername, message);
                    System.out.println("I sent a message: " + message);
                    textField.setText("/" +player);
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
            for(String playerName : app.getPlayerList().keySet().stream().toList()){
                if (textField.getText().startsWith("/" + playerName)) {
                    //tmpText is assigned to the suffix in which there is the text written by hand
                    tmpText = textField.getText().substring(playerName.length() + 1);
                    countPrivateMessage = app.getPlayerList().keySet().stream().toList().indexOf(playerName);
                    break;
                }
            }

            tmpText = tmpText.trim();

            if (countPrivateMessage < app.getPlayerList().size()-1)
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
     * Sends a chat message.
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
     *
     * @param toUsername username of the person to contact with a private message
     * @param message The message to send.
     */
    private void sendText(String toUsername, String message) {
        try {
            client.sendChatMessage(client.getUsername(), toUsername, message);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }
}