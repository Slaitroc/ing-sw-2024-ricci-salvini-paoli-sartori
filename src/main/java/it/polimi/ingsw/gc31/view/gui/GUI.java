package it.polimi.ingsw.gc31.view.gui;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import javafx.application.Platform;
import it.polimi.ingsw.gc31.view.UI;

import java.util.List;

public class GUI extends UI {

    public GUI(ClientCommands client) {
        this.client = client;
    }

    /**
     * This method allows to call a Runnable inside the GUI thread
     * 
     * @param r
     * 
     * @Slaitroc
     */
    public void runInGuiApp(Runnable r) {
        Platform.runLater(r);
    }

    @Override
    protected void uiRunUI() {

    }

    @Override
    public void setQuitRun(boolean bool) throws RemoteException {
        throw new UnsupportedOperationException("Unimplemented method 'setQuitRun'");
    }

    @Override
    public void showHand(List<String> hand) throws RemoteException {

    }

    @Override
    public void showListGame(List<String> listGame) throws RemoteException {

    }

    @Override
    public void showMessage(String msg) throws RemoteException {

    }

    @Override
    public void show_gameCreated() {
        throw new UnsupportedOperationException("Unimplemented method 'show_gameCreated'");
    }
}
