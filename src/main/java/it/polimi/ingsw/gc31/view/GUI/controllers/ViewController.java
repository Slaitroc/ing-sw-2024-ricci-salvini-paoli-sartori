package it.polimi.ingsw.gc31.view.GUI.controllers;

import it.polimi.ingsw.gc31.view.GUI.GUIApplication;
import javafx.fxml.FXML;

public abstract class ViewController {

    protected GUIApplication app;

    @FXML
    protected abstract void initialize();

    public void setGUIApplication(GUIApplication app) {
        this.app = app;
    }
}
