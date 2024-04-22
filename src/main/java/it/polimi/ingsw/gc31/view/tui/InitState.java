package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import static it.polimi.ingsw.gc31.OurScanner.scanner;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

public class InitState extends TuiState {

    protected InitState(TUI tui) {
        this.tui = tui;
        initialize();
        stateName = "Init State";
    }

    @Override
    protected void initialize() {
        // command's map
        commandsMap = new HashMap<>();

        commandsMap.put(("commands info").toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put(("create game").toLowerCase(), this::command_createGame);
        commandsMap.put("show games", this::command_showGames);
        commandsMap.put("join game", this::command_joinGame);
        commandsMap.put("ready", this::command_ready);

        // info map
        commandsInfo = new HashMap<>();

        commandsInfo.put("commands info", "Shows this command info");
        commandsInfo.put("create game", "Create a new game");
        commandsInfo.put("show games", "Shows all the active games");
        commandsInfo.put("join game", "Join an existing game");
        commandsInfo.put("ready", "Game chosen and your ready to play");
    }

    @Override
    protected void run() {
        procedure_setUsername();
        show_options();
    }

    @Override
    protected void command_createGame() {
        tuiWrite("Type the number of players for the game:");
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().createGame(input);
            tui.setQuitRun(true);
            tui.setState(new JoinedToGameState(tui));
            tui.runUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_showGames() {
        try {
            tui.getClient().getGameList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoGamesException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_joinGame() {
        tuiWrite("Type gameID:");
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().joinGame(input);
            tui.setQuitRun(true);
            tui.setState(new JoinedToGameState(tui));
            tui.runUI();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_ready() {
    }

    @Override
    protected void command_showHand() {
    }

    @Override
    protected void command_drawGold() {
    }

    protected void procedure_setUsername() {
        String message = "Type your username:";
        boolean usernameIsValid = false;
        String input;
        while (!usernameIsValid) {
            tuiWrite(message);
            input = scanner.nextLine();
            try {
                tui.getClient().setUsername(input);
                usernameIsValid = true;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (PlayerNicknameAlreadyExistsException e) {
                message = "Username already exists :,( \n Try another username:";
            }
        }

        tui.getClient().setUI(this.tui);
    }

}
