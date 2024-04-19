package it.polimi.ingsw.gc31.view.GUI;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import javafx.application.Application;
import javafx.application.Platform;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.view.UI;

import java.rmi.RemoteException;
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
    public boolean isInGame() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInGame'");
    }

    @Override
    public void setInGame(boolean inGame) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInGame'");
    }

    @Override
    public void setQuitRun(boolean bool) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setQuitRun'");
    }

    @Override
    protected IController uiChooseUsername(VirtualServer server_stub, VirtualClient clients) throws RemoteException {
        Application.launch(GUIApplication.class);
        System.out.println("CiaoMODNOOOOO");
        return null;
        // FIX la GUI dovrebbe aprirsi qui e permettere al client di scegliere lo
        // username e di conseguenza ricevere il controller dal server. Probabilmente
        // serviranno dei thread per permettere alla GUI di passare dati al main del
        // client
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
}
