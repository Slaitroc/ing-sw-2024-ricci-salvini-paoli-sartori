package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.DefaultValues;
import static it.polimi.ingsw.gc31.OurScanner.scanner;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TUI extends UI {

    /**
     * when <code> true </code> stop the current TUI and reset itself to
     * <code> false </code>
     * 
     * @Slaitroc
     */
    private boolean quitRun = false;
    private boolean usernameSet = false;
    private boolean usernameIsValid = false;
    /**
     * tracks the current state of the player:
     * <p>
     * <code> true </code> if the player is ready to start the game
     * <p>
     * <code> false </code> otherwise
     * 
     */
    private boolean ready = false;

    @Override
    public void setQuitRun(boolean quitRun) throws RemoteException {
        this.quitRun = quitRun;
    }

    @Override
    public boolean isInGame() {
        return inGame;
    }

    @Override
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    private Map<String, Runnable> commandsMap;
    private Map<String, Runnable> gameCommandsMap;
    private Map<String, String> commandsInfo;
    private Map<String, String> gameCommandsInfo;

    public TUI(ClientCommands client) {
        commandsMap = new HashMap<>();
        commandsInfo = new HashMap<>();
        gameCommandsMap = new HashMap<>();
        gameCommandsInfo = new HashMap<>();
        inGame = false;

        this.client = client;
        initializeCommands();
        initializeCommandsInfo();
    }

    /**
     * Simply calls System.out.println adding the server tag and printing the text
     * 
     * @param text
     * 
     * @Slaitroc
     */
    private void tuiWrite(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_RESET + text);
    }

    private void tuiWriteGreen(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_GREEN + text
                + DefaultValues.ANSI_RESET);
    }

    private void tuiWritePurple(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_PURPLE + text
                + DefaultValues.ANSI_RESET);
    }

    /**
     * Initializes all the commands info maps adding the right description for each
     * command
     * 
     * @Slaitroc
     */
    private void initializeCommandsInfo() {
        commandsInfo.put("create game", "Create a new game");
        commandsInfo.put("show games", "Shows all the active games");
        commandsInfo.put("join game", "Join an existing game");
        commandsInfo.put("ready", "Game chosen and your ready to play");

        gameCommandsInfo.put("show hand", "Shows your cards");
        gameCommandsInfo.put("draw gold", "Draw a gold card");

    }

    /**
     * Initializes the command maps adding the right runnable for each command
     * string
     * 
     * @Slaitroc
     */
    private void initializeCommands() {
        commandsMap.put(("create game").toLowerCase(), this::command_createGame);
        commandsMap.put("show games", this::command_showGames);
        commandsMap.put("join game", this::command_joinGame);
        commandsMap.put("ready", this::command_ready);

        gameCommandsMap.put("show hand", this::command_showHand);
        gameCommandsMap.put("draw gold", this::command_drawGold);

    }

    /* commands */
    private void command_setUsername() {
        String message = "Type your username:";
        String input = scanner.nextLine();
        do {
            tuiWrite(message);
            try {
                client.setUsername(input);
                usernameIsValid = true;

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (PlayerNicknameAlreadyExistsException e) {
                message = "Username already exists :,( \n Try another username:";
                e.printStackTrace();

            }
        } while (!usernameIsValid);

        client.setUI(this);
    }

    /**
     * {@link #commandsMap}'s command.
     * <p>
     * Defines the TUI implementation to get the input values needed to create the
     * game.
     * <p>
     * Calls {@link VirtualClient#createGame(int)}
     * 
     * @Slaitroc
     */
    private void command_createGame() {
        tuiWrite("Type the number of players for the game:");
        int input = Integer.parseInt(scanner.nextLine());
        try {
            client.createGame(input);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
     * {@link #commandsMap}'s command.
     * <p>
     * Defines the TUI visualization of the current available games
     * <p>
     * Calls {@link VirtualClient#showGames()}
     * 
     * @Slaitroc
     */
    private void command_showGames() {
        try {
            client.getGameList();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NoGamesException e) {
            e.printStackTrace();
        }

        // List<String> list;
        // try {
        // list = client.getGameList();
        // show_gameList(list);
        // } catch (RemoteException e) {
        // e.printStackTrace();
        // } catch (NoGamesException e) {
        // tuiWrite("No games :'(");
        // }

    }

    /**
     * JAVADOC da modificare
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

    /**
     * {@link #commandsMap}'s command.
     * <p>
     * Defines the TUI implementation to get the inputs needed to join an existing
     * game
     * <p>
     * Calls {@link VirtualClient#joinGame(int)()}
     * 
     * @Slaitroc
     */
    private void command_joinGame() {
        tuiWrite("Type gameID:");
        int input = Integer.parseInt(OurScanner.scanner.nextLine());
        try {
            client.joinGame(input);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link #commandsMap}'s command.
     * <p>
     * Defines the TUI implementation to get the inputs needed to join an existing
     * game
     * <p>
     * Calls {@link VirtualClient#ready()()}
     * 
     * @see #ready
     *
     * @Slaitroc
     */
    private void command_ready() {
        if (!ready) {
            ready = !ready;
            tuiWrite("U are ready to play!");
        } else {
            tuiWrite("U are not ready :`(");
            ready = !ready;
        }
        try {
            client.ready();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /* game commands */
    /**
     * {@link #gameCommandsMap}'s commands
     * <p>
     * Defines the TUI visualization for client's hand
     * <p>
     * Calls {@link VirtualClient#showHand()}
     * 
     * @Slaitroc
     */
    private void command_showHand() {
        List<String> list;
        try {
            list = client.showHand();
            tuiWrite(">>Your Cards<<");
            list.stream().forEach(System.out::println);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link #gameCommandsMap}'s commands
     * <p>
     * Calls {@link VirtualClient#drawGold()()}
     * 
     * @Slaitroc
     */
    private void command_drawGold() {
        try {
            client.drawGold();
            tuiWrite("Gold card Pescata");
        } catch (RemoteException e) {
            e.printStackTrace();
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
            // sceglie la lista di comandi
            if (inGame)
                command = gameCommandsMap.get(input);
            else
                command = commandsMap.get(input);
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
        return OurScanner.scanner.nextLine();
    }

    /**
     * Prints the active command map info's commands
     * 
     * @Slaitroc
     */
    protected void showOptions() {
        tuiWriteGreen(">>Commands List<< ");
        Map<String, String> commands;
        if (!inGame)
            commands = commandsInfo;
        else
            commands = gameCommandsInfo;
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + "\t : \t" + entry.getValue());
        }
    }

    @Override
    protected void uiRunUI() {
        if (!usernameSet)
            command_setUsername();
        showOptions();
        String input;
        do {
            do {
                System.out.print(DefaultValues.TUI_START_LINE_SYMBOL);
                input = getInput();
            } while (input.isEmpty());
            inputUpdate(input);
        } while (!input.equals(DefaultValues.STOP_CURRENT_TUI_STRING));

    }

    @Override
    protected IController uiChooseUsername(VirtualServer server_stub, VirtualClient client) throws RemoteException {
        String message = "Type your username:";
        String input;
        IController c = null;
        do {
            tuiWritePurple(message);
            input = OurScanner.scanner.nextLine();

            try {
                c = server_stub.clientConnection(client, input);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            message = "Username already exists... \nTry a different username:";
        } while (c == null);
        client.setUsername(input);
        return c;
    }

    @Override
    public void showHand(List<String> hand) {
        hand.forEach(x -> System.out.println(x));
    }

    @Override
    public void showMessage(String msg) throws RemoteException {

    }
}
