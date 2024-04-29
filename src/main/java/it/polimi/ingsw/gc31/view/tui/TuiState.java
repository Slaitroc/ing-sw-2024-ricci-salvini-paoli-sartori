package it.polimi.ingsw.gc31.view.tui;

import java.util.Map;
import it.polimi.ingsw.gc31.DefaultValues;

public abstract class TuiState {
    // debug
    protected String stateName;

    protected Map<String, Runnable> commandsMap;
    protected Map<String, String> commandsInfo;
    // protected ClientCommands client;
    protected TUI tui;

    protected void tuiWrite(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_RESET + text);
    }

    protected void tuiWriteGreen(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_GREEN + text
                + DefaultValues.ANSI_RESET);
    }

    protected void tuiWritePurple(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + DefaultValues.TUI_TAG + DefaultValues.ANSI_PURPLE + text
                + DefaultValues.ANSI_RESET);
    }

    protected abstract void initialize();

    protected void show_options() {
        tuiWriteGreen(">>Commands List<< ");
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            String command = entry.getKey();
            String description = entry.getValue();
            String formattedLine = String.format("%-15s : %s", command, description);
            System.out.println(formattedLine);
        }
    }

    protected void command_showCommandsInfo() {
        show_options();
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
