package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

import java.rmi.RemoteException;

public class GUI extends UI {

    public GUI(VirtualClient client) {
        this.client = client;
    }

    @Override
    protected void uiRunUI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uiRunUI'");
    }

    @Override
    public void update() {

    }

    @Override
    protected void uiShow_Options() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uiShow_Options'");
    }

    @Override
    protected void uiShow_Nicknames() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uiShow_Nicknames'");
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
    }

}
