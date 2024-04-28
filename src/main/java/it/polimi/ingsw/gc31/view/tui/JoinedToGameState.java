package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.util.HashMap;

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

        tuiWrite("Initialize di JoinGameState");
        // command's map
        commandsMap = new HashMap<>();

        commandsMap.put(("commands info").toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put("ready", this::command_ready);

        // info map
        commandsInfo = new HashMap<>();

        commandsInfo.put("commands info", "Shows this command info");
        commandsInfo.put("ready", "your ready to play");
    }

    @Override
    protected void run() {
        show_options();
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
    protected void command_ready() {
        if (!ready) {
            ready = !ready;
            tuiWrite("U are ready to play!");
            // tui.getClient().setReady();
        } else {
            tuiWrite("U are not ready :`(");
            ready = !ready;
        }
    }

    @Override
    protected void command_showHand() {
    }

    @Override
    protected void command_drawGold() {

    }

}
