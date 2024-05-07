package it.polimi.ingsw.gc31.view.tui;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.io.PrintStream;
import java.awt.Point;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.rmi.RemoteException;
import java.nio.charset.Charset;
import org.fusesource.jansi.AnsiConsole;

import com.google.gson.reflect.TypeToken;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

public class TUI extends UI {

    // MODIFICABILI
    private static final int CMD_LINE_INITIAL_ROW = 1;
    private static final int CMD_LINE_INITIAL_COLUMN = 1;
    private static final int CMD_LINE_LINES = 10;
    private static final int CMD_LINE_WIDTH = 60;
    private static final int CHAT_BOARD_INITIAL_ROW = 1;
    private static final int CHAT_BOARD_INITIAL_COLUMN = 61;
    private static final int CHAT_BOARD_LINES = 10;
    private static final int CHAT_BOARD_WIDTH = 60;

    // CONSTANTS
    private static final int CMD_LINE_EFFECTIVE_WIDTH = CMD_LINE_WIDTH - 2;
    private static final int CMD_LINE_INPUT_ROW = CMD_LINE_INITIAL_ROW + CMD_LINE_LINES;
    private static final int CMD_LINE_INPUT_COLUMN = CMD_LINE_INITIAL_COLUMN + 1;
    private static final int CMD_LINE_OUT_LINES = CMD_LINE_LINES - 1;
    private static final int CHAT_BOARD_EFFECTIVE_WIDTH = CHAT_BOARD_WIDTH - 2;
    private static final int CHAT_BOARD_INPUT_ROW = CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES;
    private static final int CHAT_BOARD_INPUT_COLUMN = CHAT_BOARD_INITIAL_COLUMN + 1;
    private static final int CHAT_BOARD_OUT_LINES = CHAT_BOARD_LINES - 1;

