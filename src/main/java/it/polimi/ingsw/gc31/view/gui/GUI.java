package it.polimi.ingsw.gc31.view.gui;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.UI;
import javafx.application.Platform;
import javafx.util.Pair;

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
        System.out.println("show_listGame called");
        Platform.runLater(() -> {
            app.setListGames(listGame);
            app.loadScene(SceneTag.GAMELIST);
        });
    }

    //Used to Load the LobbyScene
    @Override
    public void show_gameCreated(int gameID) {
        System.out.println("show_gameCreated called");
        Platform.runLater(() -> {
            app.setCurrentGameID(gameID);
            app.setLobbyWindowSize();
            app.loadScene(SceneTag.LOBBY);
        });
    }

    //Used to Load the MainMenuScene
    @Override
    public void show_validUsername(String username) {
        System.out.println("show_validUsername called");
        Platform.runLater(() -> app.loadScene(SceneTag.MAINMENU));
    }

    @Override
    public void show_wrongUsername(String username) {
        System.out.println("show_wrongUsername called");
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
        System.out.println("show_gameIsFull called");
        Platform.runLater(() -> app.getCurrentController().setMessage("Lobby is full!"));
    }

    @Override
    public void show_readyStatus(String username, boolean status) {
        System.out.println("show_readyStatus triggered!!!! VALUES: " + username + " " + status);
        Platform.runLater(() -> app.getCurrentController().showReady(username, status));
    }

    @Override
    public void show_playerTurn(String username, String info) {
        System.out.println("show_playerTurn called");
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
        System.out.println("show_GameIsOver called");
        Platform.runLater(() -> app.getCurrentController().showWinner(username));
    }

    @Override
    public void receiveToken(int token, boolean temporary) {

    }

    //Used to Load the InGameScene
    @Override
    public void update_ToPlayingState() {
        System.out.println("update_ToPlayingState called");
        Platform.runLater(() -> {
            app.loadScene(SceneTag.GAME);
            app.setFullScreen();
        });
    }

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        System.out.println("show_goldDeck called");
        Platform.runLater(() -> app.getCurrentController().show_goldDeck(firstCardDeck, card1, card2));
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand, int selectedCard) {
        System.out.println("show_handPlayer called");
        Platform.runLater(() -> app.getCurrentController().show_handPlayer(username, hand));
    }

    @Override
    public void show_scorePlayer(LinkedHashMap<String, Pair<Integer, Boolean>> scores) {
        System.out.println("show_scorePlayer called");
        Platform.runLater(() -> app.getCurrentController().show_scorePlayer(scores));
    }

    //Change Side of the common objective cards if they are sent on the back
    @Override
    public void show_commonObjectiveCard(ObjectiveCard card1, ObjectiveCard card2) {
        System.out.println("show_commonObjectiveCard called");
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
        System.out.println("show_starterCard called");
        Platform.runLater(() -> app.getCurrentController().show_starterCard(username, starterCard));
    }

    @Override
    public void show_playArea(String username, LinkedHashMap<Point, PlayableCard> playArea, Map<Resources, Integer> achievedResources) {
        System.out.println("show_playArea called");
        Platform.runLater(() -> app.getCurrentController().show_playArea(username, playArea, achievedResources));
    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        System.out.println("show_resourceDeck called");
        Platform.runLater(() -> app.getCurrentController().show_resourceDeck(firstCardDeck, card1, card2));
    }

    @Override
    public void show_chooseObjectiveCard(String username, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        System.out.println("show_chooseObjectiveCard called");
        Platform.runLater(() -> {
            objectiveCard1.changeSide();
            objectiveCard2.changeSide();
            app.getCurrentController().show_chooseObjectiveCard(username, objectiveCard1, objectiveCard2);
        });
    }

    @Override
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
        System.out.println("show_objectiveCard called");
        Platform.runLater(() -> {
            objectiveCard.changeSide();
            app.getCurrentController().show_objectiveCard(username, objectiveCard);
        });
    }

    @Override
    public void show_chatMessage(String username, String message) {
        System.out.println("show_chatMessage called");
        Platform.runLater(() -> app.getCurrentController().updateChat(username, message));
    }

    @Override
    public void show_privateChatMessage(String fromUsername, String toUsername, String message) {
        System.out.println("show_privateChatMessage called");
        Platform.runLater(() -> app.getCurrentController().updateChat(fromUsername, toUsername, message));
    }

    @Override
    public void show_gameDoesNotExist() {
        System.out.println("show_gameDoesNotExist called");
        Platform.runLater(() -> app.getCurrentController().setMessage("Game does not exist!"));
    }

    @Override
    public void show_wrongGameSize() {
        System.out.println("show_wrongGameSize called");
        throw new UnsupportedOperationException("This method should never trigger in the GUI");
    }

    @Override
    public void show_quitFromGame(String username) {
        System.out.println("show_quitFromGame called");
    }

    @Override
    public void show_heartBeat() {
        Platform.runLater(() -> app.getCurrentController().showPing());
        // TODO Auto-generated method stub
    }

    @Override
    public void show_wantReconnect(String username) {

    }

    @Override
    public void show_rejoined(boolean result) {
        System.out.println("show_rejoined called: " + result);
        Platform.runLater(()-> app.getCurrentController().playerRejoined(result));
    }

    /**
     * This method should ask if the player wants to play
     * another match with the same opponents when the current game is finished
     */
    @Override
    public void show_anotherMatch() {
        System.out.println("show_anotherMatch called");
    }

    @Override
    public void show_timerLastPlayerConnected(Integer secondsLeft) {
        System.out.println("show_timerLastPlayerConnected called");
        Platform.runLater(()-> app.getCurrentController().showCountDown(secondsLeft));
    }

    @Override
    public void show_unableToReconnect() {

    }

    @Override
    public void show_GenericClientResonse(String response) {
        // TODO Auto-generated method stub
        System.out.println(response);
    }

}