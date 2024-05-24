package it.polimi.ingsw.gc31.view.interfaces;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface ShowUpdate {

    void show_goldDeck(String firstCardDeck, String card1, String card2);

    void show_resourceDeck(String firstCardDeck, String card1, String card2);

    void show_objectiveDeck(String firstCardDeck, String card1, String card2);

    void show_starterCard(PlayableCard starterCard);

    void show_objectiveCard(ObjectiveCard objectiveCard);

    void show_chooseObjectiveCard(ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2);

    void show_playArea(String username, Map<Point, PlayableCard> playArea, String achievedResources);

    void show_handPlayer(String username, List<PlayableCard> hand);

    void show_scorePlayer(String key, Integer value);

    void show_listGame(List<String> listGame);

    void show_gameCreated(int gameID);

    void show_validUsername(String username);

    void show_wrongUsername(String username);

    void show_joinedToGame(int id);

    void show_gameIsFull(int id);

    void show_readyStatus(boolean status);

    void show_chatMessage(String username, String message);

    void show_gameDoesNotExist();

    void show_wrongGameSize();

    void show_playerTurn(String username, String info);

    void show_inGamePlayers(List<String> players);

}
