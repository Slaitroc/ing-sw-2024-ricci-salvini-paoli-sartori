package it.polimi.ingsw.gc31.view.tui;

import java.util.Map;
import it.polimi.ingsw.gc31.view.interfaces.*;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.controller.*;
import it.polimi.ingsw.gc31.client_server.rmi.*;

public abstract class TUIstate {
    /**
     * The name of the current concrete TUIState.
     * Used to check the current state of the TUI.
     */
    protected String stateName;

    /**
     * A map of all user-executable commands for the current concrete TUIState.
     * <p>
     * This map contains commands that the user can execute directly. A command may
     * have an
     * implementation in the concrete TUIState and not be executable by the user;
     * such commands
     * are not included in this map and are invoked only by TUI methods or other
     * TUIState commands.
     * <p>
     * Key: Command tag as a string.
     * Value: The command implementation as a {@link Runnable}.
     */
    protected Map<String, Runnable> commandsMap;
    /**
     * A map of all user-executable commands tags and their short description for
     * the current concrete TUIState.
     * <p>
     * The user can write the command's tag contained in this map to execute the
     * corresponding command
     * <p>
     * Key: Command tag as a string.
     * Value: Command short description.
     */
    protected Map<String, String> commandsInfo;
    // protected ClientCommands client;
    protected TUI tui;

    /**
     * Initializes {@link TUIstate#commandsMap} and {@link TUIstate#commandsInfo}
     * with the user-executable commands and their tag and a short description
     * 
     * @see InitState#initialize()
     * @see JoinedToGameState#initialize()
     * @see PlayingState#initialize()
     */
    protected abstract void initialize();

    /**
     * Prints the {@link TUIstate#commandsInfo} of the concrete TUIstate.
     */
    protected synchronized void command_showCommandsInfo() {
        tui.printToCmdLineOut(tui.tuiWriteGreen(">>Commands List<< "));
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            String command = entry.getKey();
            String description = entry.getValue();
            String formattedLine = String.format("%-20s : %s", command, description);
            tui.printToCmdLineOut(formattedLine);
        }
    }

    /**
     * Sometimes, due to different terminal setup and properties the printed tui
     * could break.
     * This command refresh and prints all the previous printed {@link TUIareas}.
     * 
     * @see TUI#forceRefreshTUI(boolean)
     */
    protected abstract void command_refresh();

    /**
     * Every time the {@link TUI#cmdLineReaderThread} is about to read the new input
     * it
     * waits this method to be called to procede and read the new characters.
     * This allow the TUIcommands to read their own input without concurrency
     * issues.
     * Generally to let the cmdLineReaderThread procede each command should invoke
     * this method at the end of its implementation, however each time a TUIcommand
     * should block the cmdLineReaderThread and wait for a
     * server response must not call stateNotify() that should be called by its
     * corresponding {@link ShowUpdate} method.
     * <p>
     * The correspondence between cmdLineReaderThread blocking and the invocation of
     * stateNotify()
     * must be one-to-one; if the cmdLineReaderThread is blocked and stateNotify()
     * is called twice
     * to unblock it, a deadlock will occur
     * <p>
     * Short sequence diagram verbose description:
     * <ul>
     * <li>A user-executable {@link TUIstateCommands} is read by the
     * cmdLineReaderThread
     * and sent
     * to cmdLineProcessThread by the
     * {@link TUI#commandToProcess(TUIstateCommands, boolean)} method
     * <li>{@link TUI#cmdLineProcessThread} executes the command running the
     * corresponding {@link TUIstate#commandsMap} {@link Runnable}
     * <li>Somewhere in the code a stateNotify() is called (more info in the next
     * lines); thanks to
     * {@link TUI#stateLockQueue} the method waits the cmdLineReaderThread to be
     * about to
     * read the input.
     * <li>cmdLineReaderThread notifies on stateLockQueue that it's about to take
     * the input and waits on {@link TUI#stateLock}.
     * <li>after being notified on stateLockQueue stateNotify() notifies the
     * cmdLineReaderThread on stateLock and unblock it
     * </ul>
     * 
     * <p>
     * stateNotify() can be called by:
     * <ul>
     * <li>a TUIcommands implementation
     * <li>{@link TUI#commandToProcess(TUIstateCommands, boolean)} method with the
     * boolean parameter set to true; Required when the call to stateNotify() is not
     * present in the TUIcommand implementation but is still necessary to unlock the
     * cmdLineReaderThread. commandToProcess(TUIcommands, boolean) is also called by
     * some {@link ShowUpdate} methods.
     * <li>a
     * </ul>
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

    /**
     * If there is non user-executable corresponding to the typed tag by the user
     * this command is called.
     * It prints a message to the user that the command is invalid.
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     */
    protected void command_invalidCommand() {
        tui.printToCmdLineOut(tui.tuiWrite("Invalid command"));
        stateNotify();
    }

    /**
     * Asks the user to type his username and send the corresponding request to the
     * {@link Controller}(socket) or {@link RmiServer}(rmi).
     * 
     * @see InitState#setUsername()
     * @see ClientCommands#setUsernameCall(String)
     */
    protected abstract void setUsername();

    /**
     * Reconnects the client to the server.
     * 
     * @see InitState#reconnect()
     * @see ClientCommands#reconnect()
     */
    protected abstract void reconnect();

    // protected abstract void reMatch();

    /**
     * Asks the user to type the number of players for the new game and sends the
     * corresponding request to the {@link Controller}(socket) or
     * {@link RmiServer}(rmi).
     * 
     * @see InitState#command_createGame()
     * @see ClientCommands#createGame(int)
     */
    protected abstract void command_createGame();

    /**
     * Sends the request to the {@link Controller} to show the list of available
     * games.
     * <p>
     * Blocks the cmdLineReaderThread until the Controller responds with
     * {@link ShowUpdate#show_listGame(java.util.List)}
     * 
     * 
     * @see InitState#command_showGames()
     * @see JoinedToGameState#command_showGames()
     * @see PlayingState#command_showGames()
     * @see ClientCommands#getGameList()
     */
    protected abstract void command_showGames();

    /**
     * Sends the request to the {@link Controller} to join the game with the typed
     * gameID.
     * 
     * @see InitState#command_joinGame()
     * @see ClientCommands#joinGame(int)
     */
    protected abstract void command_joinGame();

    /**
     * Sends the request to the {@link Controller} to quit the game.
     * 
     * @see JoinedToGameState#command_quitGame()
     * @see PlayingState#command_quitGame()
     * @see ClientCommands#quitGame()
     * 
     */
    protected abstract void command_quitGame();

    /**
     * Sends the request to the {@link Controller} to set the client as ready to
     * play.
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * 
     * @see JoinedToGameState#command_ready()
     * @see ClientCommands#setReady(boolean)
     */
    protected abstract void command_ready();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_drawGold();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_drawResource();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_chooseSecreteObjective();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_playStarter();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_play();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_selectCard();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_changeSide();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_changeStarterSide();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_movePlayArea();

    /**
     * Unimplemented method in this TUIstate
     */
    protected abstract void command_changePlayArea();
}
