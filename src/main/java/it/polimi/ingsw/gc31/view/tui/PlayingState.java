package it.polimi.ingsw.gc31.view.tui;

import static it.polimi.ingsw.gc31.utility.OurScanner.scanner;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

/**
 * TUIstate in which the player can play the running game.
 */
public class PlayingState extends TUIstate {

    /**
     * Constructor for the PlayingState, calls the initialize method
     * and set the stateName to "Playing State"
     * 
     * @param tui the TUI instance that will use this state
     */
    public PlayingState(TUI tui) {
        this.tui = tui;
        stateName = "Playing State";
        initialize();
    }

    /**
     * User-executable commands for the PlayingState
     * <ul>
     * <li>help - Shows commands info</li>
     * <li>quit - quit the game</li>
     * <li>ref - refresh</li>
     * <li>dg - draw gold</li>
     * <li>dr - draw resource</li>
     * <li>co - Choose secrete objective</li>
     * <li>ps - Play starter</li>
     * <li>p - Play</li>
     * <li>s - Select card</li>
     * <li>c - Change side</li>
     * <li>cs - Change starter side</li>
     * <li>mv - Move play area</li>
     * <li>cp - Change play area</li>
     * </ul>
     */
    @Override
    protected void initialize() {
        commandsMap = new LinkedHashMap<>();
        commandsMap.put(TUIstateCommands.SHOW_GAMES.toString().toLowerCase(), this::command_showGames);
        commandsMap.put(TUIstateCommands.SHOW_COMMAND_INFO.toString().toLowerCase(), this::command_showCommandsInfo);
        commandsMap.put(TUIstateCommands.QUIT_GAME.toString().toLowerCase(), this::command_quitGame);
        commandsMap.put(TUIstateCommands.REFRESH.toString().toLowerCase(), this::command_refresh);
        commandsMap.put(TUIstateCommands.DRAW_GOLD.toString().toLowerCase(), this::command_drawGold);
        commandsMap.put(TUIstateCommands.DRAW_RESOURCES.toString().toLowerCase(), this::command_drawResource);
        commandsMap.put(TUIstateCommands.CHOOSE_SECRET_OBJ.toString().toLowerCase(),
                this::command_chooseSecreteObjective);
        commandsMap.put(TUIstateCommands.PLAY_STARTER.toString().toLowerCase(), this::command_playStarter);
        commandsMap.put(TUIstateCommands.PLAY.toString().toLowerCase(), this::command_play);
        commandsMap.put(TUIstateCommands.SELECT_CARD.toString().toLowerCase(), this::command_selectCard);
        commandsMap.put(TUIstateCommands.CHANGE_SIDE.toString().toLowerCase(), this::command_changeSide);
        commandsMap.put(TUIstateCommands.CHANGE_STARTER_SIDE.toString().toLowerCase(), this::command_changeStarterSide);
        commandsMap.put(TUIstateCommands.MOVE_PLAY_AREA.toString().toLowerCase(), this::command_movePlayArea);
        commandsMap.put(TUIstateCommands.CHANGE_PLAY_AREA.toString().toLowerCase(), this::command_changePlayArea);
        commandsMap.put(TUIstateCommands.INVALID.toString().toLowerCase(), this::command_invalidCommand);

        commandsInfo = new LinkedHashMap<>();
        commandsInfo.put(TUIstateCommands.SHOW_COMMAND_INFO.toString().toLowerCase(), "Shows commands info");
        commandsInfo.put(TUIstateCommands.QUIT_GAME.toString().toLowerCase(), "quit the game");
        commandsInfo.put(TUIstateCommands.REFRESH.toString().toLowerCase(), "refresh tui");
        commandsInfo.put(TUIstateCommands.DRAW_GOLD.toString().toLowerCase() + " -------------->", " Draw a gold card");
        commandsInfo.put(TUIstateCommands.DRAW_RESOURCES.toString().toLowerCase() + " -------------->",
                " Draw a resource card");
        commandsInfo.put(TUIstateCommands.CHOOSE_SECRET_OBJ.toString().toLowerCase() + " -------------->",
                " Choose secrete objective");
        commandsInfo.put(TUIstateCommands.PLAY_STARTER.toString().toLowerCase() + " -------------->",
                " Play starter card");
        commandsInfo.put(TUIstateCommands.PLAY.toString().toLowerCase() + "  -------------->",
                " Play a card in the play area");
        commandsInfo.put(TUIstateCommands.SELECT_CARD.toString().toLowerCase() + "  -------------->",
                " Select a card from hand");
        commandsInfo.put(TUIstateCommands.CHANGE_SIDE.toString().toLowerCase() + "  -------------->",
                " Change side select card");
        commandsInfo.put(TUIstateCommands.CHANGE_STARTER_SIDE.toString().toLowerCase() + " -------------->",
                " Change side starter card");
        commandsInfo.put(TUIstateCommands.CHANGE_PLAY_AREA.toString().toLowerCase() + " -------------->",
                " Change play area");
        commandsInfo.put(TUIstateCommands.MOVE_PLAY_AREA.toString().toLowerCase() + " -------------->",
                " Move play area");
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_createGame() {
    }

    /**
     * Sends the request to the {@link it.polimi.ingsw.gc31.controller.Controller}
     * to show the list of available
     * games.
     * Blocks the cmdLineReaderThread until the Controller responds with
     * {@link ShowUpdate#show_listGame(java.util.List)}
     * 
     * @see it.polimi.ingsw.gc31.controller.Controller#getGameList(int) 
     * @see ClientCommands#getGameList()
     */
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

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_joinGame() {
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void command_ready() {
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * to draw a gold card.
     * <p>
     * Asks the user which card he wants to draw:
     * <ul>
     * <li>0 -> from top of the deck</li>
     * <li>1 -> card 1</li>
     * <li>2 -> card 2</li>
     * <li>-1 -> quit command</li>
     * </ul>
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#drawGold(int)
     * @see it.polimi.ingsw.gc31.controller.GameController#drawGold(String, int)
     * 
     * 
     * 
     */
    @Override
    protected void command_drawGold() {
        tui.printToCmdLineOut("Which card do you want to draw?");
        tui.printToCmdLineOut("0 -> from top of the deck");
        tui.printToCmdLineOut("1 -> card 1");
        tui.printToCmdLineOut("2 -> card 2");
        tui.printToCmdLineOut("-1 -> quit command");
        while (true) {
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input == -1) {
                    break;
                } else {

                    if (input == 0 || input == 1 || input == 2) {
                        try {
                            tui.getClient().drawGold(input);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else {
                        tui.printToCmdLineOut("Wrong input!");
                    }
                }
            } catch (NumberFormatException e) {
                tui.printToCmdLineOut("Wrong input!");
            }
        }

        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * draw a resource card.
     * <p>
     * Asks the user which card he wants to draw:
     * <ul>
     * <li>0 -> from top of the deck</li>
     * <li>1 -> card 1</li>
     * <li>2 -> card 2</li>
     * <li>-1 -> quit command</li>
     * </ul>
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#drawResource(int)
     * @see it.polimi.ingsw.gc31.controller.GameController#drawResource(String, int)
     */
    @Override
    protected void command_drawResource() {
        tui.printToCmdLineOut("Which card do you want to draw?");
        tui.printToCmdLineOut("0 -> from top of the deck");
        tui.printToCmdLineOut("1 -> card 1");
        tui.printToCmdLineOut("2 -> card 2");
        tui.printToCmdLineOut("-1 -> quit command");
        while (true) {
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input == -1) {
                    break;
                } else {
                    if (input == 0 || input == 1 || input == 2) {
                        try {
                            tui.getClient().drawResource(input);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else {
                        tui.printToCmdLineOut("Wrong input!");
                    }
                }
            } catch (NumberFormatException e) {
                tui.printToCmdLineOut("Wrong input");
            }
        }
        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * choose a secret objective.
     * <p>
     * Asks the user which card he wants to choose:
     * <ul>
     * <li>1 -> Secret Objective 1</li>
     * <li>2 -> Secret Objective 2</li>
     * <li>-1 -> quit command</li>
     * </ul>
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#chooseSecretObjective1()
     * @see ClientCommands#chooseSecretObjective2()
     * @see it.polimi.ingsw.gc31.controller.GameController#chooseSecretObjective(String, Integer)
     * 
     */
    @Override
    protected void command_chooseSecreteObjective() {
        tui.printToCmdLineOut("Which card do you want to choose?");
        tui.printToCmdLineOut("1 -> Secret Objective 1");
        tui.printToCmdLineOut("2 -> Secret Objective 2");
        tui.printToCmdLineOut("-1 -> quit command");
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.isBlank()) {
                    tui.printToCmdLineOut("Wrong input!");
                    continue;
                }
                int inputInt = Integer.parseInt(input);
                if (inputInt == -1) {
                    break;
                } else {

                    if (inputInt == 1) {
                        try {
                            tui.getClient().chooseSecretObjective1();
                            // tui.commandsCache.put(TUIstateCommands.CHOOSE_SECRET_OBJ, true);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else if (inputInt == 2) {
                        try {
                            tui.getClient().chooseSecretObjective2();
                            // tui.commandsCache.put(TUIstateCommands.CHOOSE_SECRET_OBJ, true);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else {
                        tui.printToCmdLineOut("Wrong input!");
                    }
                }
            } catch (NumberFormatException e) {
                tui.printToCmdLineOut("Wrong input!");
            }
        }
        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * play the starter card.
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#playStarter()
     * @see it.polimi.ingsw.gc31.controller.GameController#playStarter(String)
     * 
     */
    @Override
    protected void command_playStarter() {
        try {
            tui.getClient().playStarter();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        // tui.commandsCache.put(TUIstateCommands.PLAY_STARTER, true);
        stateNotify();
    }

    /**
     *
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * play a card.
     * <p>
     * Asks the user to type the X and Y coordinates where to place the card:
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#play(Point)
     * @see it.polimi.ingsw.gc31.controller.GameController#play(String, Point)
     * 
     */
    @Override
    protected void command_play() {
        String input;
        int inputX = 0;
        int inputY = 0;
        tui.printToCmdLineOut("Executing place command. Type 'e' to exit");
        command: while (true) {
            tui.printToCmdLineOut(tui.tuiWrite("Type X coordinate: "));
            x: while (true) {
                try {
                    input = scanner.nextLine();
                    if (input.equals("e")) {
                        tui.printToCmdLineOut("No place request sent");
                        break command;
                    }
                    inputX = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    tui.printToCmdLineOut(tui.tuiWrite("Try again... "));
                    continue x;
                }
                break x;
            }
            tui.printToCmdLineOut(tui.tuiWrite("Type Y coordinate: "));
            y: while (true) {
                try {
                    input = scanner.nextLine();
                    if (input.equals("e")) {
                        tui.printToCmdLineOut("No place request sent");
                        break command;
                    }
                    inputY = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    tui.printToCmdLineOut(tui.tuiWrite("Try again... "));
                    continue y;
                }
                break y;
            }
            try {
                tui.getClient().play(new Point(inputX, inputY));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            break command;
        }
        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * select a card.
     * <p>
     * Asks the user to type the index of the card he wants to select.
     * <p>
     * 
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#selectCard(int)
     * @see it.polimi.ingsw.gc31.controller.GameController#selectCard(String, int)
     * 
     */
    @Override
    protected void command_selectCard() {
        tui.printToCmdLineOut(tui.tuiWrite("Type the index of the card:"));
        try {
            int input = Integer.parseInt(scanner.nextLine());
            try {
                tui.getClient().selectCard(input - 1);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            tui.printToCmdLineOut("Wrong input");
        }
        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * change the side of the selected card.
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#changeSide()
     * @see it.polimi.ingsw.gc31.controller.GameController#changeSide(String)
     */
    @Override
    protected void command_changeSide() {
        try {
            tui.getClient().changeSide();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to
     * change the side of the starter card.
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see ClientCommands#changeStarterSide()
     * @see it.polimi.ingsw.gc31.controller.GameController#changeStarterSide(String)
     * 
     */
    @Override
    protected void command_changeStarterSide() {
        try {
            tui.getClient().changeStarterSide();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        stateNotify();
    }

    /**
     * Calls the TUI methods that move the play area in the specified direction.
     * <p>
     * Asks the user in which direction he wants to move the play area:
     * <ul>
     * <li>r -> right</li>
     * <li>l -> left</li>
     * <li>u -> up</li>
     * <li>d -> down</li>
     * </ul>
     * <p>
     * It calls {@link TUIstate#stateNotify()} to unblock the cmdLineReaderThread.
     * 
     * @see TUI#movePlayAreaRight()
     * @see TUI#movePlayAreaLeft()
     * @see TUI#movePlayAreaUp()
     * @see TUI#movePlayAreaDown()
     * 
     */
    @Override
    protected void command_movePlayArea() {
        tui.printToCmdLineOut(tui.tuiWrite("In which direction do you want to move?:"));
        tui.printToCmdLineOut(tui.tuiWrite("r -> right"));
        tui.printToCmdLineOut(tui.tuiWrite("l -> left"));
        tui.printToCmdLineOut(tui.tuiWrite("u -> up"));
        tui.printToCmdLineOut(tui.tuiWrite("d -> down"));
        String input = scanner.nextLine();
        // tui.printToCmdLineOut(tui.tuiWrite(input));
        if (input.equals("r")) {
            tui.movePlayAreaRight();
        } else if (input.equals("l")) {
            tui.movePlayAreaLeft();
        } else if (input.equals("u")) {
            tui.movePlayAreaUp();
        } else if (input.equals("d")) {
            tui.movePlayAreaDown();
        } else {
            tui.printToCmdLineOut("Wrong input");
        }
        stateNotify();
    }

    /**
     * Calls the TUI method that changes the active play area.
     * <p>
     * Asks the user which player's play area he wants to see.
     * <p>
     * The {@link TUIstate#stateNotify()} call to unblock the cmdLineReaderThread is
     * not needed cause the calls flow terminate in the
     * {@link PlayingState#command_refresh()} method that calls
     * {@link TUIstate#stateNotify()}.
     * 
     */
    @Override
    protected void command_changePlayArea() {
        tui.printToCmdLineOut(tui.tuiWrite("Which player do you want to see the playArea of?"));

        String input;
        input = scanner.nextLine();
        if (tui.getPlayAreaAllPlayers().containsKey(input)) {
            tui.printToCmdLineOut(tui.tuiWrite(input));
            tui.changeActivePlayArea(input);
        } else {
            tui.printToCmdLineOut(tui.tuiWrite(input));
            tui.printToCmdLineOut("Wrong Username...");
            stateNotify();
        }
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void setUsername() {
    }

    @Override
    protected void command_invalidCommand() {
        tui.printToCmdLineOut(tui.tuiWrite("Invalid command"));
        stateNotify();
    }

    /**
     * Sends the request to the
     * {@link it.polimi.ingsw.gc31.controller.GameController} to quit the game.
     * 
     * @see ClientCommands#quitGame()
     * 
     */
    @Override
    protected void command_quitGame() {
        try {
            tui.getClient().quitGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the TUI method that refreshes the TUI.
     * That method will call {@link TUIstate#stateNotify()} to unblock the
     * cmdLineReaderThread.
     * 
     * @see TUI#forceRefreshTUI(boolean)
     */
    @Override
    protected void command_refresh() {
        tui.forceRefreshTUI(true);
    }

    /**
     * Unimplemented method in this TUIstate
     */
    @Override
    protected void reconnect() {
    }

    // @Override
    // protected void reMatch() {
    // String input;
    // tui.printToCmdLineOut(tui.tuiWrite("Do you want to do another match?
    // (y/n)"));
    // tui.moveCursorToCmdLine();
    // input = scanner.nextLine();
    // while (true) {
    // try {
    // if (input.trim().equals("y")) {
    // tui.printToCmdLineOut(tui.tuiWrite("okokokoko"));
    // tui.moveCursorToCmdLine();
    // tui.getClient().anotherMatchResponse(true);
    // break;
    // } else if (input.trim().equals("n")) {
    // tui.getClient().anotherMatchResponse(false);
    // break;
    // } else {
    // tui.printToCmdLineOut("Wrong Input");
    // }
    // } catch (RemoteException e) {
    // e.getStackTrace();
    // }
    // }
    // }

}
