package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public class PlayingState extends TuiState {

    public PlayingState(TUI tui) {
        this.tui = tui;
        stateName = "Playing State";
        initialize();
    }

    @Override
    protected void initialize() {
        commandsMap = new LinkedHashMap<>();
        commandsMap.put(("help").toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put("dg", this::command_drawGold);
        commandsMap.put("dr", this::command_drawResource);

        commandsInfo = new LinkedHashMap<>();
        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("dg -> draw gold", "Draw a gold card");
        commandsInfo.put("dr -> draw resource", "Draw a resource card");
    }

    @Override
    protected void command_createGame() {
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
    protected void command_joinGame() {

    }

    @Override
    protected void command_ready() {
    }

    @Override
    protected synchronized void command_drawGold() {
        try {
            tui.getClient().drawGold();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tui.tuiWrite("Which card do you want to draw?");
        try {
            tui.getClient().drawGold();
            tui.tuiWrite("Gold card draw");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void command_drawResource() {
        // TODO qui va aggiunto un metodo che mostri le possibili carte da pescare
        tui.tuiWrite("Which card do you want to draw?");
        try {
            tui.getClient().drawResource();
            tui.tuiWrite("Resource card draw");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void command_initial() {
        command_showCommandsInfo();
    }

}
