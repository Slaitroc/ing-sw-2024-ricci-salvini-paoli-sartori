package it.polimi.ingsw.gc31.view.tui;

import java.util.Map;

public abstract class TuiState {
    // debug
    protected String stateName;

    protected Map<String, Runnable> commandsMap;
    protected Map<String, String> commandsInfo;
    // protected ClientCommands client;
    protected TUI tui;

    protected abstract void initialize();

    protected synchronized void command_showCommandsInfo() {
        tui.printToCmdLineOut(tui.tuiWriteGreen(">>Commands List<< "));
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            String command = entry.getKey();
            String description = entry.getValue();
            String formattedLine = String.format("%-20s : %s", command, description);
            tui.printToCmdLineOut(formattedLine);
        }
    }

    protected abstract void command_initial();

    protected abstract void command_createGame();

    protected abstract void command_showGames();

    protected abstract void command_joinGame();

    protected abstract void command_ready();

    protected abstract void command_drawGold();

    protected abstract void command_drawResource();

    protected abstract void command_chooseSecreteObjective();

}
