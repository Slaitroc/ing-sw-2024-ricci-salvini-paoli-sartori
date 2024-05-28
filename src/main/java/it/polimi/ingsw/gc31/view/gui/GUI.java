
package it.polimi.ingsw.gc31.view.gui;

import java.awt.*;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import javafx.application.Platform;
import it.polimi.ingsw.gc31.view.UI;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GUI extends UI {

    GUIApplication app;

    public GUI(ClientCommands client) {
        this.client = client;
    }

    /**
     * This method allows to call a Runnable inside the GUI thread
     *
     * @param r
     * @Slaitroc
     */
    public void runInGuiApp(Runnable r) {
        Platform.runLater(r);
    }

    @Override
    protected void uiRunUI() {
        // Application.launch(GUIApplication.class);

        app = new GUIApplication();
        app.setClient(client);
        client.setUI(this);
        GUIApplication.run();
    }

    @Override
    public void show_listGame(List<String> listGame) {
        Platform.runLater(() -> {
            app.setListGames(listGame);
            app.loadScene(SceneTag.GAMELIST);
        });
    }

    @Override
    public void show_gameCreated(int gameID) {
        Platform.runLater(() -> {
                app.setCurrentGameID(gameID);
                app.setLobbyWindowSize();
                app.loadScene(SceneTag.LOBBY);
        });
    }

    @Override
    public void show_validUsername(String username) {
        Platform.runLater(() -> {
            app.loadScene(SceneTag.MAINMENU);
        });
    }

    @Override
    public void show_wrongUsername(String username) {
        Platform.runLater(() -> {
            app.getCurrentController().setMessage("Username already taken!");
        });
    }

    @Override
    public void show_joinedToGame(int id, int maxNumberOfPlayers) {
        Platform.runLater(() -> {
            app.setLobbyWindowSize();
            app.setNumberOfPlayers(maxNumberOfPlayers);
            app.setCurrentGameID(id);
            app.loadScene(SceneTag.LOBBY);
        });
    }

    @Override
    public void show_gameIsFull(int id) {
        Platform.runLater(() -> {
            app.getCurrentController().setMessage("Lobby is full!");
        });
    }

    @Override
    public void show_readyStatus(String username,  boolean status) {
        System.out.println("show_readyStatus triggered!!!! VALUES: " + username + " " + status);
        Platform.runLater(() -> {
            app.getCurrentController().showReady(username ,status);
        });
    }

    @Override
    public void show_playerTurn(String username, String info) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateHand'");
    }

    @Override
    public void show_inGamePlayers(LinkedHashMap<String, Boolean> players) {
        Platform.runLater(() -> {
            System.out.println("Show_inGamePlayers triggered!!!!: values" + players);
            app.setPlayerList(players);
            app.getCurrentController().updateLobby();
        });
    }

    @Override
    public void updateHand(String username, List<String> hand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateHand'");
    }

    @Override
    public void update_ToPlayingState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateHand'");
    }

    // SHOW UPDATE
    @Override
    public void show_scorePlayer(String key, Integer value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_scorePlayer'");
    }

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_goldDeck'");
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_handPlayer'");
    }

    @Override
    public void show_objectiveDeck(ObjectiveCard firstCardDeck, ObjectiveCard card1, ObjectiveCard card2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveDeck'");
    }

    @Override
    public void show_starterCard(PlayableCard starterCard) {

    }

    @Override
    public void show_playArea(String username, Map<Point, PlayableCard> playArea, String achievedResources) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_playArea'");
    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_resourceDeck'");
    }

    @Override
    public void show_chooseObjectiveCard(ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_chooseObjectiveCard'");
    }

    @Override
    public void show_objectiveCard(ObjectiveCard objectiveCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveCard'");
    }

    @Override
    public void show_chatMessage(String username, String message) {
        //System.out.println(username + "said:" + message);
        Platform.runLater(() -> {
            app.getCurrentController().updateChat(username, message);
        });
    }

    @Override
    public void show_gameDoesNotExist() {
        Platform.runLater(() -> {
            app.getCurrentController().setMessage("Game does not exist!");
        });
    }

    @Override
    public void show_wrongGameSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_wrongGameSize'");
    }

}


//TODO show_inGamePlayers (when i join a match lobby I want to know the string of player currently in the game)
//TODO show_PlayerJoined (when i am in a match lobby I want to know who entered my lobby)