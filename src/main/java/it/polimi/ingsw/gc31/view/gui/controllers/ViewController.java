package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.view.gui.GUIApplication;

public abstract class ViewController {

    protected GUIApplication app;
    protected ClientCommands client;

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
}
