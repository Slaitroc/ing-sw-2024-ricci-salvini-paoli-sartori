package it.polimi.ingsw.gc31.view.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class used to populate a TableCell of the TableView in GameListController
 * A single instance of this class will be constructed from a string containing
 * the game ID and #PlayerInGame / #MaxNumberOfPlayer
 */
public class GameInstance {
    private final IntegerProperty gameID;
    private final StringProperty players;

    public GameInstance(Integer gameID, String players) {
        this.gameID = new SimpleIntegerProperty(gameID);
        this.players = new SimpleStringProperty(players);
    }

    public Integer getGameID() {
        return gameID.get();
    }

    public IntegerProperty gameIDProperty() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
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
