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

    /**
     * Si assicura che il Reader Thread sia in attesa del termine dell'esecuzione di
     * un comando. Se non lo fosse entrerebbe in attesa di essere risvegliato senza
     * che alcun comando lo possa risvegliare.
     * Questo è necessario per far si che la lettura input del comando e del Reader
     * non entrino in conflitto.
     * <p>
     * Potrebbe essere nella tui ma visto che riguarda principalmente la concorrenza
     * con gli stati per ora lo lascio qui
     * Nota: devo migliorare le java doc---> per ora basta sapere che o il comando o
     * la risposta devono chiamare state.notify perché tutto funzioni correttamente
     */
    protected void stateNotify() {
        synchronized (tui.stateLockQueue) {
            if (tui.stateLockQueue.isEmpty()) {
                try {
                    tui.stateLockQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        tui.removeFromStateLockQueue();
        synchronized (tui.stateLock) {
            tui.stateLock.notify();
        }
    }

    protected void command_invalidCommand() {
        tui.printToCmdLineOut(tui.tuiWrite("Invalid command"));
        stateNotify();
    }

    protected abstract void setUsername();

    protected abstract void command_createGame();

    protected abstract void command_showGames();

    protected abstract void command_joinGame();

    protected abstract void command_ready();

    protected abstract void command_drawGold();

    protected abstract void command_drawResource();

    protected abstract void command_chooseSecreteObjective();

    protected abstract void command_playStarter();

    protected abstract void command_play();

    protected abstract void command_selectCard();

    protected abstract void command_changeSide();

    protected abstract void command_changeStarterSide();

    protected abstract void command_movePlayArea();
}
