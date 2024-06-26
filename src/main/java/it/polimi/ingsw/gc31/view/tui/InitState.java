package it.polimi.ingsw.gc31.view.tui;

import static it.polimi.ingsw.gc31.utility.OurScanner.scanner;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.NoTokenException;
import it.polimi.ingsw.gc31.utility.DV;

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
        commandsMap.put("invalid", this::command_invalidCommand);

        // info map
        commandsInfo = new LinkedHashMap<>();

        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("create game", "Create a new game");
        commandsInfo.put("show games", "Shows all the active games");
        commandsInfo.put("join game", "Join an existing game");
    }

    @Override
    protected void command_createGame() {
        tui.printToCmdLineOut(tui.tuiWrite("Type the number of players for the game:"));
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().createGame(input);
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
        tui.printToCmdLineOut(tui.tuiWrite("Type gameID:"));
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().joinGame(input);
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

    @Override
    protected void command_drawResource() {
    }

    @Override
    protected void command_chooseSecreteObjective() {

    }

    @Override
    protected void command_playStarter() {

    }

    @Override
    protected void command_play() {

    }

    @Override
    protected void command_selectCard() {

    }

    @Override
    protected void command_changeSide() {

    }

    @Override
    protected void command_changeStarterSide() {

    }

    @Override
    protected void command_movePlayArea() {
    }

    @Override
    protected void command_initial() {
        tui.getClient().setUI(this.tui);
        setUsername();
    }

    @Override
    protected void setUsername() {
        String input;
        ClientCommands client = tui.getClient();

        if (tui.getClient().getToken().getToken() != DV.defaultToken) {
            try {
                tui.getClient().setUsernameCall(null);
            } catch (IOException e) {

            }
        } else {
            String message = "Type your username:";
            tui.printToCmdLineOut(tui.tuiWrite(message));
            tui.moveCursorToCmdLine();
            input = scanner.nextLine();
            try {
                tui.getClient().setUsernameCall(input.trim());
            } catch (IOException f) {
                f.printStackTrace();
            }
        }

    }

    @Override
    protected void command_quitGame() {
    }

    @Override
    protected void command_refresh() {
    }

    @Override
    protected void reconnect() {
        String input;
        tui.printToCmdLineOut(tui.tuiWrite("Welcome back " + tui.getClient().getUsername()));
        tui.printToCmdLineOut(tui.tuiWrite("You disconnected from the last match :,("));
        tui.printToCmdLineOut(tui.tuiWrite("Would u like to rejoin the game? (y/n)"));
        tui.moveCursorToCmdLine();
        input = scanner.nextLine();
        while (true) {
            try {
                if (input.trim().equals("y")) {
                    tui.getClient().reconnect(true);
                    break;
                } else if (input.trim().equals("n")) {
                    // TODO anche qui cose sul token
                    tui.getClient().reconnect(false);
                    break;
                } else {
                    tui.printToCmdLineOut("Wrong Input");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
