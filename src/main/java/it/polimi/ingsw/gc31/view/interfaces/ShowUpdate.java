package it.polimi.ingsw.gc31.view.interfaces;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ShowUpdate {

    void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2);

    void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2);

    void show_objectiveDeck(ObjectiveCard firstCardDeck, ObjectiveCard card1, ObjectiveCard card2);

    void show_starterCard(PlayableCard starterCard);

    void show_objectiveCard(ObjectiveCard objectiveCard);

    void show_chooseObjectiveCard(ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2);

    void show_playArea(String username, Map<Point, PlayableCard> playArea, String achievedResources);

    void show_handPlayer(String username, List<PlayableCard> hand);

    void show_scorePlayer(LinkedHashMap<String, Integer> scores);

    void show_listGame(List<String> listGame);

    void show_gameCreated(int gameID);

    void show_validUsername(String username);

    void show_wrongUsername(String username);

    void show_joinedToGame(int id, int maxNumberOfPlayers);

    void show_gameIsFull(int id);

    void show_readyStatus(String username, boolean status);

    void show_chatMessage(String username, String message);

    void show_gameDoesNotExist();

    void show_wrongGameSize();

    void show_playerTurn(String username, String info);

    void show_inGamePlayers(LinkedHashMap<String, Boolean> players);

}
