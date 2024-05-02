package it.polimi.ingsw.gc31.view.tui;

import java.util.Map;
import it.polimi.ingsw.gc31.DefaultValues;

public abstract class TuiState {
    // debug
    protected String stateName;

    protected volatile boolean isCommandRunning = false;

    protected Map<String, Runnable> commandsMap;
    protected Map<String, String> commandsInfo;
    // protected ClientCommands client;
    protected TUI tui;

    protected String tuiWrite(String text) {
        return DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_RESET + text;
    }

    protected String tuiWriteGreen(String text) {
        return DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_GREEN + text
                + DefaultValues.ANSI_RESET;
    }

    protected String tuiWritePurple(String text) {
        return DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_PURPLE + text
                + DefaultValues.ANSI_RESET;
    }

    protected abstract void initialize();

    protected void show_options() {
        tui.printToCmdLineOut(tuiWriteGreen(">>Commands List<< "));
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            String command = entry.getKey();
            String description = entry.getValue();
            String formattedLine = String.format("%-20s : %s", command, description);
            tui.printToCmdLineOut(formattedLine);
        }
    }

    protected void command_showCommandsInfo() {
        isCommandRunning = true;
        show_options();
        isCommandRunning = false;
    }

    protected abstract void command_initial();

    protected abstract void command_createGame();

    protected abstract void command_showGames();

    protected abstract void command_joinGame();

    protected abstract void command_ready();

    protected abstract void command_showHand();

    protected abstract void command_drawGold();

    protected abstract void command_drawResource();

    protected abstract void command_showDrawable();

}
