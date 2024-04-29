package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.tui.tuiObj.CardTUI;

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
        commandsMap.put("s", this::command_showHand);
        commandsMap.put("dg", this::command_drawGold);
        commandsMap.put("dr", this::command_drawResource);
        commandsMap.put("sd", this::command_showDrawable);

        commandsInfo = new LinkedHashMap<>();
        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("s  -> show hand", "Shows your cards");
        commandsInfo.put("dg -> draw gold", "Draw a gold card");
        commandsInfo.put("dr -> draw resource", "Draw a resource card");
        commandsInfo.put("sd -> show drawable", "Shows the drawable cards");
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
        List<CardTUI> temp = new ArrayList<>();
        for (PlayableCard card : tui.getPlayersHands(tui.getClient().getUsername())) {
            temp.add(new CardTUI(card));
        }
        CardTUI.showHand(temp);
        temp = new ArrayList<>();
        for (PlayableCard card : tui.getPlayersHands(tui.getClient().getUsername())) {
            card.changeSide();
            temp.add(new CardTUI(card));
        }
        CardTUI.showHand(temp);

    }

    @Override
    protected void command_drawGold() {
        try {
            tui.getClient().drawGold();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tuiWrite("Which card do you want to draw?");
        try {
            tui.getClient().drawGold();
            tuiWrite("Gold card draw");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_drawResource() {
        // TODO qui va aggiunto un metodo che mostri le possibili carte da pescare
        tuiWrite("Which card do you want to draw?");
        try {
            tui.getClient().drawResource();
            tuiWrite("Resource card draw");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_showDrawable() {
    }

    @Override
    protected void command_initial() {
        command_showCommandsInfo();
    }

}
