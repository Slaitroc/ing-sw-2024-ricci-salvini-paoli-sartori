package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import javafx.util.Pair;

import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class PlayerObservable{
    Observable<Pair<String, List<PlayableCard>>> playerHandListeners = new Observable<>();
    Observable<PlayableCard> playerStarterCardListener = new Observable<>();
    Observable<ObjectiveCard> playerObjectiveCardListener = new Observable<>();
    Observable<Pair<String, Integer>> playerScoreListeners = new Observable<>();
    Observable<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> playAreaListeners = new Observable<>();
    public void addPlayerHandListener(Listener<Pair<String, List<PlayableCard>>> listener) {
        playerHandListeners.addListener(listener);
    }
    public void addPlayerStarterCardListener(Listener<PlayableCard> listener) {
        playerStarterCardListener.addListener(listener);
    }
    public void addPlayerObjectiveCardListener(Listener<ObjectiveCard> listener) {
        playerObjectiveCardListener.addListener(listener);
    }
    public void addPlayerScoreListener(Listener<Pair<String, Integer>> listener) {
        playerScoreListeners.addListener(listener);
    }
    public void addPlayAreaListener(Listener<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> listener) {
        playAreaListeners.addListener(listener);
    }

    public void removePlayerHandListener(Listener<Pair<String, List<PlayableCard>>> listener) {
        playerHandListeners.removeListener(listener);
    }
    public void removePlayerStarterCardListener(Listener<PlayableCard> listener) {
        playerStarterCardListener.removeListener(listener);
    }
    public void removePlayerObjectiveCardListener(Listener<ObjectiveCard> listener) {
        playerObjectiveCardListener.removeListener(listener);
    }
    public void removePlayerScoreListener(Listener<Pair<String, Integer>> listener) {
        playerScoreListeners.removeListener(listener);
    }
    public void remvePlayAreaListener(Listener<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> listener) {
        playAreaListeners.removeListener(listener);
    }
    public void notifyPlayerHandListener(Pair<String, List<PlayableCard>> data) {
        playerHandListeners.notifyListeners(data);
    }
    public void notifyPlayerStarterCardListener(PlayableCard data) {
        playerStarterCardListener.notifyListeners(data);
    }
    public void notifyPlayerObjectiveCardListener(ObjectiveCard data) {
        playerObjectiveCardListener.notifyListeners(data);
    }
    public void notifyPlayerScoreListener(Pair<String, Integer> data) {
        playerScoreListeners.notifyListeners(data);
    }
    public void notifyPlayAreaListener(Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>> data) {
        playAreaListeners.notifyListeners(data);
    }
}
