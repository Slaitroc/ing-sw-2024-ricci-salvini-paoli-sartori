package it.polimi.ingsw.gc31.view.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameInstance {
    private final StringProperty gameID;
    private final StringProperty players;

    public GameInstance(String gameID, String players) {
        this.gameID = new SimpleStringProperty(gameID);
        this.players = new SimpleStringProperty(players);
    }

    public String getGameID() {
        return gameID.get();
    }

    public StringProperty gameIDProperty() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID.set(gameID);
    }

    public String getPlayers() {
        return players.get();
    }

    public StringProperty playersProperty() {
        return players;
    }

    public void setPlayers(String players) {
        this.players.set(players);
    }
}
