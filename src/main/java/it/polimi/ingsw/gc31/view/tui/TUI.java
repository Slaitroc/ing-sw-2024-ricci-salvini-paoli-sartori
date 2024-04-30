package it.polimi.ingsw.gc31.view.tui;

import it.polimi.ingsw.gc31.DefaultValues;
import static it.polimi.ingsw.gc31.OurScanner.scanner;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.view.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.List;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.WHITE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;
public class TUI extends UI {

    private Runnable runTUI = this::run;
    private TuiState state;
    /**
     * when <code> true </code> stop the current TUI and reset itself to
     * <code> false </code>
     * 
     * @Slaitroc
     */
    private boolean quitRun = false;

    public void setQuitRun(boolean quitRun) {
        this.quitRun = quitRun;
    }

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
        AnsiConsole.systemInstall();

        this.state = new InitState(this);
        this.client = client;

        System.out.print("\033[H\033[2J");
        System.out.flush();
        print_title();
        print_chat();
        print_playArea();
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
        if (!input.equals(DefaultValues.STOP_CURRENT_TUI_STRING)) {
            command = state.commandsMap.get(input);
            // verifica se il comando esiste nella lista
            if (command != null)
                command.run();
            if (command == null)
                tuiWrite("Invalid command");
        }
    }

    /**
     * @return the next line input. If {@link #quitRun} is set to
     *         <code>true</code> returns the <code>String</code> value that stops
     *         the TUI.
     * 
     * @Slaitroc
     */
    private String getInput() {
        if (quitRun) {
            quitRun = false;
            return DefaultValues.STOP_CURRENT_TUI_STRING;
        }
        return scanner.nextLine();
    }

    public synchronized void run() {
        tuiWriteGreen(state.stateName);
        quitRun = false;
        state.run();
        String input;

        do {
            do {
                System.out.print(DefaultValues.TUI_START_LINE_SYMBOL);
                input = getInput();
            } while (input.isEmpty());
            inputUpdate(input);
        } while (!quitRun);
        tuiWriteGreen("Cambio Stato");

    }

    @Override
    protected void uiRunUI() {
        new Thread(runTUI).start();

    }

    @Override
    public boolean isInGame() throws RemoteException {
        return false;
    }

    @Override
    public void setInGame(boolean inGame) throws RemoteException {

    }

    @Override
    public void showHand(List<String> hand) {
        hand.forEach(x -> System.out.println(x));
    }

    @Override
    public void showMessage(String msg) throws RemoteException {

    }

    protected void set() {

    }

    public void print_title() {
        new PrintStream(System.out, true, System.console() != null
            ? System.console().charset()
            : Charset.defaultCharset()
        ).println(ansi().fg(YELLOW).a("""
            █▀▀█  █▀▀█  █▀▀▄  █▀▀▀ █   █
            █     █  █  █  █  █▀▀▀ ▀▀▄▀▀
            █▄▄█  █▄▄█  █▄▄▀  █▄▄▄ █   █
                """).reset());
    }
    public void print_chat() {
        StringBuilder res = new StringBuilder();

        res.append(ansi().cursor(DefaultValues.row_chat, DefaultValues.col_chat).fg(YELLOW).a("┌──────────────────────────────────┐"));
        res.append(ansi().cursor(DefaultValues.row_chat+1, DefaultValues.col_chat).fg(YELLOW).a("│         Chat                     │"));
        res.append(ansi().cursor(DefaultValues.row_chat+2, DefaultValues.col_chat).fg(YELLOW).a("│                                  │"));
        res.append(ansi().cursor(DefaultValues.row_chat+3, DefaultValues.col_chat).fg(YELLOW).a("│                                  │"));
        
        res.append(ansi().cursor(DefaultValues.row_chat+4, DefaultValues.col_chat).fg(YELLOW).a("│                                  │"));
        
        res.append(ansi().cursor(DefaultValues.row_chat+5, DefaultValues.col_chat).fg(YELLOW).a("│                                  │"));
        res.append(ansi().cursor(DefaultValues.row_chat+6, DefaultValues.col_chat).fg(YELLOW).a("└──────────────────────────────────┘"));
    
        System.out.println(res);
    }

    public void print_playArea() {
        StringBuilder ris = new StringBuilder();
        int heightPlayArea = 15;

        ris.append(ansi().cursor(DefaultValues.row_playArea,DefaultValues.col_playArea).fg(WHITE).a("┌"));
        for (int i=0; i<DefaultValues.col_chat-DefaultValues.col_playArea-3; i++) {
            ris.append(ansi().fg(WHITE).a("─"));
        }
        ris.append(ansi().fg(WHITE).a("┐"));

        for (int i=1; i<heightPlayArea; i++) {
            ris.append(ansi().cursor(DefaultValues.row_playArea+i, DefaultValues.col_playArea).a("│"));
            ris.append(ansi().cursor(DefaultValues.row_playArea+i, DefaultValues.col_chat-DefaultValues.col_playArea-1).a("│"));
        }

        ris.append(ansi().cursor(DefaultValues.row_playArea+heightPlayArea,DefaultValues.col_playArea).fg(WHITE).a("└"));
        for (int i=0; i<DefaultValues.col_chat-DefaultValues.col_playArea-3; i++) {
            ris.append(ansi().fg(WHITE).a("─"));
        }
        ris.append(ansi().fg(WHITE).a("┘"));

        System.out.println(ris);
    } 
}
