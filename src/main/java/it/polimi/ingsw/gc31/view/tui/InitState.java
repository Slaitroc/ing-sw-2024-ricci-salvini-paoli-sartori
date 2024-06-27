package it.polimi.ingsw.gc31.view.tui;

import static it.polimi.ingsw.gc31.utility.OurScanner.scanner;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.utility.DV;

public class InitState extends TUIstate {

    /**
     * Constructor for the InitState, calls the initialize method
     * and set the stateName to "Init State"
     *
     * @param tui the TUI instance that will use this state
     */
    protected InitState(TUI tui) {
        this.tui = tui;
        initialize();
        stateName = "Init State";

    }

    /**
     * User-executable commands for the InitState
     * <ul>
     * <li>help - Shows commands info</li>
     * <li>create game - Create a new game</li>
     * <li>show games - Shows all the active games</li>
     * <li>join game - Join an existing game</li>
     * </ul>
     */
    @Override
    protected void initialize() {
        // command's map
        commandsMap = new LinkedHashMap<>();
        commandsMap.put(TUIstateCommands.SHOW_COMMAND_INFO.toString().toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put(TUIstateCommands.CREATE_GAME.toString().toLowerCase(), this::command_createGame);
        commandsMap.put(TUIstateCommands.SHOW_GAMES.toString().toLowerCase(), this::command_showGames);
        commandsMap.put(TUIstateCommands.JOIN_GAME.toString().toLowerCase(), this::command_joinGame);
        commandsMap.put(TUIstateCommands.REFRESH.toString().toLowerCase(), this::command_refresh);
        commandsMap.put(TUIstateCommands.INVALID.toString().toLowerCase(), this::command_invalidCommand);

        // info map
        commandsInfo = new LinkedHashMap<>();

        commandsInfo.put(TUIstateCommands.SHOW_COMMAND_INFO.toString().toLowerCase(), "Shows commands info");
        commandsInfo.put(TUIstateCommands.CREATE_GAME.toString().toLowerCase(), "Create a new game");
        commandsInfo.put(TUIstateCommands.SHOW_GAMES.toString().toLowerCase(), "Shows all the active games");
        commandsInfo.put(TUIstateCommands.JOIN_GAME.toString().toLowerCase(), "Join an existing game");
        commandsInfo.put(TUIstateCommands.REFRESH.toString().toLowerCase(), "refresh tui");

    }

    /**
     * Asks the user to type the number of players for the new game and sends the
     * corresponding request to the
     * {@link it.polimi.ingsw.gc31.controller.Controller} (socket) or
     * {@link RmiServer}(rmi).
     * Blocks the cmdLineReaderThread until the Controller or RmiServer responds
     * with {@link ShowUpdate#show_gameCreated(int)}
     * 
     * @see ClientCommands#createGame(int)
     * @see Controller#createGame(String, int)
     */
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

    /**
     * Sends the request to the {@link it.polimi.ingsw.gc31.controller.Controller}
     * to show the list of available
     * games.
     * Blocks the cmdLineReaderThread until the Controller responds with
     * {@link ShowUpdate#show_listGame(java.util.List)}
     * 
     * @see Controller#getGameList(String)
     * @see ClientCommands#getGameList()
     */
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

    /**
     * Sends the request to the {@link it.polimi.ingsw.gc31.controller.Controller}
     * to join the game with the typed
     * gameID.
     * Blocks the cmdLineReaderThread until the Controller responds with
     * {@link ShowUpdate#show_gameIsFull(int)} or
     * {@link ShowUpdate#show_gameDoesNotExist()}
     * 
     * @see ClientCommands#joinGame(int)
     * @see Controller#joinGame(String, int)
     */
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

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_ready() {
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_drawGold() {
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_drawResource() {
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_chooseSecreteObjective() {

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_playStarter() {

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_play() {

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_selectCard() {

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_changeSide() {

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_changeStarterSide() {

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_movePlayArea() {
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_changePlayArea() {

    }

    /**
     * Asks the user to type the username and sends the corresponding request to the
     * {@link it.polimi.ingsw.gc31.controller.Controller}(socket) or
     * {@link RmiServer}(rmi).
     * Blocks the cmdLineReaderThread until the Controller or RmiServer responds
     * with {@link ShowUpdate#show_validUsername(String)} or
     * or {@link ShowUpdate#show_wrongUsername(String)} or
     * {@link ShowUpdate#show_wantReconnect(String)
     * 
     * 
     * @see ClientCommands#setUsernameCall(String)
     */
    @Override
    protected void setUsername() {
        String input;
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

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_quitGame() {
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_refresh() {
        tui.forceRefreshTUI(true);
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
                    tui.getClient().reconnect(false);
                    break;
                } else {
                    tui.printToCmdLineOut("Wrong Input");
                }
            } catch (RemoteException e) {
                e.getStackTrace();
            }
        }
    }

    // @Override
    // protected void reMatch() {

    // }

}
