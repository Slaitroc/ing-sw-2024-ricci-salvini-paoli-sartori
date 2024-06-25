package it.polimi.ingsw.gc31.view.gui;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.UI;
import javafx.application.Platform;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GUI extends UI {

    GUIApplication app;

    public GUI(ClientCommands client) {
        this.client = client;
    }

    @Override
    public void runUI() {
        app = new GUIApplication();
        app.setClient(client);
        client.setUI(this);
        GUIApplication.run();
    }

    //Used to Load the GameListScene
    @Override
    public void show_listGame(List<String> listGame) {
        Platform.runLater(() -> {
            app.setListGames(listGame);
            app.loadScene(SceneTag.GAMELIST);
        });
    }

    //Used to Load the LobbyScene
    @Override
    public void show_gameCreated(int gameID) {
        Platform.runLater(() -> {
            app.setCurrentGameID(gameID);
            app.setLobbyWindowSize();
            app.loadScene(SceneTag.LOBBY);
        });
    }

    //Used to Load the MainMenuScene
    @Override
    public void show_validUsername(String username) {
        Platform.runLater(() -> app.loadScene(SceneTag.MAINMENU));
    }

    @Override
    public void show_wrongUsername(String username) {
        Platform.runLater(() -> app.getCurrentController().setMessage("Username already taken!"));
    }

    //Used to Load the LobbyScene
    @Override
    public void show_joinedToGame(int id, int maxNumberOfPlayers) {
        Platform.runLater(() -> {
            app.setNumberOfPlayers(maxNumberOfPlayers);
            app.setCurrentGameID(id);
            app.loadScene(SceneTag.LOBBY);
            app.setLobbyWindowSize();
        });
    }

    @Override
    public void show_gameIsFull(int id) {
        Platform.runLater(() -> app.getCurrentController().setMessage("Lobby is full!"));
    }

    @Override
    public void show_readyStatus(String username, boolean status) {
        // System.out.println("show_readyStatus triggered!!!! VALUES: " + username + " " + status);
        Platform.runLater(() -> app.getCurrentController().showReady(username, status));
    }

    @Override
    public void show_playerTurn(String username, String info) {
        Platform.runLater(() -> app.getCurrentController().playerStateInfo(username, info));
    }

    @Override
    public void show_inGamePlayers(LinkedHashMap<String, Boolean> players) {
        Platform.runLater(() -> {
            System.out.println("show_inGamePlayers triggered!!!!: values" + players);
            app.getCurrentController().handleInGamePlayers(players);
        });
    }

    @Override
    public void show_invalidAction(String message) {
        System.out.println("show_invalidAction called");
    }

    @Override
    public void show_GameIsOver(String username, Map<String, Integer> playersScore) {
        Platform.runLater(() -> app.getCurrentController().showWinner(username));
    }

    @Override
    public void receiveToken(int token) {
        client.setToken(token);
    }

    //Used to Load the InGameScene
    @Override
    public void update_ToPlayingState() {
        Platform.runLater(() -> {
            app.loadScene(SceneTag.GAME);
            app.setFullScreen();
        });
    }

    // SHOW UPDATE

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        Platform.runLater(() -> app.getCurrentController().show_goldDeck(firstCardDeck, card1, card2));
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand, int selectedCard) {
        Platform.runLater(() -> app.getCurrentController().show_handPlayer(username, hand));
    }

    @Override
    public void show_scorePlayer(LinkedHashMap<String, Integer> scores) {

        Platform.runLater(() -> app.getCurrentController().show_scorePlayer(scores));
    }

    //Change Side of the common objective cards if they are sent on the back
    @Override
    public void show_commonObjectiveCard(ObjectiveCard card1, ObjectiveCard card2) {

        Platform.runLater(() -> {
            if (card1 != null && !card1.getSide()) {
                card1.changeSide();
            }
            if (card2 != null && !card2.getSide()) {
                card2.changeSide();
            }
            app.getCurrentController().show_commonObjectives(card1, card2);
        });

    }

    @Override
    public void show_starterCard(String username, PlayableCard starterCard) {
        Platform.runLater(() -> app.getCurrentController().show_starterCard(username, starterCard));
    }

    @Override
    public void show_playArea(String username, LinkedHashMap<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {
        Platform.runLater(() -> app.getCurrentController().show_playArea(username, playArea, achievedResources));
    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        Platform.runLater(() -> app.getCurrentController().show_resourceDeck(firstCardDeck, card1, card2));
    }

    @Override
    public void show_chooseObjectiveCard(String username, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        Platform.runLater(() -> {
            objectiveCard1.changeSide();
            objectiveCard2.changeSide();
            app.getCurrentController().show_chooseObjectiveCard(username, objectiveCard1, objectiveCard2);
        });
    }

    @Override
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
        Platform.runLater(() -> {
            objectiveCard.changeSide();
            app.getCurrentController().show_objectiveCard(username, objectiveCard);
        });
    }

    @Override
    public void show_chatMessage(String username, String message) {
        Platform.runLater(() -> app.getCurrentController().updateChat(username, message));
    }

    @Override
    public void show_privateChatMessage(String fromUsername, String toUsername, String message) {
        Platform.runLater(() -> app.getCurrentController().updateChat(fromUsername, toUsername, message));
    }

    @Override
    public void show_gameDoesNotExist() {
        Platform.runLater(() -> app.getCurrentController().setMessage("Game does not exist!"));
    }

    @Override
    public void show_wrongGameSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("This method should never trigger in the GUI");
    }

    @Override
    public void show_quitFromGame(String username) {
        // TODO Auto-generated method stub
        Platform.runLater(() -> System.out.println("show_quitFromGame called: " + username));
    }

    @Override
    public void show_heartBeat() {
        // TODO Auto-generated method stub
    }

    @Override
    public void show_wantReconnect() {

    }

    @Override
    public void show_rejoined(boolean result) {

    }

    @Override
    public void showGenericClientResonse(String response) {
        // TODO Auto-generated method stub
        System.out.println(response);
    }

}