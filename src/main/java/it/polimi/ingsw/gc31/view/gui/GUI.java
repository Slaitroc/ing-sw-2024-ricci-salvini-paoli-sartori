
package it.polimi.ingsw.gc31.view.gui;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import javafx.application.Platform;
import it.polimi.ingsw.gc31.view.UI;

import java.util.List;

public class GUI extends UI {

    public GUI(ClientCommands client) {
        this.client = client;
    }

    /**
     * This method allows to call a Runnable inside the GUI thread
     * 
     * @param r
     * 
     * @Slaitroc
     */
    public void runInGuiApp(Runnable r) {
        Platform.runLater(r);
    }

    @Override
    protected void uiRunUI() {
        // Application.launch(GUIApplication.class);

        GUIApplication GUIApplication = new GUIApplication();
        GUIApplication.setClient(client);
        GUIApplication.run();
    }

    @Override
    public void show_listGame(List<String> listGame) {

    }

    @Override
    public void show_gameCreated(int gameID) {

    }

    @Override
    public void show_validUsername(String username) {

    }

    @Override
    public void show_wrongUsername(String username) {

    }

    @Override
    public void show_joinedToGame(int id) {

    }

    @Override
    public void show_gameIsFull(int id) {

    }

    @Override
    public void show_readyStatus(boolean status) {

    }

    @Override
    public void show_chatMessage(String username, String message) {

    }

    @Override
    public void show_gameDoesNotExist() {

    }

    @Override
    public void show_wrongGameSize() {

    }

    @Override
    public void updateHand(String username, List<String> hand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateHand'");
    }

    @Override
    public void update_ToPlayingState() {

    }

    // SHOW UPDATE
    @Override
    public void show_scorePlayer(String key, Integer value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_scorePlayer'");
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_goldDeck'");
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_handPlayer'");
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveDeck'");
    }

    @Override
    public void show_starterCard(String starterCard) {

    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_playArea'");
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) {
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
}
