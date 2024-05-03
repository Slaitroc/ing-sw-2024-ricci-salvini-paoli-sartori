package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
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
        commandsMap = new LinkedHashMap<>();

        commandsMap.put(("help").toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put(("create game").toLowerCase(), this::command_createGame);
        commandsMap.put("show games", this::command_showGames);
        commandsMap.put("join game", this::command_joinGame);

        // info map
        commandsInfo = new LinkedHashMap<>();

        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("create game", "Create a new game");
        commandsInfo.put("show games", "Shows all the active games");
        commandsInfo.put("join game", "Join an existing game");
    }

    @Override
    protected synchronized void command_createGame() {
        tui.printToCmdLineOut(tui.tuiWrite("Type the number of players for the game:"));
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().createGame(input);
            tui.setState(new JoinedToGameState(tui));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void command_showGames() {
        try {
            tui.getClient().getGameList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoGamesException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void command_joinGame() {
        tui.printToCmdLineOut(tui.tuiWrite("Type gameID:"));
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().joinGame(input);
            tui.setState(new JoinedToGameState(tui));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_ready() {
    }

    @Override
    protected void command_drawGold() {
    }

    private void procedure_setUsername() {
        String message = "Type your username:";
        boolean usernameIsValid = false;
        String input;
        while (!usernameIsValid) {
            tui.printToCmdLineOut(tui.tuiWrite(message));
            input = scanner.nextLine();

            try {
                tui.getClient().setUsername(input.trim());
                usernameIsValid = true;
                tui.printToCmdLineOut(tui.tuiWrite("Your name is: " + input.trim()));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (PlayerNicknameAlreadyExistsException e) {
                message = "Username already exists :,( -> Try another username:";
            }
        }

        tui.getClient().setUI(this.tui);

    }

    @Override
    protected void command_drawResource() {
    }

    @Override
    protected void command_showDrawable() {
    }

    @Override
    protected synchronized void command_initial() {
        procedure_setUsername();
        command_showCommandsInfo();
    }

}
