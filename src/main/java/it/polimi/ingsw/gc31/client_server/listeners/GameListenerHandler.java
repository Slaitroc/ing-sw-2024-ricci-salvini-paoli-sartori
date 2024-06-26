package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.util.HashMap;
import java.util.Map;

public class GameListenerHandler{
    private final Map<String, Listener> listeners = new HashMap<>();
    private final String username;
    private final Object lock;
    private Boolean enabled = true;

    // TODO usare un enumeratore per i tipi di listener invece che mille metodi

    public GameListenerHandler(String username, Object lock) {
        this.username = username;
        this.lock = lock;
    }

    public void notifyAllListeners(GameModel model) {
        if (enabled) {
        synchronized (lock) {
            for (Listener listener : listeners.values()) {
                listener.update(model, username);
            }
        }
        }
    }

    public void setEnabled() {
        enabled = true;
    }

    public void setDisabled() {
        enabled = false;
    }

    private void notifyListener(String type, GameModel model){
        if (enabled) {
            synchronized (lock) {
                if (listeners.containsKey(type)) {
                    listeners.get(type).update(model, username);
                }
            }
        }
    }

    public void addPlayAreaListener(Listener listener){
        listeners.put("playArea", listener);
    }

    public void addHandListener(Listener listener){
        listeners.put("hand", listener);
    }

    public void addChooseObjectiveListener(Listener listener){
        listeners.put("chooseObjective", listener);
    }

    public void addStarterCardListener(Listener listener){
        listeners.put("starterCard", listener);
    }

    public void addTurnListener(Listener listener){
        listeners.put("turn", listener);
    }

    public void addObjectiveCardListener(Listener listener){
        listeners.put("objectiveCard", listener);
    }

    public void addGoldDeckListener(Listener listener){
        listeners.put("goldDeck", listener);
    }

    public void addResourcedDeckListener(Listener listener){
        listeners.put("resourcedDeck", listener);
    }

    public void addCommonObjectiveCardListener(Listener listener){
        listeners.put("commonObjectiveCard", listener);
    }

    public void addPlayerScoreListener(Listener listener){
        listeners.put("playerScore", listener);
    }

    public void removePlayAreaListener(){
        listeners.remove("playArea");
    }

    public void removeHandListener(){
        listeners.remove("hand");
    }

    public void removeChooseObjectiveListener(){
        listeners.remove("chooseObjective");
    }

    public void removeStarterCardListener(){
        listeners.remove("starterCard");
    }

    public void removeTurnListener(){
        listeners.remove("turn");
    }

    public void removeObjectiveCardListener(){
        listeners.remove("objectiveCard");
    }

    public void removeGoldDeckListener(){
        listeners.remove("goldDeck");
    }

    public void removeResourcedDeckListener(){
        listeners.remove("resourcedDeck");
    }

    public void removeCommonObjectiveCardListener(){
        listeners.remove("commonObjectiveCard");
    }

    public void removePlayerScoreListener() {
        listeners.remove("playerScore");
    }

    public void notifyPlayAreaListener(GameModel model){
        notifyListener("playArea", model);
    }

    public void notifyHandListener(GameModel model){
        notifyListener("hand", model);
    }

    public void notifyChooseObjectiveListener(GameModel model){
        notifyListener("chooseObjective", model);
    }

    public void notifyStarterCardListener(GameModel model){
        notifyListener("starterCard", model);
    }

    public void notifyTurnListener(GameModel model){
        notifyListener("turn", model);
    }

    public void notifyObjectiveCardListener(GameModel model){
        notifyListener("objectiveCard", model);
    }

    public void notifyGoldDeckListener(GameModel model){
        notifyListener("goldDeck", model);
    }

    public void notifyResourcedDeckListener(GameModel model){
        notifyListener("resourcedDeck", model);
    }

    public void notifyCommonObjectiveCardListener(GameModel model){
        notifyListener("commonObjectiveCard", model);
    }

    public void notifyPlayerScoreListener(GameModel model){
        notifyListener("playerScore", model);
    }

}
