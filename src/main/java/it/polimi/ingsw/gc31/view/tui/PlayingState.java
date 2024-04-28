package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public class PlayingState extends TuiState {

    public PlayingState(TUI tui) {
        this.tui = tui;
        stateName = "Playing State";
        initialize();
    }

    @Override
    protected void run() {
        show_options();
    }

    @Override
    protected void initialize() {
        commandsMap = new HashMap<>();
        commandsMap.put("show hand", this::command_showHand);
        commandsMap.put("draw gold", this::command_drawGold);

        commandsInfo = new HashMap<>();
        commandsInfo.put("show hand", "Shows your cards");
        commandsInfo.put("draw gold", "Draw a gold card");
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
    }

    @Override
    protected void command_showHand() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'command_showHand'");
    }

    @Override
    protected void command_drawGold() {
        try {
            tui.getClient().drawGold();
            tuiWrite("Gold card draw");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
