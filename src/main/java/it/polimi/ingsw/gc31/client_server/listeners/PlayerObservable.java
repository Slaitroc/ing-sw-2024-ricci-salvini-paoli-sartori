package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import javafx.util.Pair;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This abstract class defines an observable for various aspects of a player's attributes.
 * It provides methods for add, remove and notify listeners about changes of:
 * <ul>
 *     <li>player's hand</li>
 *     <li>starter card</li>
 *     <li>objective card</li>
 *     <li>core</li>
 *     <lI>play area</lI>
 * </ul>
 *
 * @author christian salvini
 */
public abstract class PlayerObservable {
    Observable<Pair<String, List<PlayableCard>>> playerHandListeners = new Observable<>();
    Observable<PlayableCard> playerStarterCardListener = new Observable<>();
    Observable<ObjectiveCard> playerObjectiveCardListener = new Observable<>();
//    Observable<LinkedHashMap<String, Integer>> playerScoreListeners = new Observable<>();
    Observable<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> playAreaListeners = new Observable<>();
    Observable<Pair<ObjectiveCard, ObjectiveCard>> playerChooseObjectiveCardListener = new Observable<>();
    Observable<Pair<String, String>> playerTurnListener = new Observable<>();

    /**
     * Adds a listener for receiving updates of the player's hand.
     *
     * @param listener the listener to be added for player hand updates.
     */

    public void addPlayerHandListener(Listener<Pair<String, List<PlayableCard>>> listener) {
        playerHandListeners.addListener(listener);
    }

    /**
     * Adds a listener for receiving updates of the player's starter card.
     *
     * @param listener the listener to be added for player starter card updates.
     */
    public void addPlayerStarterCardListener(Listener<PlayableCard> listener) {
        playerStarterCardListener.addListener(listener);
    }

    /**
     * Adds a listener for receiving updates of the player's objective card.
     *
     * @param listener the listener to be added for player objective card updates.
     */
    public void addPlayerObjectiveCardListener(Listener<ObjectiveCard> listener) {
        playerObjectiveCardListener.addListener(listener);
    }

//    /**
//     * Adds a listener for receiving updates of the player's score
//     *
//     * @param listener the listener to be added for player score updates.
//     */
//    public void addPlayerScoreListener(Listener<LinkedHashMap<String, Integer>> listener) {
//        playerScoreListeners.addListener(listener);
//    }

    /**
     * Adds a listener for receiving updates of the player's play area
     *
     * @param listener the listener to be added for player play area updates.
     */
    public void addPlayAreaListener(Listener<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> listener) {
        playAreaListeners.addListener(listener);
    }
    public void addPlayerChooseObjectiveCardListener(Listener<Pair<ObjectiveCard, ObjectiveCard>> listener) {
        playerChooseObjectiveCardListener.addListener(listener);
    }
    public void addPlayerTurnListener(Listener<Pair<String, String>> listener) {
        playerTurnListener.addListener(listener);
    }

    // FIXME servono?
//    /**
//     * Removes a listener for receiving updates to the player's hand.
//     *
//     * @param listener the listener to be removed.
//     */
//    public void removePlayerHandListener(Listener<Pair<String, List<PlayableCard>>> listener) {
//        playerHandListeners.removeListener(listener);
//    }
//
//    /**
//     * Removes a listener for receiving updates to the player's starter card.
//     *
//     * @param listener the listener to be removed.
//     */
//    public void removePlayerStarterCardListener(Listener<PlayableCard> listener) {
//        playerStarterCardListener.removeListener(listener);
//    }
//
//    /**
//     * Removes a listener for receiving updates to the player's objective card.
//     *
//     * @param listener the listener to be removed.
//     */
//    public void removePlayerObjectiveCardListener(Listener<ObjectiveCard> listener) {
//        playerObjectiveCardListener.removeListener(listener);
//    }
//
//    /**
//     * Removes a listener for receiving updates to the player's score.
//     *
//     * @param listener the listener to be removed.
//     */
//    public void removePlayerScoreListener(Listener<Pair<String, Integer>> listener) {
//        playerScoreListeners.removeListener(listener);
//    }
//
//    /**
//     * Removes a listener for receiving updates to the player's play area.
//     *
//     * @param listener the listener to be removed.
//     */
//    public void removePlayAreaListener(Listener<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> listener) {
//        playAreaListeners.removeListener(listener);
//    }
//
//    public void removePlayerChooseObjectiveCardListener(Listener<Pair<ObjectiveCard, ObjectiveCard>> listener) {
//        playerChooseObjectiveCardListener.removeListener(listener);
//    }

    /**
     * Notifies all registered listeners about an update to the player's hand.
     *
     * @param data a Pair<String, List<PlayableCard>> object containing the player's name and updated hand.
     */
    public void notifyPlayerHandListener(Pair<String, List<PlayableCard>> data) {
        playerHandListeners.notifyListeners(data);
    }

    /**
     * Notifies all registered listeners about an update to the player's starter card.
     *
     * @param data a Pair<String, List<PlayableCard>> object containing the player's starter card.
     */
    public void notifyPlayerStarterCardListener(PlayableCard data) {
        playerStarterCardListener.notifyListeners(data);
    }

    /**
     * Notifies all registered listeners about an update to the player's objective card.
     *
     * @param data a Pair<String, List<PlayableCard>> object containing the player's objective card.
     */
    public void notifyPlayerObjectiveCardListener(ObjectiveCard data) {
        playerObjectiveCardListener.notifyListeners(data);
    }

//    /**
//     * Notifies all registered listeners about an update to the player's score.
//     *
//     * @param data a Pair<String, List<PlayableCard>> object containing the player's score.
//     */
//    public void notifyPlayerScoreListener(LinkedHashMap<String, Integer> data) {
//        playerScoreListeners.notifyListeners(data);
//    }

    /**
     * Notifies all registered listeners about an update to the player's play area.
     *
     * @param data a Pair<String, List<PlayableCard>> object containing the player's play area.
     */
    public void notifyPlayAreaListener(Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>> data) {
        playAreaListeners.notifyListeners(data);
    }

    /**
     *
     * @param data
     */
    public void notifyPlayerChooseObjectiveCardListener(Pair<ObjectiveCard, ObjectiveCard> data) {
        playerChooseObjectiveCardListener.notifyListeners(data);
    }

    /**
     *
     * @param data
     */
    public void notifyPlayerTurnListener(Pair<String, String> data) {
        playerTurnListener.notifyListeners(data);
    }
}
