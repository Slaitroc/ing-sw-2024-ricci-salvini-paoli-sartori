package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

public abstract class UI {
    protected VirtualClient client;

    public abstract IController choose_username(VirtualServer server_stub, VirtualClient clients)
            throws RemoteException;

    public abstract void uiRunUI();

    protected abstract void uiOptions();

    protected abstract void uiNicknames();

    protected void runUI() {
        uiRunUI();
    }

    protected void show_Options() {
        uiOptions();
    };

    protected void show_Nickname() {
        uiNicknames();
    };

}
