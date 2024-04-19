package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

//NOTE: i metodi che iniziano con ui (uiNomeMetodo()) sono le implementazioni delle classi che ereditano da UI
//gli omonimi senza prefisso ui sono quelli da chiamare nei client
public abstract class UI {
    /**
     * The client interacting with the UI
     * 
     * @Slaitroc
     */
    protected ClientCommands client;
    /**
     * This variable's type could change during the development.
     * Its scope is to track the different section of an UI (Scenes for GUI
     * and
     * views for TUI)
     * Probably it'll become an array of boolean or a Map or IDK
     * 
     * @Slaitroc
     */
    protected boolean inGame;

    // questo metodo viene chiamato nel costruttore delle concretizzazioni di
    // VirtualCLient.
    // il controller viene restituito solo quando il nome viene accettato dal
    // server.
    /**
     * Called in the client constructor, this method, is responsible to load the UI
     * that allows the client to choose its username.
     * 
     * @apiNote override {@link #uiChooseUsername(VirtualServer, VirtualClient)} to
     *          implement this method
     * 
     * @param server_stub : remote server object who checks the client's username
     * @param clients     : the clients to whom, once validated, the username is
     *                    assigned
     * @return the remote Controller
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public IController choose_username(VirtualServer server_stub, VirtualClient clients)
            throws RemoteException {
        return uiChooseUsername(server_stub, clients);
    }

    /**
     * Implementation of {@link #choose_username(VirtualServer, VirtualClient)}
     * 
     * @Slaitroc
     */
    protected abstract IController uiChooseUsername(VirtualServer server_stub, VirtualClient clients)
            throws RemoteException;

    /**
     * Run a new scene/view. Should be using {@link #inGame} to choose between
     * scenes/views.
     * 
     * @apiNote override {@link #uiRunUI()} to implement this method
     * 
     * @Slaitroc
     */
    public void runUI() {
        uiRunUI();
    }

    /**
     * Implementation of {@link #runUI()}
     * 
     * @Slaitroc
     */
    protected abstract void uiRunUI();

    /**
     * Remote controllers may check the state of the clients' UI
     * 
     * @return
     * @throws RemoteException
     * 
     * @see {@link #inGame}
     * @see #isInGame()
     * @see #setQuitRun(boolean)
     * 
     * @Slaitroc
     */
    public abstract boolean isInGame() throws RemoteException;

    /**
     * Remote controller may want to change clients' view/scene
     * 
     * @param inGame
     * 
     * @throws RemoteException
     * 
     * @see {@link #inGame}
     * @see #setQuitRun(boolean)
     * @see #isInGame()
     * 
     * @Slaitroc
     */
    public abstract void setInGame(boolean inGame) throws RemoteException;

    /**
     * Remote controller may want to stop (and typically rerun) the current UI
     * 
     * @param bool
     * 
     * @throws RemoteException
     * 
     * @see {@link #inGame}
     * @see #runUI()
     * @see #isInGame()
     * 
     * @Slaitroc
     */
    public abstract void setQuitRun(boolean bool) throws RemoteException;

    public abstract void showHand(List<String> hand) throws RemoteException;

    public abstract void showListGame(List<String> listGame) throws RemoteException;

    public abstract void showMessage(String msg) throws RemoteException;

    public abstract void show_gameCreated();
}
