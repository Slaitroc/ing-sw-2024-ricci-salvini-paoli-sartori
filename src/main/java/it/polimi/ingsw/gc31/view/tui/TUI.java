package it.polimi.ingsw.gc31.view.tui;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.view.UI;

import java.rmi.RemoteException;
import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TUI extends UI {

    private TuiState state;

    private Thread inputThread;
    private volatile boolean shouldInterrupt = false;

    public void runInputLoop() {
        tuiWriteGreen(state.stateName);
        state.run();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder inputBuilder;
        try {
            System.out.print(DefaultValues.TUI_START_LINE_SYMBOL);
            while (!shouldInterrupt) {
                if (reader.ready()) {
                    inputBuilder = new StringBuilder();
                    while (reader.ready()) {
                        inputBuilder.append((char) reader.read());
                    }
                    String input = inputBuilder.toString().trim();
                    // una volta premuto invio il buffer è vuoto e dunque viene printato l'input
                    if (!input.isEmpty()) {
                        inputUpdate(input);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Eccezione nel run --> Thread Interrotto");
        } finally {
            try {
                reader.close(); // Chiude il BufferedReader
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void runThreads() {
        shouldInterrupt = false;
        inputThread = new Thread(this::runInputLoop);
        inputThread.start();

    }

    public void stopThreads() {
        if (inputThread != null && inputThread.isAlive()) {
            shouldInterrupt = true; // Imposta il flag per interrompere il thread di input
        }
        inputThread.interrupt(); // Interrompe il thread di input
    }

    @Override
    protected void uiRunUI() {
        this.runThreads();
    }

    /**
     * when <code> true </code> stop the current TUI and reset itself to
     * <code> false </code>
     * 
     * @Slaitroc
     */

    /* TUI implementation */
    /**
     * Runs the corresponding Runnable value to the String key in the active command
     * map
     * 
     * @param input key value of the active command map
     * 
     * @Slaitroc
     */
    public void inputUpdate(String input) {
        Runnable command;
        command = state.commandsMap.get(input);
        // verifica se il comando esiste nella lista
        if (command != null)
            command.run();
        if (command == null)
            tuiWrite("Invalid command");
    }

    /**
     * @return the next line input. If {@link #quitRun} is set to
     *         <code>true</code> returns the <code>String</code> value that stops
     *         the TUI.
     * 
     * @Slaitroc
     */

    @Override
    public void showHand(List<String> hand) {
        hand.forEach(x -> System.out.println(x));
    }

    @Override
    public void showMessage(String msg) throws RemoteException {

    }
    /* Fine TUI Implementation */

    /**
     * tracks the current state of the player:
     * <p>
     * <code> true </code> if the player is ready to start the game
     * <p>
     * <code> false </code> otherwise
     * 
     */

    public ClientCommands getClient() {
        return this.client;
    }

    public void setState(TuiState state) {
        this.state = state;
    }

    public TuiState getState() {
        return state;
    }

    public TUI(ClientCommands client) {
        this.state = new InitState(this);
        this.client = client;
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