    // DRAWS
    /**
     * Draws the borders of the chat board
     */
    private void drawChatBorders() {
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW, CHAT_BOARD_INITIAL_COLUMN)
                        .a("┌" + "─".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + "┐"));
        for (int i = 0; i < CHAT_BOARD_LINES; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + 1 + i, CHAT_BOARD_INITIAL_COLUMN)
                            .a("│" + " ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + "│"));
        }
        AnsiConsole.out().print(
                Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES + 1, CHAT_BOARD_INITIAL_COLUMN)
                        .a("└" + "─".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + "┘"));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES + 2, CHAT_BOARD_INITIAL_COLUMN)
                        .fg(YELLOW).a("  __ _ _  _ ___ ").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES + 3, CHAT_BOARD_INITIAL_COLUMN)
                        .fg(YELLOW).a(" / _| U |/ \\_ _|").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES + 4, CHAT_BOARD_INITIAL_COLUMN)
                        .fg(YELLOW).a("( (_|   | o | | ").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES + 5, CHAT_BOARD_INITIAL_COLUMN)
                        .fg(YELLOW).a(" \\__|_n_|_n_|_| ").reset());

    }

    /**
     * Draws the borders of the command line
     */
    private void drawCmdLineBorders() {
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW, CMD_LINE_INITIAL_COLUMN)
                        .a("┌" + "─".repeat(CMD_LINE_EFFECTIVE_WIDTH) + "┐"));
        for (int i = 0; i < CMD_LINE_LINES; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + 1 + i, CMD_LINE_INITIAL_COLUMN)
                            .a("│" + " ".repeat(CMD_LINE_EFFECTIVE_WIDTH) + "│"));
        }
        AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + CMD_LINE_LINES + 1, CMD_LINE_INITIAL_COLUMN)
                .a("└" + "─".repeat(CMD_LINE_EFFECTIVE_WIDTH) + "┘"));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + CMD_LINE_LINES + 2, CMD_LINE_INITIAL_COLUMN)
                        .fg(CYAN).a("  __ _   _ __    _   _ _  _ ___  ").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + CMD_LINE_LINES + 3, CMD_LINE_INITIAL_COLUMN)
                        .fg(CYAN).a(" / _| \\_/ |  \\  | | | | \\| | __|").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + CMD_LINE_LINES + 4, CMD_LINE_INITIAL_COLUMN)
                        .fg(CYAN).a("( (_| \\_/ | o )_| |_| | \\\\ | _|  ").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + CMD_LINE_LINES + 5, CMD_LINE_INITIAL_COLUMN)
                        .fg(CYAN).a(" \\__|_| |_|__/__|___|_|_|\\_|___|").reset());

    }

    /**
     * Draws the title of the game
     */
    private void drawTitle() {
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()).println(ansi().fg(YELLOW).a("""
                        █▀▀█  █▀▀█  █▀▀▄  █▀▀▀ █   █
                        █     █  █  █  █  █▀▀▀ ▀▀▄▀▀
                        █▄▄█  █▄▄█  █▄▄▀  █▄▄▄ █   █
                            """).reset());
    }

    /**
     * State of the TUI (State Design Pattern)
     * <p>
     * This variable is used to manage the available commands in the current state
     */
    private TuiState state;
    /**
     * This volatile boolean variable is used to check if the state has changed
     * <p>
     * Knowing if the state has changed is useful to call the command_initial method
     * of the new state
     */
    private volatile boolean isStateChanged = true;
    /**
     * This variable manage a race condition between the commandLineReader and the
     * commandLineProcess threads. <code>commandLineReader</code> thread must wait
     * for the <code>commandLineProcess</code> thread to be ready to process the
     * input.
     */
    private volatile boolean cmdLineProcessReady = false;
    /**
     * This variable is used to manage the chat board avoiding to update it every
     * time
     */
    private volatile boolean chatNeedsUpdate = false;
    /**
     * This variable is used to manage the chat messages. The ChatReader thread adds
     * new client's messages to this queue.
     * <p>
     * Right now this is the simplest implementation that comes to my mind, but it
     * would be better to use a specific class for the chat messages.
     */
    private final Queue<String> chatMessages;
    /**
     * This variable is used to manage the command line output messages.
     * <p>
     * Instead of printing the messages directly to the system output, each state
     * must add its messages to this queue.
     * <p>
     * The <code>commandLineOut</code> thread
     * will print
     * them to the right position of the console.
     */
    private final Queue<String> cmdLineOut = new ArrayDeque<String>();
    /**
     * This variable is used to manage the global commands.
     * <p>
     * Global commands are commands sent by the server to the
     * client. So they are not typed nor managed by the current state.
     * <p>
     * The <code>commandLineProcess</code> thread reads the messages from this queue
     * and processes
     * them.
     */
    private final BlockingQueue<String> globalCommands;

    /**
     * This variable is used to manage the command line input messages.
     * <p>
     * The <code>commandLineReader</code> thread reads the input from the system
     * input and adds
     * it to this queue.
     * <p>
     * The <code>commandLineProcess</code> thread reads the messages from this
     * queue and processes them.
     */
    private final Queue<String> cmdLineMessages;

    /**
     * This variable is used to manage the current working area of the terminal.
     * <p>
     * 0 = command line
     * <p>
     * 1 = chat
     * <p>
     * 3 = playArea
     */
    private int terminalAreaSelection = 0;
    /**
     * This variable is used to manage the access to the
     * <code>terminalAreaSelection</code> variable
     */
    private Object areaSelectionLock = new Object();

    // GETTERS & SETTERS

    /**
     * @return the ClientCommands client of the TUI
     */
    public ClientCommands getClient() {
        return this.client;
    }

    /**
     * State's command may need to access the TUI to change to a new state
     * 
     * @param state
     */
    public void setState(TuiState state) {
        this.state = state;
        this.isStateChanged = true;
    }

    // credo sia stata utilizzata solo per debugging
    public TUI(TuiState state, ClientCommands client) {
        this.client = client;
        this.state = state;

        globalCommands = new LinkedBlockingQueue<>();

        chatMessages = new ArrayDeque<String>();
        cmdLineMessages = new ArrayDeque<String>();

    }

    public TUI(ClientCommands client) {
        AnsiConsole.systemInstall();

        this.state = new InitState(this);
        this.client = client;

        globalCommands = new LinkedBlockingQueue<>();
        chatMessages = new ArrayDeque<String>();
        cmdLineMessages = new ArrayDeque<String>();
    }

    // UTILITIES

    /**
     * This method is used to move the cursor to the command line input area.
     * <p>
     * It also clears the command line input area and update the
     * <code>terminalAreaSelection</code> variable.
     */
    private void moveCursorToCmdLine() {
        AnsiConsole.out().print(
                Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
        AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a("> "));
        synchronized (areaSelectionLock) {
            terminalAreaSelection = 0;
        }
    }

    /**
     * This method is used to move the cursor to the chat input area.
     * <p>
     * It also clears the chat input area and update the
     * <code>terminalAreaSelection</code> variable.
     */
    private void moveCursorToChatLine() {
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN)
                .a(" ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH)));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN).a("> "));
        synchronized (areaSelectionLock) {
            terminalAreaSelection = 1;
        }
    }

    /**
     * This method is used to move the cursor to the chat input area.
     * <p>
     * It also clears the chat input area and update the
     * <code>terminalAreaSelection</code> variable.
     * 
     * @param prefix the prefix to be printed before the cursor
     */
    private void moveCursorToChatLine(String prefix) {
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN)
                .a(" ".repeat(CHAT_BOARD_WIDTH)));
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN).a(prefix + " "));
        synchronized (areaSelectionLock) {
            terminalAreaSelection = 1;
        }
    }

    /**
     * This method is used to reset the cursor to the right area after updating
     * another area.
     */
    protected void resetCursor() {
        synchronized (areaSelectionLock) {
            if (terminalAreaSelection == 0) {
                moveCursorToCmdLine();
            }
            if (terminalAreaSelection == 1) {
                moveCursorToChatLine();
            }
        }
    }

    /**
     * This method is used to add a message to the <code>commandLineOut</code>
     * thread's queue.
     * <p>
     * State methods must use this method to print their messages.
     * 
     * @param message
     * @param color
     */
    protected void printToCmdLineOut(String message, Ansi.Color color) {
        synchronized (cmdLineOut) {
            cmdLineOut.add(Ansi.ansi().fg(color).a(message).reset().toString());
            cmdLineOut.notifyAll();
        }
    }

    /**
     * This method is used to add a message to the <code>commandLineOut</code>
     * thread's queue.
     * <p>
     * State methods must use this method to print their messages.
     * 
     * @param message
     */
    protected void printToCmdLineOut(String message) {
        synchronized (cmdLineOut) {
            cmdLineOut.add(Ansi.ansi().a(message).reset().toString());
            cmdLineOut.notifyAll();
        }
    }

    // probabilmente non necessario al momento ma potrebbe essere utile in futuro
    protected void printToCmdLineIn(String message) {
        moveCursorToChatLine(message);

    }

    /**
     * Format a String as it is printed from the TUI
     * 
     * @param text
     * @return the formatted String
     */
    protected String tuiWrite(String text) {
        return DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_RESET + text;
    }

    /**
     * Format a String as it is printed from the TUI in green
     * 
     * @param text
     * @return the formatted String
     */
    protected String tuiWriteGreen(String text) {
        return DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_GREEN + text
                + DefaultValues.ANSI_RESET;
    }

    /**
     * Format a String as it is printed from the TUI in purple
     * 
     * @param text
     * @return the formatted String
     */
    protected String tuiWritePurple(String text) {
        return DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_PURPLE + text
                + DefaultValues.ANSI_RESET;
    }

    // THREADS
    /**
     * This method starts the <code>commandLineOut</code> thread.
     * <p>
     * This thread is used to print the command line output messages the right way
     * and in the right position.
     * 
     */
    private void commandLineOut() {
        new Thread(() -> {
            while (true) {
                synchronized (cmdLineOut) {
                    if (cmdLineOut.isEmpty()) {
                        try {
                            drawCmdLineBorders();
                            commandLineReader(); // solo al lancio del thread command line out la lista cmdLineOut è
                                                 // vuota, quindi entra in questo if solo una volta
                            cmdLineOut.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        drawCmdLineBorders();
                        updateCmdLineOut();
                    }
                }
            }
        }).start();
    }

    /**
     * This method starts the <code>commandLineReader</code> thread.
     * <p>
     * This thread is used to read the input from the system input and add it to the
     * <code>cmdLineMessages</code> queue.
     */
    private void commandLineReader() {
        new Thread(() -> {
            commandLineProcess();
            while (!cmdLineProcessReady)
                ;
            Scanner cmdScanner = new Scanner(System.in);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                cmdScanner.close();
            }));
            while (true) {
                synchronized (areaSelectionLock) {
                    // It waits for the command line to be selected to read the input
                    if (!(terminalAreaSelection == 0)) {
                        try {
                            areaSelectionLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                moveCursorToCmdLine();
                String input = "";
                // if a state is running a command, it waits for the command to be finished
                // This is necessary to let each command get its input
                synchronized (state) {
                    input = cmdScanner.nextLine();
                }

                // Sends the input to the command line process thread and waits for the command
                // to be executed
                if (!input.isEmpty()) {
                    synchronized (cmdLineMessages) {
                        cmdLineMessages.add(input.trim());
                        try {
                            cmdLineMessages.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }).start();

    }

    /**
     * This method starts the <code>commandLineProcess</code> thread.
     * <p>
     * This thread is used to process the commands in the
     * <code>cmdLineMessages</code> queue and in the <code>globalCommands</code>
     * queue.
     * <p>
     * If the command is "chat", it moves the cursor to the chat input area.
     */
    private void commandLineProcess() {
        new Thread(() -> {
            while (true) {
                if (isStateChanged) {
                    state.command_initial();
                    isStateChanged = false;
                    cmdLineProcessReady = true;
                }
                if (!cmdLineMessages.isEmpty()) {
                    String cmd = null;
                    synchronized (cmdLineMessages) {
                        cmd = cmdLineMessages.poll();
                    }
                    if (cmd.equals("chat")) {
                        synchronized (areaSelectionLock) {
                            printToCmdLineOut("comando " + cmd + " eseguito", Ansi.Color.CYAN);
                            moveCursorToChatLine();
                            areaSelectionLock.notify();
                        }
                        synchronized (cmdLineMessages) {
                            cmdLineMessages.notify();
                        }
                        continue;
                    } else {
                        if (!globalCommands.isEmpty()) {
                            List<String> commands = new ArrayList<>();
                            globalCommands.drainTo(commands);
                            for (String command : commands) {
                                execute_command(command);
                            }
                        } else {
                            synchronized (state) { // commandLineReader could take control of the input before the
                                                   // command execution... this synchronized should fix the thing
                                synchronized (cmdLineMessages) {
                                    execute_command(cmd);
                                    cmdLineMessages.notify();
                                }
                            }
                        }
                    }

                }
            }
        }).start();

    }

    /**
     * This method starts the <code>chatReader</code> thread.
     * <p>
     * This thread is used to read the input from the system input and add it to the
     * <code>chatMessages</code> queue.
     */
    private void chatReader() {
        new Thread(() -> {
            Scanner chatScanner = new Scanner(System.in);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                chatScanner.close();
            }));
            while (true) {
                synchronized (areaSelectionLock) {
                    if (!(terminalAreaSelection == 1)) {
                        try {
                            areaSelectionLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String input = chatScanner.nextLine();
                if (input.isEmpty()) {
                    continue;
                }
                if (input.equals("ccc")) {
                    synchronized (areaSelectionLock) {
                        moveCursorToCmdLine();
                        areaSelectionLock.notify();
                    }
                } else {
                    chatMessages.add(input.trim());
                    chatNeedsUpdate = true;
                }
            }

        }).start();
    }

    /**
     * This method starts the <code>chatBoard</code> thread.
     * <p>
     * This thread is used to print the chat board messages the right way and in the
     * right position.
     */
    private void chatBoard() {
        new Thread(() -> {
            drawChatBorders();
            while (true) {
                if (chatNeedsUpdate) {
                    drawChatBorders();
                    updateChatBoardOut();
                }
            }
        }).start();
    }

    // THREADS UTILITIES

    /**
     * This method searches for the command in the state's commands map and executes
     * it.
     * <p>
     * If the command is not found, it prints "Command not recognized".
     * 
     * @param command
     */
    private void execute_command(String command) {
        if (state.commandsMap.containsKey(command)) {
            state.commandsMap.get(command).run();
        } else {
            printToCmdLineOut(tuiWrite("Command not recognized"));
        }
    }

    /**
     * This method is used to update the command line output messages.
     * <p>
     * It prints the messages in the right position of the console.
     * <p>
     * It also manages the queue of the messages.
     * 
     */
    private void updateCmdLineOut() {
        synchronized (cmdLineOut) {
            for (int i = 0; i < cmdLineOut.size() && i < CMD_LINE_OUT_LINES; i++) {
                AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW - 1 - i, CMD_LINE_INPUT_COLUMN)
                        .a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
                AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW - 1 - i, CMD_LINE_INPUT_COLUMN + 1)
                        .a(cmdLineOut.toArray()[cmdLineOut.size() - 1 - i]));
            }
            if (cmdLineOut.size() > CMD_LINE_OUT_LINES - 1) {
                int i = cmdLineOut.size() - CMD_LINE_OUT_LINES - 1;
                for (int j = 0; j < i; j++) {
                    cmdLineOut.poll();
                }
            }
            resetCursor();
            try {
                cmdLineOut.notifyAll();
                cmdLineOut.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to update the chat board output messages.
     * <p>
     * It prints the messages in the right position of the console.
     * 
     */
    private void updateChatBoardOut() {
        for (int i = 0; i < chatMessages.size() && i < CHAT_BOARD_OUT_LINES; i++) {
            AnsiConsole.out()
                    .print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW - 1 - i, CHAT_BOARD_INPUT_COLUMN)
                            .a(" ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH)));
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW - 1 - i, CHAT_BOARD_INPUT_COLUMN)
                            .a(chatMessages.toArray()[chatMessages.size() - 1 - i]));
        }
        chatNeedsUpdate = false;
        resetCursor();
    }

    // UTILITIES

    @Override
    protected void uiRunUI() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        AnsiConsole.systemInstall();

        drawTitle();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        commandLineOut();
        chatBoard();
        chatReader();
    }

    // UPDATES FIELDS & METHODS

    @Override
    public void updateToPlayingState() {
        this.state = new PlayingState(this);
        this.isStateChanged = true;
    }

    @Override
    public void updateHand(String username, List<String> hand) {

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
    public void show_listGame(List<String> listGame) throws RemoteException {
        tuiWrite(">>Game List<<");
        for (String string : listGame) {
            System.out.println(string);
        }
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        @SuppressWarnings("unused")
        Map<Point, PlayableCard> pA = gsonTranslater.fromJson(playArea, new TypeToken<Map<Point, PlayableCard>>() {
        }.getType());
    }

    @Override
    public void show_scorePlayer(String key, Integer value) {
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
    }

    @Override
    public void show_handPlayer(String username, List<String> hand) throws RemoteException {
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
    }

}
