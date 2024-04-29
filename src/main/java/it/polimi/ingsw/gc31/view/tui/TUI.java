package it.polimi.ingsw.gc31.view.tui;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.view.tui.tuiObj.CardTUI;
import static it.polimi.ingsw.gc31.OurScanner.scanner;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TUI extends UI {

    private TuiState state;
    private boolean isStateChanged = true;

    public void setStateChanged(boolean isStateChanged) {
        this.isStateChanged = isStateChanged;
    }

    private final BlockingQueue<String> messageQueue;

    private volatile boolean isRunning = false;

    public TUI(TuiState state, ClientCommands client) {
        this.client = client;
        this.state = state;
        messageQueue = new LinkedBlockingQueue<>();

    }

    public TUI(ClientCommands client) {
        this.state = new InitState(this);
        this.client = client;
        messageQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Simply calls System.out.println adding the server tag and printing the text
     * 
     * @param text
     * 
     * @Slaitroc
     */
    protected void tuiWrite(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_RESET + text);
    }

    protected void tuiWriteGreen(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_GREEN + text
                + DefaultValues.ANSI_RESET);
    }

    protected void tuiWritePurple(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_PURPLE + text
                + DefaultValues.ANSI_RESET);
    }

    @Override
    protected void uiRunUI() {
        Thread inputReaderThread = new Thread(this::inputReaderThread);
        inputReaderThread.start();
        Thread executorThread = new Thread(this::executorThread);
        executorThread.start();
    }

    public void inputReaderThread() {
        try {
            while (true) {
                if (isRunning)
                    continue;
                if (isStateChanged) {
                    state.command_initial();
                    isStateChanged = false;
                }
                String input = scanner.nextLine().trim();
                messageQueue.put(input); // Metti l'input nella coda
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void executorThread() {
        while (true) {
            executeCommand();
        }
    }

    private void executeCommand() {
        isRunning = true;
        String action = null;
        synchronized (messageQueue) {
            try {
                action = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (state.commandsMap.containsKey(action))
            state.commandsMap.get(action).run();
        else
            System.out.println("Invalid Command");
        isRunning = false;
    }

    public ClientCommands getClient() {
        return this.client;
    }

    public void setState(TuiState state) {
        this.state = state;
    }

    public TuiState getState() {
        return state;
    }

    // UPDATES

    Map<String, List<PlayableCard>> playersHands = new HashMap<>();

    public List<PlayableCard> getPlayersHands(String username) {
        return playersHands.get(username);
    }

    @Override
    public void showMessage(String msg) throws RemoteException {
    }

    @Override
    public void updateToPlayingState() {
        this.state = new PlayingState(this);
        setStateChanged(true);
    }

    @Override
    public void updateHand(String username, List<String> hand) {
        List<PlayableCard> temp = new ArrayList<>();
        for (String line : hand)
            temp.add(gsonTranslater.fromJson(line, PlayableCard.class));
        playersHands.put(username, temp);

    }

    @Override
    public void show_gameCreated() {
        try {
            tuiWrite("New game created with ID: " + client.getGameID());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * JAVADOC da modificare (non prendetela seriamente)
     * <p>
     * Prints the game list: this method is triggered by the controller.
     * <p>
     * <code>TUI.command_showGames()</code>->
     * <p>
     * <code>controller.getGameList(String username)</code> ->
     * <p>
     * <code>client.shoListGame(List gameList)</code> ->
     * <p>
     * <code>ui.showListGame(List gameList)</code>
     * 
     * @Slaitroc
     */
    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        tuiWrite(">>Game List<<");
        for (String string : listGame) {
            System.out.println(string);
        }
    }

}
