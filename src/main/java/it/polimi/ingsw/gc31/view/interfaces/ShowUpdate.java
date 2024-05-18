package it.polimi.ingsw.gc31.view.interfaces;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.util.List;

public interface ShowUpdate {

    public void show_goldDeck(String firstCardDeck, String card1, String card2);

    public void show_resourceDeck(String firstCardDeck, String card1, String card2);

    public void show_objectiveDeck(String firstCardDeck, String card1, String card2);

    public void show_starterCard(String starterCard);

    public void show_objectiveCard(ObjectiveCard objectiveCard);

    public void show_chooseObjectiveCard(ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2);

    public void show_playArea(String username, String playArea, String achievedResources);

    public void show_handPlayer(String username, List<PlayableCard> hand);

    public void show_scorePlayer(String key, Integer value);

    public void show_listGame(List<String> listGame);

    public void show_gameCreated(int gameID);

    public void show_validUsername(String username);

    public void show_wrongUsername(String username);

    public void show_joinedToGame(int id);

    public void show_gameIsFull(int id);

    public void show_readyStatus(boolean status);

}
