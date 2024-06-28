package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

/**
 * Abstract class that represents the UI.
 */
public abstract class UI implements ShowUpdate {
    /**
     * The client interacting with the UI
     */
    protected ClientCommands client;

    /**
     * Run the UI.
     */
    public abstract void runUI();

    /**
     * Generic show method for the UI
     * 
     * @param response the response message to show
     */
    public abstract void show_GenericClientResponse(String response);

}
