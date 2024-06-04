package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class GameListenerHandler{
    private final Map<String, Listener> listeners = new HashMap<>();

    private void notifyListeners(String type, GameModel model, String username){
        // TODO gestire quando non c'è il tipo?
        try {
            listeners.get(type).update(model, username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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

    public void notifyPlayAreaListener(GameModel model, String username){
        notifyListeners("playArea", model, username);
    }

    public void notifyHandListener(GameModel model, String username){
        notifyListeners("hand", model, username);
    }

    public void notifyChooseObjectiveListener(GameModel model, String username){
        notifyListeners("chooseObjective", model, username);
    }

    public void notifyStarterCardListener(GameModel model, String username){
        notifyListeners("starterCard", model, username);
    }

    public void notifyTurnListener(GameModel model, String username){
        notifyListeners("turn", model, username);
    }

    public void notifyObjectiveCardListener(GameModel model, String username){
        notifyListeners("objectiveCard", model, username);
    }

    public void notifyGoldDeckListener(GameModel model, String username){
        notifyListeners("goldDeck", model, username);
    }

    public void notifyResourcedDeckListener(GameModel model, String username){
        notifyListeners("resourcedDeck", model, username);
    }

    public void notifyCommonObjectiveCardListener(GameModel model, String username){
        notifyListeners("commonObjectiveCard", model, username);
    }

    public void notifyPlayerScoreListener(GameModel model, String username){
        notifyListeners("playerScore", model, username);
    }

}
