package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public class JoinedToGameState extends TuiState {

    private boolean ready = false;

    public JoinedToGameState(TUI tui) {
        this.tui = tui;
        initialize();
        stateName = "Joined To Game State";

    }

    @Override
    protected void initialize() {

        // command's map
        commandsMap = new LinkedHashMap<>();

        commandsMap.put(("help").toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put("ready", this::command_ready);
        commandsMap.put("quit", this::command_quitGame);
        commandsMap.put("invalid", this::command_invalidCommand);
        commandsMap.put("ref", this::command_refresh);

        // info map
        commandsInfo = new LinkedHashMap<>();

        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("ready", "your ready to play");
        commandsInfo.put("quit", "quit the game");
        commandsInfo.put("ref", "refresh tui");

    }

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
    }

    @Override
    protected void command_joinGame() {
    }

    @Override
    protected void command_quitGame() {
        try {
            tui.getClient().quitGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

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
        command_showCommandsInfo();
    }

    @Override
    protected void setUsername() {
    }

    @Override
    protected void command_refresh() {
        tui.forceRefreshTUI(true);
    }

}
