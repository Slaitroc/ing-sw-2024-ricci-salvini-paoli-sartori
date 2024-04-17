package it.polimi.ingsw.gc31.view.GUI.controllers;

import it.polimi.ingsw.gc31.view.GUI.GUIApplication;

public abstract class ViewController {

    protected GUIApplication app;

    protected abstract void initialize();

    public void setGUIApplication(GUIApplication app) {
        this.app = app;
    }
}
