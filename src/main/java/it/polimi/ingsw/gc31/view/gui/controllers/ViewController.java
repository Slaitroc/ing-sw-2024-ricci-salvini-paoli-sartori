package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.gui.GUIApplication;
import it.polimi.ingsw.gc31.view.gui.GameInstance;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ViewController {

    protected GUIApplication app;
    protected ClientCommands client;



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
    protected void show_ServerCrashWarning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error 505");
        alert.setHeaderText("Server Error");
        alert.setContentText("Server has crashed. Please restart application");

        alert.showAndWait();
    }

    /**
     * Method necessary to set up UI elements otherwise not
     * editable from the initiate method. Called right after scene is created
     */
    public void setUp(){}

    /**
     * Used generally to display a warning sign.
     * @param message Message to be shown
     */
    public void setMessage(String message){}

    /**
     * Updates the chat with a new message. Color Usernames.
     * ScrollPane is updated to keep the last message visible.
     * Only lobbyScene and inGameScene implements this method
     *
     * @param username The username of the sender.
     * @param message  The message.
     */
    public void updateChat(String username, String message){}
    public void updateLobby(){}
    public void showReady(String username, boolean status){}
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2){}
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2){}
    public void show_handPlayer(String username, List<PlayableCard> hand){}
    public void show_scorePlayer(LinkedHashMap<String, Integer> scores){}
    public void show_starterCard(String username, PlayableCard starterCard) {}
    public void show_playArea(String username, Map<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {}
    public void show_chooseObjectiveCard(String username, ObjectiveCard secretObjectiveCard1, ObjectiveCard secretObjectiveCard2) {}
    public void show_commonObjectives(ObjectiveCard commonObjectiveCard1, ObjectiveCard commonObjectiveCard2) {}
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {}
    public void playerStateInfo(String username, String info) {}
}