package it.polimi.ingsw.gc31.view;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;

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
     * Remote controller may want to stop (and typically rerun) the current UI
     * 
     * @param bool
     * 
     * @throws RemoteException
     * @see {@link #inGame}
     * @see #runUI()
     * @see #isInGame()
     * 
     * @Slaitroc
     */
    public abstract void updateHand(String username, List<String> hand) throws RemoteException;

    public abstract void showListGame(List<String> listGame) throws RemoteException;

    public abstract void showMessage(String msg) throws RemoteException;

    public abstract void show_gameCreated();

    public abstract void updateToPlayingState();
}
