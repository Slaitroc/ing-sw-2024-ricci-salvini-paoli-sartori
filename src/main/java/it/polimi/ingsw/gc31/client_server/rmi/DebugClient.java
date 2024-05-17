package it.polimi.ingsw.gc31.client_server.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientSide.ClientQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

public class DebugClient implements VirtualClient, ClientCommands {

    @Override
    public int getGameID() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameID'");
    }

    @Override
    public void setUsername(String username) throws IOException, PlayerNicknameAlreadyExistsException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUsername'");
    }

    @Override
    public void createGame(int maxNumberPlayer) throws RemoteException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createGame'");
    }

    @Override
    public void drawGold() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawGold'");
    }

    @Override
    public void drawGoldCard1() throws RemoteException {

    }

    @Override
    public void drawGoldCard2() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawGoldCard2'");
    }

    @Override
    public void drawResource() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawResource'");
    }

    @Override
    public void drawResourceCard1() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawResourceCard1'");
    }

    @Override
    public void drawResourceCard2() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawResourceCard2'");
    }

    @Override
    public void chooseSecretObjective1() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chooseSecretObjective1'");
    }

    @Override
    public void chooseSecretObjective2() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chooseSecretObjective2'");
    }

    @Override
    public void joinGame(int gameId) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinGame'");
    }

    @Override
    public void getGameList() throws RemoteException, NoGamesException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameList'");
    }

    @Override
    public void setUI(UI ui) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUI'");
    }

    @Override
    public void setReady(boolean ready) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setReady'");
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public void setGameID(int gameID) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setGameID'");
    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_handPlayer'");
    }

    @Override
    public void show_scorePlayer(String username, Integer score) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_scorePlayer'");
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_starterCard'");
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveCard'");
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_playArea'");
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_goldDeck'");
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_resourceDeck'");
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveDeck'");
    }

    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showListGame'");
    }

    @Override
    public boolean isReady() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isReady'");
    }

    @Override
    public void startGame() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public ShowUpdate getUI() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUI'");
    }

    @Override
    public void sendCommand(ClientQueueObject obj) throws RemoteException {

    }

}
