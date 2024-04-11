package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

public class GUI extends UI {

    public GUI(VirtualClient client) {
        this.client = client;
    }

    @Override
    public void uiRunUI() {
    }

    @Override
    protected void uiOptions() {
    }

    @Override
    protected void uiNicknames() {
    }

    @Override
    public IController choose_username(VirtualServer server_stub, VirtualClient clients) {
        return null;
    }

}
