package it.polimi.ingsw.gc31.view.tui;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;

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
    private volatile boolean isStateChanged = true;

    public void setStateChanged(boolean isStateChanged) {
        this.isStateChanged = isStateChanged;
    }

    private final BufferedReader buffer;
    private String command = null;
    private final BlockingQueue<String> globalCommands;
    private volatile boolean isCommandChanged = false;

    public TUI(TuiState state, ClientCommands client) {
        this.client = client;
        this.state = state;

        buffer = new BufferedReader(new InputStreamReader(System.in));
        globalCommands = new LinkedBlockingQueue<>();

    }

    public TUI(ClientCommands client) {
        this.state = new InitState(this);
        this.client = client;

        buffer = new BufferedReader(new InputStreamReader(System.in));
        globalCommands = new LinkedBlockingQueue<>();
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
        new Thread(this::nonBlockingInputReader).start();
        new Thread(this::processThread).start();
    }

    public void nonBlockingInputReader() {
        try {
            command = "default";
            String input;
            StringBuilder builder = new StringBuilder(50);
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (true) {
                while (buffer.ready()) {
                    char newChar = (char) buffer.read();
                    if (newChar != '\n')
                        builder.append(newChar);
                    else {
                        if (builder.isEmpty()) {
                            System.out.print("> ");
                            continue;
                        }
                        input = builder.toString().trim();
                        synchronized (this) {
                            if (!input.isEmpty()) {
                                command = input;
                                isCommandChanged = true;
                            }
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                        builder = new StringBuilder(50);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processThread() {
        while (true) {
            if (isStateChanged) {
                synchronized (this) {
                    state.command_initial();
                    isStateChanged = false;
                    this.notify();
                }
            }
            if (isCommandChanged) {
                synchronized (this) {
                    execute_command(command);
                    isCommandChanged = false;
                    this.notify();
                }
            }
            if (!globalCommands.isEmpty()) {
                synchronized (this) {
                    List<String> commands = new ArrayList<>();
                    globalCommands.drainTo(commands);
                    for (String command : commands) {
                        execute_command(command);
                    }
                }
            }

        }
    }

    public void execute_command(String command) {

        if (state.commandsMap.containsKey(command)) {
            state.commandsMap.get(command).run();
        } else {
            tuiWrite("Command not recognized");
        }
    }

    // UTLITIES
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
