package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

public class GUI extends UI {

    public GUI(VirtualClient client) {
        this.client = client;
    }

    @Override
    protected IController uiChooseUsername(VirtualServer server_stub, VirtualClient clients) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uiChooseUsername'");
    }

    @Override
    protected void uiRunUI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uiRunUI'");
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

}
