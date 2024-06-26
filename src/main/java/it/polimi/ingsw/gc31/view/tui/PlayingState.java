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
        commandsMap.put("quit", this::command_quitGame);
        commandsMap.put("ref", this::command_refresh);
        commandsMap.put("dg", this::command_drawGold);
        commandsMap.put("dr", this::command_drawResource);
        commandsMap.put("co", this::command_chooseSecreteObjective);
        commandsMap.put("ps", this::command_playStarter);
        commandsMap.put("p", this::command_play);
        commandsMap.put("s", this::command_selectCard);
        commandsMap.put("c", this::command_changeSide);
        commandsMap.put("cs", this::command_changeStarterSide);
        commandsMap.put("mv", this::command_movePlayArea);
        commandsMap.put("cp", this::command_changePlayArea);
        commandsMap.put("invalid", this::command_invalidCommand);

        commandsInfo = new LinkedHashMap<>();
        commandsInfo.put("help", "Shows commands info");
        commandsInfo.put("quit", "quit the game");
        commandsInfo.put("ref", "refresh tui");
        commandsInfo.put("dg -> draw gold", "Draw a gold card");
        commandsInfo.put("dr -> draw resource", "Draw a resource card");
        commandsInfo.put("co ->", "Choose secrete objective");
        commandsInfo.put("ps ->", "Play starter card");
        commandsInfo.put("p ->", "Play a card in the play area");
        commandsInfo.put("s ->", "Select a card from hand");
        commandsInfo.put("c ->", "Change side select card");
        commandsInfo.put("cs ->", "Change side starter card");
        commandsInfo.put("cp ->", "Change play area");
        commandsInfo.put("mv -> ", "Move play area");
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
                            tui.commandsCache.put(TUIcommands.CHOOSE_SERCRET_OBJ, true);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else if (inputInt == 2) {
                        try {
                            tui.getClient().chooseSecretObjective2();
                            tui.commandsCache.put(TUIcommands.CHOOSE_SERCRET_OBJ, true);
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

    @Override
    protected void command_playStarter() {
        try {
            tui.getClient().playStarter();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        tui.commandsCache.put(TUIcommands.PLAY_STARTER, true);
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
    protected void command_movePlayArea() {
        tui.printToCmdLineOut(tui.tuiWrite("In which direction do you want to move?:"));
        tui.printToCmdLineOut(tui.tuiWrite("r -> right"));
        tui.printToCmdLineOut(tui.tuiWrite("l -> left"));
        tui.printToCmdLineOut(tui.tuiWrite("u -> up"));
        tui.printToCmdLineOut(tui.tuiWrite("d -> down"));
        String input = scanner.nextLine();

        tui.printToCmdLineOut(tui.tuiWrite(input));

        if (input.equals("r")) {
            tui.movePlayAreaRight();
        } else if (input.equals("l")) {
            tui.movePlayAreaLeft();
        } else if (input.equals("u")) {
            tui.movePlayAreaUp();
        } else if (input.equals("d")) {
            tui.movePlayAreaDown();
        }

        stateNotify();
    }

    @Override
    protected void command_changePlayArea() {
        tui.printToCmdLineOut(tui.tuiWrite("Which player do you want to see the playArea of?"));

        String input = scanner.nextLine();
        tui.printToCmdLineOut(tui.tuiWrite(input));

        tui.changeActivePlayArea(input);
        // stateNotify();
    }

    @Override
    protected void command_initial() {
        // command_showCommandsInfo();
    }

    @Override
    protected void setUsername() {
    }

    @Override
    protected void command_invalidCommand() {
        tui.printToCmdLineOut(tui.tuiWrite("Invalid command"));
        stateNotify();
    }

    @Override
    protected void command_quitGame() {
        try {
            tui.getClient().quitGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void command_refresh() {
        tui.forceRefreshTUI(true);
    }

    @Override
    protected void reconnect() {
    }

    @Override
    protected void reMatch() {
        String input;
        tui.printToCmdLineOut(tui.tuiWrite("Do you want to do another match? (y/n)"));
        tui.moveCursorToCmdLine();
        input = scanner.nextLine();
        while (true) {
            try {
                if (input.trim().equals("y")) {
                    tui.printToCmdLineOut(tui.tuiWrite("okokokoko"));
                    tui.moveCursorToCmdLine();
                    tui.getClient().anotherMatchResponse(true);
                    break;
                } else if (input.trim().equals("n")) {
                    tui.getClient().anotherMatchResponse(false);
                    break;
                } else {
                    tui.printToCmdLineOut("Wrong Input");
                }
            } catch (RemoteException e) {
                e.getStackTrace();
            }
        }
    }

}
