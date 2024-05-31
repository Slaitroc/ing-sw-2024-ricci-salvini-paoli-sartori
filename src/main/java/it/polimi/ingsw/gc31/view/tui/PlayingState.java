package it.polimi.ingsw.gc31.view.tui;

import static it.polimi.ingsw.gc31.utility.OurScanner.scanner;

import java.awt.*;
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
        commandsMap.put("cs1", this::command_chooseSecreteObjective1);
        commandsMap.put("cs2", this::command_chooseSecreteObjective2);
        commandsMap.put("ps", this::command_playStarter);
        commandsMap.put("p", this::command_play);
        commandsMap.put("s", this::command_selectCard);
        commandsMap.put("c", this::command_changeSide);
        commandsMap.put("cs", this::command_changeStarterSide);
        commandsMap.put("invalid", this::command_invalidCommand);

        commandsInfo = new LinkedHashMap<>();
        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("dg -> draw gold", "Draw a gold card");
        commandsInfo.put("dr -> draw resource", "Draw a resource card");
        commandsInfo.put("cs1 ->", "Choose secrete objective 1");
        commandsInfo.put("cs2 ->", "Choose secrete objective 2");
        commandsInfo.put("ps ->", "Play starter card");
        commandsInfo.put("p ->", "Play a card in the play area");
        commandsInfo.put("s ->", "Select a card from hand");
        commandsInfo.put("c ->", "Change side select card");
        commandsInfo.put("cs ->", "Change side starter card");
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
        stateNotify();
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
        tui.printToCmdLineOut("Which card do you want to draw?");
        tui.printToCmdLineOut("0 -> from top of the deck");
        tui.printToCmdLineOut("1 -> card 1");
        tui.printToCmdLineOut("2 -> card 2");
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().drawResource(input);
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
        boolean valid = true;
        int inputX = 0;
        int inputY = 0;
        do {
            valid = true;
            tui.printToCmdLineOut(tui.tuiWrite("Type X coordinate: "));
            try {
                inputX = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                valid = false;
                tui.printToCmdLineOut(tui.tuiWrite("Try again... "));
            }
            tui.printToCmdLineOut(tui.tuiWrite("Type Y coordinate: "));
            try {
                inputY = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                valid = false;
                tui.printToCmdLineOut(tui.tuiWrite("Try again... "));
            }
        } while (!valid);

        try {
            tui.getClient().play(new Point(inputX, inputY));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    @Override
    protected void command_selectCard() {
        tui.printToCmdLineOut(tui.tuiWrite("Type the index of    the card:"));
        int input = Integer.parseInt(scanner.nextLine());
        try {
            tui.getClient().selectCard(input - 1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    @Override
    protected void command_changeSide() {
        try {
            tui.getClient().changeSide();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    @Override
    protected void command_changeStarterSide() {
        try {
            tui.getClient().changeStarterSide();
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
