package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public class JoinedToGameState extends TUIstate {

    private boolean ready = false;

    /**
     * Constructor for the JoinedToGameState, calls the initialize method
     * and set the stateName to "Joined To Game State"
     * 
     * @param tui the TUI instance that will use this state
     */
    public JoinedToGameState(TUI tui) {
        this.tui = tui;
        initialize();
        stateName = "Joined To Game State";

    }

    /**
     * User-executable commands for the JoinedToGameState
     * <ul>
     * <li>help - Shows commands info</li>
     * <li>ready - your ready to play</li>
     * <li>quit - quit the game</li>
     * <li>ref - refresh</li>
     * </ul>
     */
    @Override
    protected void initialize() {

        // command's map
        commandsMap = new LinkedHashMap<>();

        commandsMap.put(TUIstateCommands.SHOW_GAMES.toString().toLowerCase(), this::command_showGames);
        commandsMap.put(TUIstateCommands.SHOW_COMMAND_INFO.toString().toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put(TUIstateCommands.READY.toString().toLowerCase(), this::command_ready);
        commandsMap.put(TUIstateCommands.QUIT_GAME.toString().toLowerCase(), this::command_quitGame);
        commandsMap.put(TUIstateCommands.INVALID.toString().toLowerCase(), this::command_invalidCommand);
        commandsMap.put(TUIstateCommands.REFRESH.toString().toLowerCase(), this::command_refresh);

        // info map
        commandsInfo = new LinkedHashMap<>();

        commandsInfo.put(TUIstateCommands.SHOW_COMMAND_INFO.toString().toLowerCase(), "Shows commands info");
        commandsInfo.put(TUIstateCommands.READY.toString().toLowerCase(), "your ready to play");
        commandsInfo.put(TUIstateCommands.QUIT_GAME.toString().toLowerCase(), "quit the game");
        commandsInfo.put(TUIstateCommands.REFRESH.toString().toLowerCase(), "refresh tui");

    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_createGame() {
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
        // stateNotify();
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_joinGame() {
    }

    /**
     * Sends the request to the {@link Controller} to quit the game.
     * 
     * @see ClientCommands#quitGame()
     * 
     */
    @Override
    protected void command_quitGame() {
        try {
            tui.getClient().quitGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the request to the {@link Controller} to set the client as ready to
     * play.
     * 
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#setReady(boolean)
     */
    @Override
    protected void command_ready() {
        ClientCommands client = tui.getClient();
        try {
            if (!ready) {
                ready = true;
                tui.printToCmdLineOut(tui.tuiWrite("U are ready to play!"));
                client.setReady(true);
            } else {
                ready = false;
                tui.printToCmdLineOut(tui.tuiWrite("U are not ready :`("));
                client.setReady(false);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        stateNotify();
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
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void setUsername() {
    }

    @Override
    protected void command_refresh() {
        tui.forceRefreshTUI(true);
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void reconnect() {
    }

    // @Override
    // protected void reMatch() {

    // }

}
