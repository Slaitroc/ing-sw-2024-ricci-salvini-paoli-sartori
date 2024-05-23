package it.polimi.ingsw.gc31.view.tui;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import com.sun.source.doctree.EscapeTree;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

import static it.polimi.ingsw.gc31.OurScanner.scanner;

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
        commandsMap.put("cs1", this::command_chooseSecreteObjective1);
        commandsMap.put("cs2", this::command_chooseSecreteObjective2);
        commandsMap.put("ps", this::command_playStarter);
        commandsMap.put("p", this::command_play);
        commandsMap.put("invalid", this::command_invalidCommand);

        commandsInfo = new LinkedHashMap<>();
        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("dg -> draw gold", "Draw a gold card");
        commandsInfo.put("dr -> draw resource", "Draw a resource card");
        commandsInfo.put("cs1 ->", "Choose secrete objective 1");
        commandsInfo.put("cs2 ->", "Choose secrete objective 2");
        commandsInfo.put("ps ->", "Play starter card");
        commandsInfo.put("p ->", "Play a card in the play area");
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
    protected void command_drawGold() {
        tui.printToCmdLineOut("Which card do you want to draw?");
        tui.printToCmdLineOut("0 -> from top of the deck");
        tui.printToCmdLineOut("1 -> card 1");
        tui.printToCmdLineOut("2 -> card 2");
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().drawGold(input);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        stateNotify();
    }

    @Override
    protected void command_drawResource() {
        // TODO qui va aggiunto un metodo che mostri le possibili carte da pescare
        try {
            tui.getClient().drawResource();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        stateNotify();
    }

    @Override
    protected void command_chooseSecreteObjective1() {
        try {
            tui.getClient().chooseSecretObjective1();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    @Override
    protected void command_chooseSecreteObjective2() {
        try {
            tui.getClient().chooseSecretObjective2();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    @Override
    protected void command_playStarter() {
        try {
            tui.getClient().playStarter();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    @Override
    protected void command_play() {
        tui.printToCmdLineOut(tui.tuiWrite("Type X coordinate: "));
        int inputX = Integer.parseInt(scanner.nextLine());
        tui.printToCmdLineOut(tui.tuiWrite("Type Y coordinate: "));
        int inputY = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().play(new Point(inputX, inputY));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }


    @Override
    protected void command_initial() {
        command_showCommandsInfo();
    }

    @Override
    protected void setUsername() {
    }

    @Override
    protected void command_invalidCommand() {
        tui.printToCmdLineOut(tui.tuiWrite("Invalid command"));
        stateNotify();
    }

}
