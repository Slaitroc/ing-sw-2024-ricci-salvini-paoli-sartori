package it.polimi.ingsw.gc31.client_server.listeners;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientSide.ClientQueueObject;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class RmiClientTemp implements VirtualClient {
    private String username;

    RmiClientTemp(String username) {
        this.username = username;
    }

    @Override
    public void setGameID(int gameID) throws RemoteException {

    }

    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand) throws RemoteException {
    }

    @Override
    public void show_scorePlayer(String username, Integer score) throws RemoteException {
        System.out.println("Client of" + this.username);
        System.out.println(username + " score: " + score);
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        System.out.println("Client of" + this.username);
        System.out.println("Starter card: " + starterCard);
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
        System.out.println("Client of" + this.username);
        System.out.println("Objective card: " + objectiveCard);
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        System.out.println("Client of " + this.username);
        System.out.println("player area");
        Map<Point, PlayableCard> playAreaMap = gsonTranslater.fromJson(playArea,
                new TypeToken<Map<Point, PlayableCard>>() {
                }.getType());
        for (Map.Entry<Point, PlayableCard> entry : playAreaMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " +
                    gsonTranslater.toJson(entry.getValue(), PlayableCard.class));
        }
        System.out.println("Achieved resources");
        System.out.println(achievedResources);
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2)
            throws RemoteException {
        System.out.println("Client of" + this.username);
        System.out.println("Gold Deck");
        System.out.println("Card on top: " + firstCardDeck);
        System.out.println("Card1: " + card1);
        System.out.println("Card2: " + card2);
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        System.out.println("Client of" + this.username);
        System.out.println("Resource Deck");
        System.out.println("Card on top: " + firstCardDeck);
        System.out.println("Card1: " + card1);
        System.out.println("Card2: " + card2);
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        System.out.println("Client of" + this.username);
        System.out.println("Objective Deck");
        System.out.println("Card on top: " + firstCardDeck);
        System.out.println("Card1: " + card1);
        System.out.println("Card2: " + card2);
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
    public void showListGame(List<String> listGame) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showListGame'");
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
