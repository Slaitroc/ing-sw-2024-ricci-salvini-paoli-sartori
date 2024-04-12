package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.OurScanner;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TUI extends UI {

    private boolean inGame = false;

    private Map<String, Runnable> commandsMap;
    private Map<String, Runnable> gameCommandsMap;
    private Map<String, String> commandsInfo;
    private Map<String, String> gameCommandsInfo;

    public TUI(VirtualClient client) {
        commandsMap = new HashMap<>();
        commandsInfo = new HashMap<>();
        gameCommandsMap = new HashMap<>();
        gameCommandsInfo = new HashMap<>();

        this.client = client;
        initializeCommands();
        initializeCommandsInfo();
    }

    private void tuiWrite(String text) {
        System.out.println(DefaultValues.TUI_TAG + text);
    }

    private void initializeCommandsInfo() {
        commandsInfo.put("create game", "Create a new game");
        commandsInfo.put("show games", "Shows all the active games");
        commandsInfo.put("join game", "Join an existing game");
        commandsInfo.put("ready", "Game chosen and your ready to play");

        gameCommandsInfo.put("show hand", "Shows your cards");
        gameCommandsInfo.put("draw gold", "Draw a gold card");

    }

    private void initializeCommands() {
        commandsMap.put(("create game").toLowerCase(), this::command_createGame);
        commandsMap.put("show games", this::command_showGames);
        commandsMap.put("join game", this::command_joinGame);
        commandsMap.put("ready", this::command_ready);

        // FIX
        commandsMap.put("show hand", this::command_showHand);
        commandsMap.put("draw gold", this::command_drawGold);

        gameCommandsMap.put("show hand", this::command_showHand);
        gameCommandsMap.put("draw gold", this::command_drawGold);

    }

    /* commands */
    private void command_createGame() {
        tuiWrite("Type the number of players for the game:");
        int input = Integer.parseInt(OurScanner.scanner.nextLine());
        try {
            if (client.createGame(input))
                tuiWrite("New game created with ID: " + client.getGameID());
            ;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void command_showGames() {
        List<String> list;
        try {
            list = client.showGames();
            show_gameList(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NoGamesException e) {
            tuiWrite("No games  :'(");
        }

    }

    private void show_gameList(List<String> list) {
        tuiWrite(">>Game List<<");
        for (String string : list) {
            System.out.println(string);
        }
    }

    private void command_joinGame() {
        tuiWrite("Type gameID:");
        int input = Integer.parseInt(OurScanner.scanner.nextLine());
        try {
            client.joinGame(input);
            // TODO runcliinitgame()
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void command_ready() {
        boolean ready = false;
        try {
            ready = client.ready();
            if (ready)
                tuiWrite("U are ready to play!");
            else
                tuiWrite("U are not ready :`(");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /* game commands */
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

    private void command_drawGold() {
        try {
            client.drawGold();
            tuiWrite("Gold card Pescata");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // UI
    public void inputUpdate(String input) {
        Runnable command;
        if (inGame) {
            command = gameCommandsMap.get(input);
        } else
            command = commandsMap.get(input);
        if (command != null)
            command.run();
        else
            tuiWrite("Invalid command");

    }

    @Override
    public void update() {

    }

    @Override
    protected void uiRunUI() {
        show_Options();
        String line = DefaultValues.TUI_START_LINE_SYMBOL;
        String input;
        do {
            do {
                System.out.print(line);
                input = OurScanner.scanner.nextLine();
            } while (input.isEmpty());
            inputUpdate(input);
        } while (!input.equals("quit"));

    }

    @Override
    protected void uiOptions() {
        tuiWrite(">>Commands List<< ");
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
    protected void uiNicknames() {
        tuiWrite("Username -> ");

    }

    @Override
    protected IController uiChooseUsername(VirtualServer server_stub, VirtualClient client) throws RemoteException {
        String message = "Type your username:";
        String input;
        IController c = null;
        do {
            tuiWrite(message);
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

}
