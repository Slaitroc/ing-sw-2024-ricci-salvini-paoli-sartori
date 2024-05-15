package it.polimi.ingsw.gc31.view.interfaces;

import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.List;

public interface ShowUpdate extends Remote {

    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException;

    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException;

    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException;

    public void show_starterCard(String starterCard) throws RemoteException;

    public void show_objectiveCard(String objectiveCard) throws RemoteException;

    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException;

    public void show_handPlayer(String username, List<PlayableCard> hand) throws RemoteException;

    public void show_scorePlayer(String key, Integer value) throws RemoteException;

}
