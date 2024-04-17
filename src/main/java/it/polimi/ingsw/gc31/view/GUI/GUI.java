package it.polimi.ingsw.gc31.view.GUI;

import java.rmi.RemoteException;
import javafx.application.Application;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.view.UI;

import java.rmi.RemoteException;

public class GUI extends UI {

    public GUI(VirtualClient client) {
        this.client = client;
    }

    @Override
    protected void uiRunUI() {
        Application.launch(GUIApplication.class);
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
        return null;
        // FIX la GUI dovrebbe aprirsi qui e permettere al client di scegliere lo
        // username e di conseguenza ricevere il controller dal server. Probabilmente
        // serviranno dei thread per permettere alla GUI di passare dati al main del
        // client
    }

}
