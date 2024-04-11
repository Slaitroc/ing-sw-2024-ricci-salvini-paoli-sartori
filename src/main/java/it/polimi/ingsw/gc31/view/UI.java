package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

public abstract class UI {
    protected VirtualClient client;

    public IController choose_username(VirtualServer server_stub, VirtualClient clients)
            throws RemoteException {
        return uiChooseUsername(server_stub, clients);
    }

    protected abstract IController uiChooseUsername(VirtualServer server_stub, VirtualClient clients)
            throws RemoteException;

    public void runUI() {
        uiRunUI();
    }

    protected abstract void uiRunUI();

    public void show_Options() {
        uiOptions();
    };

    protected abstract void uiOptions();

    public void show_Nickname() {
        uiNicknames();
    };

    protected abstract void uiNicknames();

    public abstract void update();

    protected void uiUpdate() {
        update();
    }

}
