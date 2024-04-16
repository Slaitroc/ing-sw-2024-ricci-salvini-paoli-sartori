package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

//NOTE: i metodi che iniziano con ui (uiNomeMetodo()) sono le implementazioni delle classi che ereditano da UI
//gli omonimi senza prefisso ui sono quelli da chiamare nei client
public abstract class UI {
    protected VirtualClient client;
    protected boolean inGame;

    // questo metodo viene chiamato nel costruttore delle concretizzazioni di
    // VirtualCLient.
    // il controller viene restituito solo quando il nome viene accettato dal
    // server.
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
        uiShow_Options();
    };

    protected abstract void uiShow_Options();

    public void show_Nickname() {
        uiShow_Nicknames();
    };

    protected abstract void uiShow_Nicknames();

    public abstract void update();

    protected void uiUpdate() {
        update();
    }

    public abstract boolean isInGame() throws RemoteException;

    public abstract void setInGame(boolean inGame) throws RemoteException;

    public abstract void setQuitRun(boolean bool) throws RemoteException;
}
