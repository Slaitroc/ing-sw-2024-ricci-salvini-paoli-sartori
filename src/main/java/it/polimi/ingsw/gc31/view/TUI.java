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

    private Map<String, Runnable> commandsMap;
    private Map<String, String> commandsInfo;

    public TUI(VirtualClient client) {
        commandsMap = new HashMap<>();
        commandsInfo = new HashMap<>();
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
    }

    private void initializeCommands() {
        commandsMap.put(("create game").toLowerCase(), this::command_createGame);
        commandsMap.put("show games", this::command_showGames);
        commandsMap.put("join game", this::command_joinGame);

    }

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

    public void command_joinGame() {
        tuiWrite("Type gameID:");
        int input = Integer.parseInt(OurScanner.scanner.nextLine());
        try {
            client.joinGame(input);
            // TODO runcliinitgame()
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void inputUpdate(String input) {
        Runnable command = commandsMap.get(input);
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
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
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
