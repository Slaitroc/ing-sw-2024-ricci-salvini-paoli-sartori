package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.listeners.Observable;
import it.polimi.ingsw.gc31.view.gui.GUIApplication;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public abstract class ViewController {

    protected GUIApplication app;
    protected ClientCommands client;



    protected ObservableList<GameInstance> gamesList;

    protected abstract void initialize();

    public void setGUIApplication(GUIApplication app) {
        this.app = app;
    }
    public void setClient(ClientCommands client) {
        this.client = client;
    }
    public ClientCommands getClient() {
        return this.client;
    }

    protected void show_ServerCrashWarning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error 505");
        alert.setHeaderText("Server Error");
        alert.setContentText("Server has crashed. Please restart application");

        alert.showAndWait();
    }
    public void setUp(){};
    public void setMessage(String message){};
    public void updateChat(String username, String message){};
    public void updateLobby(){};
    public void showReady(String username, boolean status){};
}
