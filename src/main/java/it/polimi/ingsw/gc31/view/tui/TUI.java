package it.polimi.ingsw.gc31.view.tui;

import static it.polimi.ingsw.gc31.utility.DV.RGB_COLOR_CORNER;
import static it.polimi.ingsw.gc31.utility.DV.getRgbColor;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

import java.awt.Point;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.*;

import it.polimi.ingsw.gc31.model.card.GoldCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.strategies.*;
import it.polimi.ingsw.gc31.utility.DV;

import javafx.util.Pair;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import it.polimi.ingsw.gc31.Client;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.UI;

/**
 * Text User Interface class that manages the text interface of the game
 */
@SuppressWarnings("FieldCanBeLocal")
public class TUI extends UI {

    // CHANGE THIS TO MODIFY THE TUI LAYOUT
    private final int CMD_LINE_INITIAL_ROW = 2;
    private final int CMD_LINE_INITIAL_COLUMN = 1;
    private final int CMD_LINE_LINES = 14;
    private final int CMD_LINE_WIDTH = 67;

    private final int PLAYAREA_INITIAL_ROW = 2;
    private final int PLAYAREA_INITIAL_COLUMN = 70;
    private final int PLAYAREA_END_ROW = 35;
    private final int PLAYAREA_END_COLUMN = 184;
    private final int CARD_LENGTH = 21;
    private final int CARD_HEIGHT = 7;
    private final int CARD_CORNER_LENGTH = 5;
    // the misalignment along x between two cards
    private final int CARD_X_OFFSET = 15;
    // the misalignment along y between two cards
    private final int CARD_Y_OFFSET = 4;

    private final int GOLD_DECK_INITIAL_ROW = 19;
    private final int GOLD_DECK_INITIAL_COLUMN = 1;
    private final int GOLD_DECK_END_ROW = GOLD_DECK_INITIAL_ROW + 9;
    private final int GOLD_DECK_END_COLUMN = 67;

    private final int CHAT_BOARD_INITIAL_ROW = GOLD_DECK_INITIAL_ROW;
    private final int CHAT_BOARD_INITIAL_COLUMN = 1;
    private final int CHAT_BOARD_LINES = 20;
    private final int CHAT_BOARD_WIDTH = CMD_LINE_WIDTH;

    private final int RESOURCE_DECK_INITIAL_ROW = GOLD_DECK_END_ROW + 2;
    private final int RESOURCE_DECK_INITIAL_COLUMN = 1;
    private final int RESOURCE_DECK_END_ROW = RESOURCE_DECK_INITIAL_ROW + 9;
    private final int RESOURCE_DECK_END_COLUMN = 67;

    private final int OBJECTIVE_INITIAL_ROW = 40;
    private final int OBJECTIVE_INITIAL_COLUMN = 46;
    private final int OBJECTIVE_END_ROW = OBJECTIVE_INITIAL_ROW + 9;
    private final int OBJECTIVE_END_COLUMN = 69;

    private final int COMMON_OBJECTIVE_INITIAL_ROW = OBJECTIVE_INITIAL_ROW;
    private final int COMMON_OBJECTIVE_INITIAL_COLUMN = 1;
    private final int COMMON_OBJECTIVE_END_ROW = OBJECTIVE_END_ROW;
    private final int COMMON_OBJECTIVE_END_COLUMN = 45;

    private final int CHOOSE_OBJECTIVE_INITIAL_ROW = 2;
    private final int CHOOSE_OBJECTIVE_INITIAL_COLUMN = 161;
    private final int CHOOSE_OBJECTIVE_END_ROW = CHOOSE_OBJECTIVE_INITIAL_ROW + 17;
    private final int CHOOSE_OBJECTIVE_END_COLUMN = 184;

    private final int HAND_INITIAL_ROW = OBJECTIVE_INITIAL_ROW - 2;
    private final int HAND_INITIAL_COLUMN = 70;
    private final int HAND_END_ROW = HAND_INITIAL_ROW + 9;
    private final int HAND_END_COLUMN = 137;

    private final int STARTER_CARD_INITIAL_ROW = 10;
    private final int STARTER_CARD_INITIAL_COLUMN = 111;
    private final int STARTER_CARD_END_ROW = 19;
    private final int STARTER_CARD_END_COLUMN = 134;

    private final int PLAYERS_INFO_INITIAL_ROW = HAND_INITIAL_ROW;
    private final int PLAYERS_INFO_INITIAL_COLUMN = 147;
    private final int PLAYERS_INFO_END_ROW = PLAYERS_INFO_INITIAL_ROW + 5;
    private final int PLAYERS_INFO_END_COLUMN = 184;

    private final int ACHIEVED_RESOURCES_INITIAL_ROW = HAND_INITIAL_ROW;
    private final int ACHIEVED_RESOURCES_INITIAL_COLUMN = HAND_END_COLUMN + 1;
    private final int ACHIEVED_RESOURCES_END_ROW = ACHIEVED_RESOURCES_INITIAL_ROW + 9;
    private final int ACHIEVED_RESOURCES_END_COLUMN = 146;

    private final int GAME_OVER_INITIAL_ROW = 18;
    private final int GAME_OVER_INITIAL_COLUMN = 1;
    private final int GAME_OVER_END_ROW = 39;
    private final int GAME_OVER_END_COLUMN = 67;

    // DO NOT CHANGE THOSE IF NOT STRICTLY NECESSARY
    private final int CMD_LINE_EFFECTIVE_WIDTH = CMD_LINE_WIDTH - 2;
    private final int CMD_LINE_INPUT_ROW = CMD_LINE_INITIAL_ROW + CMD_LINE_LINES;
    private final int CMD_LINE_INPUT_COLUMN = CMD_LINE_INITIAL_COLUMN + 1;
    private final int CMD_LINE_OUT_LINES = CMD_LINE_LINES - 1;
    private final int CHAT_BOARD_EFFECTIVE_WIDTH = CHAT_BOARD_WIDTH - 2;
    private final int CHAT_BOARD_INPUT_ROW = CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES;
    private final int CHAT_BOARD_INPUT_COLUMN = CHAT_BOARD_INITIAL_COLUMN + 1;
    private final int CHAT_BOARD_OUT_LINES = CHAT_BOARD_LINES - 1;

    // PLAY AREA
    // shift of the StarterCard along the x-axis relative to the center
    private int OFFSET_X_PLAYAREA = 0;
    // shift of the StarterCard along the y-axis relative to the center
    private int OFFSET_Y_PLAYAREA = 0;

    /**
     * List of players' usernames joined to the game the client is in
     */
    private List<String> playersUsernames = new ArrayList<>();

    // COLORS
    private final int[] RGB_COLOR_GOLD = { 181, 148, 16 };
    int[] greyText = null;
    // int[] greyText = new int[] { 192, 192, 192 };
    int[] goldText = new int[] { 233, 227, 38 };
    int[] blueText = new int[] { 51, 153, 255 };
    int[] violetText = new int[] { 153, 153, 255 };

    private LinkedHashMap<Point, PlayableCard> placedCards = null;
    private Map<String, StringBuilder> playAreaAllPlayers = new HashMap<>();

    public Map<String, StringBuilder> getPlayAreaAllPlayers() {
        return playAreaAllPlayers;
    }

    /**
     * String that contains username of the owner of the playArea to be printed
     */
    private String activePlayArea = "";

    /**
     * Changes the active playArea to the one specified by the username.
     * Update the areasCache with the playArea of the new activePlayArea and send
     * a {@link TUIstateCommands#REFRESH} command to the
     * {@link #cmdLineProcessTHREAD}
     * 
     * @param username
     */
    public void changeActivePlayArea(String username) {
        activePlayArea = username;
        areasCache.put(TUIareas.PLAY_VIEW_AREA, playAreaAllPlayers.get(username));
        commandToProcess(TUIstateCommands.REFRESH, false);
    }

    // PRINT METHODS
    /**
     * Map that associates a {@link TUIareas} with a {@link StringBuilder}
     * containing the last
     * received representation of that area
     */
    Map<TUIareas, StringBuilder> areasCache = new HashMap<>();
    // /**
    // * If a command is set to true in this map the
    // * {@link TUI#forceRefreshTUI(boolean)} will skip the corresponding area
    // */
    // protected Map<TUIstateCommands, Boolean> commandsCache = new HashMap<>();

    /**
     * Reprint the TUIareas based on the current state of the game and the
     * TUIareas contained in the areasCache map
     */
    protected void forceRefreshTUI(boolean stateNotify) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (playViewUpdateLOCK) {
            for (Map.Entry<TUIareas, StringBuilder> area : areasCache.entrySet()) {
                playViewUpdateLOCK.add(area.getValue());
            }
            try {

                playViewUpdateLOCK.add(areasCache.get(TUIareas.PLAY_VIEW_AREA));
            } catch (NullPointerException ignored) {

            }
            playViewUpdateLOCK.notify();
        }
        moveCursorToCmdLine();
        if (state.stateName.equals("Joined To Game State")) {
            // print_ChatBorders();
        }
        commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, stateNotify);

    }

    // PRINT UTILITIES
    /**
     * Erase the chat board from the terminal
     */
    private void erase_ChatBoard() {
        for (int i = -2; i < CHAT_BOARD_LINES + 1; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + 1 + i, CHAT_BOARD_INITIAL_COLUMN)
                            .a(" " + " ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + " "));
        }
    }

    /**
     * Erase every characters inside the playArea StringBuilders
     */
    public StringBuilder clearArea(int initialRow, int initialCol, int endRow, int endCol) {
        StringBuilder res = new StringBuilder();
        for (int i = -1; i <= endRow - initialRow; i++) {
            for (int j = 0; j <= endCol - initialCol; j++) {
                res.append(ansi().cursor(initialRow + i, initialCol + j).a(" "));
            }
        }
        return res;
    }

    /**
     * Draws the borders of the chat board
     */
    private void print_ChatBorders() {
        erase_ChatBoard();
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW - 1, CHAT_BOARD_INITIAL_COLUMN).fgBrightCyan()
                        .a("CHAT").reset());
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW, CHAT_BOARD_INITIAL_COLUMN)
                        .a("┌" + "─".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + "┐"));
        for (int i = 0; i < CHAT_BOARD_LINES; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + 1 + i, CHAT_BOARD_INITIAL_COLUMN)
                            .a("│" + " ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + "│"));
        }
        AnsiConsole.out().print(
                Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + CHAT_BOARD_LINES + 1, CHAT_BOARD_INITIAL_COLUMN)
                        .a("└" + "─".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + "┘"));

    }

    /**
     * Draws the borders of the command line
     */
    private void print_CmdLineBorders() {
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW - 1, CMD_LINE_INITIAL_COLUMN)
                        .a("COMMAND LINE"));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW, CMD_LINE_INITIAL_COLUMN)
                        .a("┌" + "─".repeat(CMD_LINE_EFFECTIVE_WIDTH) + "┐"));
        for (int i = 0; i < CMD_LINE_LINES; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + 1 + i, CMD_LINE_INITIAL_COLUMN)
                            .a("│" + " ".repeat(CMD_LINE_EFFECTIVE_WIDTH) + "│"));
        }
        AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INITIAL_ROW + CMD_LINE_LINES + 1, CMD_LINE_INITIAL_COLUMN)
                .a("└" + "─".repeat(CMD_LINE_EFFECTIVE_WIDTH) + "┘"));

    }

    /**
     * Generic borders printer utility for TUIareas
     * 
     * @param titleArea     the title of the area
     * @param color         the color of the title
     * @param initialRow    the initial row of the area
     * @param initialColumn the initial column of the area
     * @param endRow        the end row of the area
     * @param endColumn     the end column of the area
     * @return
     */
    private StringBuilder print_Borders(String titleArea, int[] color, int initialRow, int initialColumn, int endRow,
            int endColumn) {
        StringBuilder res = new StringBuilder();
        res.append(ansi().cursor(initialRow, initialColumn).fg(WHITE).a("┌")
                .a(("─").repeat(endColumn - initialColumn - 1)).a("┐"));

        for (int i = 1; i < endRow - initialRow; i++) {
            res.append(ansi().cursor(initialRow + i, initialColumn).a("│"));
            res.append(ansi().cursor(initialRow + i, endColumn).a("│"));
        }
        res.append(ansi().cursor(endRow, initialColumn).fg(WHITE).a("└")
                .a(("─").repeat(endColumn - initialColumn - 1)).a("┘"));
        if (color != null) {
            res.append(ansi().cursor(initialRow - 1, initialColumn + 1).bgRgb(color[0], color[1], color[2])
                    .a(titleArea.toUpperCase()).reset());
        } else {
            res.append(ansi().cursor(initialRow - 1, initialColumn + 1).a(titleArea.toUpperCase()).reset());
        }
        return res;
    }

    /**
     * Returns a stringBuilder that allows you to print a
     * {@link it.polimi.ingsw.gc31.model.player.PlayArea} in the playArea section of
     * the tui.
     * The cards are printed following the order of insertion on the map and
     * positioned based on the point.
     * PlaceHolders are printed before the cards to show the player the points in
     * which he can play a card
     *
     * @param placedCards a LinkedHashMap of Point and PlayableCard representing the
     *                    placed cards on the play area
     * @return a StringBuilder containing the printed representation of the placed
     *         cards
     */
    private StringBuilder print_PlacedCards(LinkedHashMap<Point, PlayableCard> placedCards) {
        StringBuilder res = new StringBuilder();
        List<Point> placeHolders = createPlaceHolder(placedCards);

        for (Point point : placeHolders) {
            res.append(print_PlaceHolder(
                    point,
                    PLAYAREA_INITIAL_COLUMN + (PLAYAREA_END_COLUMN - PLAYAREA_INITIAL_COLUMN) / 2
                            + (CARD_X_OFFSET * point.x) + OFFSET_X_PLAYAREA,
                    PLAYAREA_INITIAL_ROW + (PLAYAREA_END_ROW - PLAYAREA_INITIAL_ROW) / 2
                            - (CARD_Y_OFFSET * point.y) + OFFSET_Y_PLAYAREA,
                    PLAYAREA_INITIAL_ROW, PLAYAREA_END_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_COLUMN));
        }

        for (Map.Entry<Point, PlayableCard> entry : placedCards.entrySet()) {
            res.append(print_PlayableCard(
                    entry.getValue(),
                    PLAYAREA_INITIAL_COLUMN + (PLAYAREA_END_COLUMN - PLAYAREA_INITIAL_COLUMN) / 2
                            + (CARD_X_OFFSET * entry.getKey().x) + OFFSET_X_PLAYAREA - (CARD_LENGTH - 1) / 2,
                    PLAYAREA_INITIAL_ROW + (PLAYAREA_END_ROW - PLAYAREA_INITIAL_ROW) / 2
                            - (CARD_Y_OFFSET * entry.getKey().y) + OFFSET_Y_PLAYAREA - ((CARD_HEIGHT - 1) / 2),
                    PLAYAREA_INITIAL_ROW, PLAYAREA_END_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_COLUMN));
        }
        return res;
    }

    /**
     * Returns a stringBuilder that allows you to print a {@link ObjectiveCard} on
     * tui, in a certain position in a delimited area.
     * The card is printed starting from the top left corner of the card.
     * (relative_x,relative_y) = (0,0) corresponds to the top left corner of the
     * area where the card is printed.
     * <p>
     * The card is made of n strings of length m, where n is equals to
     * {@link #CARD_HEIGHT} and m is equals to {@link #CARD_LENGTH}.
     * If a character of the strings that composes the card goes outside the limits
     * it is not printed.
     *
     * @param card          The objective card to be printed.
     * @param relative_x    The relative x position of the card on the screen.
     * @param relative_y    The relative y position of the card on the screen.
     * @param overFlowUp    The upper limit of overflow on the screen.
     * @param overFlowDown  The lower limit of overflow on the screen.
     * @param overFlowLeft  The left limit of overflow on the screen.
     * @param overFlowRight The right limit of overflow on the screen.
     * @return StringBuilder The result of the printing operation in the form of a
     *         string builder.
     *         Returns null if the card exceeds the limits of the playArea.
     */
    protected StringBuilder print_ObjectiveCard(ObjectiveCard card, int relative_x, int relative_y, int overFlowUp,
            int overFlowDown, int overFlowLeft, int overFlowRight) {
        // int[] cardColor = RGB_COLOR_RED;
        // if the card entirely exceeds the limits of the playArea it is not printed
        if (overFlowLeft - (relative_x + CARD_LENGTH - 1) < 0 && (overFlowRight - relative_x) > 0
                && (overFlowDown - relative_y) > 0 && overFlowUp - (relative_y + CARD_HEIGHT - 1) < 0) {
            StringBuilder res = new StringBuilder();
            String line;

            // FIRST LINE
            // if the first line of the card exceeds the upper or lower limit the line is
            // not printed
            if (relative_y > overFlowUp && relative_y < overFlowDown) {
                line = "┌" + ("─").repeat(CARD_LENGTH - 2) + "┐";
                // line = "▏" + String.valueOf("▔").repeat(CARD_LENGTH - 2) + "▕";
                if (relative_x + line.length() > overFlowRight) {
                    line = line.substring(0, overFlowRight - relative_x);
                    res.append(ansi().cursor(relative_y, relative_x)
                            // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                            .a(line).reset());
                } else if (overFlowLeft - relative_x >= 0) {
                    line = line.substring(overFlowLeft - relative_x + 1);
                    res.append(ansi().cursor(relative_y, overFlowLeft + 1)
                            // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                            .a(line).reset());
                } else {
                    res.append(ansi().cursor(relative_y, relative_x)
                            // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                            .a(line).reset());
                }
            }

            // creates the center lines of the paper
            for (int i = 1; i <= CARD_HEIGHT - 2; i++) {
                // if a center line of the card exceeds the upper or lower limit the line is not
                // printed
                if (relative_y + i > overFlowUp && relative_y + i < overFlowDown) {
                    line = "│" + (" ").repeat(CARD_LENGTH - 2) + "│";
                    // line = "▏" + String.valueOf(" ").repeat(CARD_LENGTH - 2) + "▕";
                    // if part of the line exceeds the right limit, the excess part is cut off
                    if (relative_x + line.length() > overFlowRight) {
                        line = line.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y + i, relative_x)
                                // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                                .a(line).reset());
                    }
                    // If part of the line exceeds the left limit, the excess part is cut off
                    else if (overFlowLeft - relative_x >= 0) {
                        line = line.substring(overFlowLeft - relative_x + 1);
                        res.append(ansi().cursor(relative_y + i, overFlowLeft + 1)
                                // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                                .a(line).reset());
                    } else {
                        res.append(ansi().cursor(relative_y + i, relative_x)
                                // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                                .a(line).reset());
                    }
                }
            }

            // LAST LINE
            // if the last line of the card exceeds the upper or lower limit the line is not
            // printed
            if (relative_y + CARD_HEIGHT - 1 > overFlowUp && relative_y + CARD_HEIGHT - 1 < overFlowDown) {
                line = "└" + ("─").repeat(CARD_LENGTH - 2) + "┘";
                // line = "▏" + String.valueOf("▁").repeat(CARD_LENGTH - 2) + "▕";
                // if part of the line exceeds the right limit, the excess part is cut off
                if (relative_x + line.length() > overFlowRight) {
                    line = line.substring(0, overFlowRight - relative_x);
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                            // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                            .a(line).reset());
                }
                // If part of the line exceeds the left limit, the excess part is cut off
                else if (overFlowLeft - relative_x >= 0) {
                    line = line.substring(overFlowLeft - relative_x + 1);
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, overFlowLeft + 1)
                            // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                            .a(line).reset());
                } else {
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                            // .bgRgb(cardColor[0], cardColor[1], cardColor[2])
                            .a(line).reset());
                }
            }

            res.append(ansi().cursor(relative_y + CARD_HEIGHT / 2, relative_x + CARD_LENGTH / 4).a(card.getScore()));
            res.append(
                    ansi().cursor(relative_y + CARD_HEIGHT / 2 - 2, relative_x + CARD_LENGTH / 2).saveCursorPosition());
            res.append(card.getObjective().print());

            res.append(ansi().reset());
            return res;
        }
        return null;

    }

    /**
     * Returns a stringBuilder that allows you to print a {@link PlayableCard} on
     * tui, in a certain position in a delimited area.
     * The card is printed starting from the top left corner of the card.
     * (relative_x,relative_y) = (0,0) corresponds to the top left corner of the
     * area where the card is printed.
     * <p>
     * The card is made of n strings of length m, where n is equals to
     * {@link #CARD_HEIGHT} and m is equals to {@link #CARD_LENGTH}.
     * If a character of the strings that composes the card goes outside the limits
     * it is not printed.
     *
     * @param card          The playable card to be printed.
     * @param relative_x    The x-coordinate of the top left corner of the card
     *                      relative to the play area.
     * @param relative_y    The y-coordinate of the top left corner of the card
     *                      relative to the play area.
     * @param overFlowUp    The upper overflow limit of the play area.
     * @param overFlowDown  The lower overflow limit of the play area.
     * @param overFlowLeft  The left overflow limit of the play area.
     * @param overFlowRight The right overflow limit of the play area.
     * @return A StringBuilder containing the generated ANSI escape sequences to
     *         print the card on the console.
     */
    protected StringBuilder print_PlayableCard(PlayableCard card, int relative_x, int relative_y, int overFlowUp,
            int overFlowDown,
            int overFlowLeft, int overFlowRight) {
        // the card is printed starting from the top left corner

        // limits of the playArea beyond which parts of the cards or entire cards are
        // not printed

        int score = card.getScore();
        List<Resources> resources = card.getCorners();
        List<Resources> requirements = new ArrayList<>();
        for (Map.Entry<Resources, Integer> entry : card.getRequirements().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                requirements.add(entry.getKey());
            }
        }

        Objective ob = card.getObjective();
        int[] cardColor = getRgbColor(card.getColor());
        int[] cornerUpSxColor;
        int[] cornerUpDxColor;
        int[] cornerDownSxColor;
        int[] cornerDownDxColor;
        int[] borderColor = { 255, 255, 255 };

        if (!resources.get(0).equals(Resources.HIDDEN)) {
            cornerUpDxColor = RGB_COLOR_CORNER;
        } else {
            cornerUpDxColor = cardColor;
        }
        if (!resources.get(1).equals(Resources.HIDDEN)) {
            cornerDownDxColor = RGB_COLOR_CORNER;
        } else {
            cornerDownDxColor = cardColor;
        }
        if (!resources.get(2).equals(Resources.HIDDEN)) {
            cornerDownSxColor = RGB_COLOR_CORNER;
        } else {
            cornerDownSxColor = cardColor;
        }
        if (!resources.get(3).equals(Resources.HIDDEN)) {
            cornerUpSxColor = RGB_COLOR_CORNER;
        } else {
            cornerUpSxColor = cardColor;
        }

        // TODO da cambiare è temporaneo
        if (card instanceof GoldCard) {
            borderColor = RGB_COLOR_GOLD;
        }

        // if the card entirely exceeds the limits of the playArea it is not printed
        if (overFlowLeft - (relative_x + CARD_LENGTH - 1) < 0 && (overFlowRight - relative_x) > 0
                && (overFlowDown - relative_y) > 0 && overFlowUp - (relative_y + CARD_HEIGHT - 1) < 0) {
            StringBuilder res = new StringBuilder();
            String preLine;
            String centerLine;
            String postLine;

            // FIRST LINE
            // if the first line of the card exceeds the upper or lower limit the line is
            // not printed
            if (relative_y > overFlowUp && relative_y < overFlowDown) {
                preLine = "▏" + (" ").repeat(CARD_CORNER_LENGTH - 1);
                centerLine = ("▔").repeat(CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                postLine = (" ").repeat(CARD_CORNER_LENGTH - 1) + "▕";
                // if part of the line exceeds the right limit, the excess part is cut off
                if (relative_x + CARD_LENGTH > overFlowRight) {
                    if (overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH) > 0) {
                        postLine = postLine.substring(0,
                                overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH));
                        res.append(ansi().cursor(relative_y, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else if (relative_x + CARD_CORNER_LENGTH >= overFlowRight) {
                        preLine = preLine.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine));
                    } else {
                        centerLine = centerLine.substring(0, overFlowRight - relative_x - CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset());
                    }

                }
                // If part of the line exceeds the left limit, the excess part is cut off, and
                // the x-coordinate changes
                else if (overFlowLeft - relative_x >= 0) {
                    if (CARD_CORNER_LENGTH - (overFlowLeft - relative_x) - 1 > 0) {
                        preLine = preLine.substring(overFlowLeft - relative_x + 1, CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y, overFlowLeft + 1)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else if ((relative_x + CARD_LENGTH - CARD_CORNER_LENGTH) - overFlowLeft - 1 < 0) {
                        postLine = postLine.substring(
                                overFlowLeft + 1 - (relative_x + CARD_LENGTH - CARD_CORNER_LENGTH), CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y, overFlowLeft + 1)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else {
                        centerLine = centerLine.substring(overFlowLeft - relative_x + 1 - CARD_CORNER_LENGTH,
                                CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y, overFlowLeft + 1)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    }

                } else {
                    res.append(ansi().cursor(relative_y, relative_x)
                            .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                            .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                            .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                            .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine)
                            .reset());
                }
            }

            // SECOND LINE
            if (relative_y + 1 > overFlowUp && relative_y + 1 < overFlowDown) {
                preLine = "▏" + (" ").repeat(CARD_CORNER_LENGTH - 1);
                centerLine = (" ").repeat(CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                postLine = (" ").repeat(CARD_CORNER_LENGTH - 1) + "▕";
                // if part of the line exceeds the right limit, the excess part is cut off
                if (relative_x + CARD_LENGTH > overFlowRight) {
                    if (overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH) > 0) {
                        postLine = postLine.substring(0,
                                overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH));
                        res.append(ansi().cursor(relative_y + 1, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else if (relative_x + CARD_CORNER_LENGTH >= overFlowRight) {
                        preLine = preLine.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y + 1, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine));
                    } else {
                        centerLine = centerLine.substring(0, overFlowRight - relative_x - CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + 1, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine));
                    }

                }
                // If part of the line exceeds the left limit, the excess part is cut off, and
                // the x-coordinate changes
                else if (overFlowLeft - relative_x >= 0) {
                    if (CARD_CORNER_LENGTH - (overFlowLeft - relative_x) - 1 > 0) {
                        preLine = preLine.substring(overFlowLeft - relative_x + 1, CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + 1, overFlowLeft + 1)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else if ((relative_x + CARD_LENGTH - CARD_CORNER_LENGTH) - overFlowLeft - 1 < 0) {
                        postLine = postLine.substring(
                                overFlowLeft + 1 - (relative_x + CARD_LENGTH - CARD_CORNER_LENGTH), CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + 1, overFlowLeft + 1)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else {
                        centerLine = centerLine.substring(overFlowLeft - relative_x + 1 - CARD_CORNER_LENGTH,
                                CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + 1, overFlowLeft + 1)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    }

                } else {
                    res.append(ansi().cursor(relative_y + 1, relative_x)
                            .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                            .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                            .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                }

                // CORNER UP SX
                if (!resources.get(3).equals(Resources.HIDDEN) && relative_x + 2 < overFlowRight
                        && relative_x + 2 > overFlowLeft + 1) {
                    res.append(ansi().cursor(relative_y + 1, relative_x + 1)
                            .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2])
                            .a(resources.get(3).getSymbol()));
                }
                // CORNER UP DX
                if (!resources.get(0).equals(Resources.HIDDEN)
                        && relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) + 2 < overFlowRight
                        && relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) + 2 > overFlowLeft + 1) {
                    res.append(ansi().cursor(relative_y + 1, relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) + 1)
                            .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2])
                            .a(resources.getFirst().getSymbol()));
                }

                // OBJECTIVE AREA
                // TODO aggiungere limiti
                if (score != 0 && ob != null) {
                    res.append(ansi().cursor(relative_y + 1, relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) / 2)
                            .bgRgb(RGB_COLOR_CORNER[0], RGB_COLOR_CORNER[1], RGB_COLOR_CORNER[2])
                            .a((" ").repeat(CARD_CORNER_LENGTH)));
                    res.append(ansi().cursor(relative_y + 1, relative_x + CARD_LENGTH / 2 - 1)
                            .bgRgb(RGB_COLOR_CORNER[0], RGB_COLOR_CORNER[1], RGB_COLOR_CORNER[2])
                            .fg(BLACK).a(score + "│" + ob.print()).reset());
                } else if (score != 0) {
                    res.append(ansi().cursor(relative_y + 1, relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) / 2)
                            .bgRgb(RGB_COLOR_CORNER[0], RGB_COLOR_CORNER[1], RGB_COLOR_CORNER[2])
                            .a((" ").repeat(CARD_CORNER_LENGTH)));
                    res.append(ansi().cursor(relative_y + 1, relative_x + CARD_LENGTH / 2)
                            .bgRgb(RGB_COLOR_CORNER[0], RGB_COLOR_CORNER[1], RGB_COLOR_CORNER[2]).fg(BLACK).a(score)
                            .reset());
                }

            }

            // creates the center lines of the paper
            for (int i = 2; i <= CARD_HEIGHT - 3; i++) {
                // if a center line of the card exceeds the upper or lower limit the line is not
                // printed
                if (relative_y + i > overFlowUp && relative_y + i < overFlowDown) {
                    String line = "▏" + (" ").repeat(CARD_LENGTH - 2) + "▕";
                    // if part of the line exceeds the right limit, the excess part is cut off
                    if (relative_x + line.length() > overFlowRight) {
                        line = line.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y + i, relative_x)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(line).reset());
                    }
                    // If part of the line exceeds the left limit, the excess part is cut off
                    else if (overFlowLeft - relative_x >= 0) {
                        line = line.substring(overFlowLeft - relative_x + 1);
                        res.append(ansi().cursor(relative_y + i, overFlowLeft + 1)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(line).reset());
                    } else {
                        res.append(ansi().cursor(relative_y + i, relative_x)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(line).reset());
                    }
                }
            }

            // SECOND-LAST LINE
            if (relative_y - 1 + CARD_HEIGHT - 1 > overFlowUp && relative_y - 1 + CARD_HEIGHT - 1 < overFlowDown) {
                // if part of the line exceeds the right limit, the excess part is cut off
                preLine = "▏" + (" ").repeat(CARD_CORNER_LENGTH - 1);
                centerLine = (" ").repeat(CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                postLine = (" ").repeat(CARD_CORNER_LENGTH - 1) + "▕";
                if (relative_x + CARD_LENGTH > overFlowRight) {
                    if (overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH) > 0) {
                        postLine = postLine.substring(0,
                                overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH));
                        res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(postLine));
                    } else if (relative_x + CARD_CORNER_LENGTH >= overFlowRight) {
                        preLine = preLine.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine));
                    } else {
                        centerLine = centerLine.substring(0, overFlowRight - relative_x - CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine));
                    }
                } else if (overFlowLeft - relative_x >= 0) {
                    if (CARD_CORNER_LENGTH - (overFlowLeft - relative_x) - 1 > 0) {
                        preLine = preLine.substring(overFlowLeft - relative_x + 1, CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    } else if ((relative_x + CARD_LENGTH - CARD_CORNER_LENGTH) - overFlowLeft - 1 < 0) {
                        postLine = postLine.substring(
                                overFlowLeft + 1 - (relative_x + CARD_LENGTH - CARD_CORNER_LENGTH), CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    } else {
                        centerLine = centerLine.substring(overFlowLeft - relative_x + 1 - CARD_CORNER_LENGTH,
                                CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    }
                } else {
                    res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, relative_x)
                            .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                            .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                            .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                }

                // CORNER DOWN SX
                if (relative_x + 2 < overFlowRight && relative_x + 2 > overFlowLeft + 1) {
                    res.append(ansi().cursor(relative_y - 1 + CARD_HEIGHT - 1, relative_x + 1)
                            .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2])
                            .a(resources.get(2).getSymbol()));
                }

                // CORNER DOWN DX
                if (relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) + 2 < overFlowRight
                        && relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) + 2 > overFlowLeft + 1) {
                    res.append(ansi()
                            .cursor(relative_y - 1 + CARD_HEIGHT - 1,
                                    relative_x + (CARD_LENGTH - CARD_CORNER_LENGTH) + 1)
                            .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2])
                            .a(resources.get(1).getSymbol()));
                }

                // REQUIREMENTS
                if (!requirements.equals(Collections.emptyMap())) {
                    StringBuilder requirementsLine = new StringBuilder();
                    for (Resources resource : requirements) {
                        requirementsLine.append(resource.getSymbol());
                    }
                    res.append(ansi()
                            .cursor(relative_y - 1 + CARD_HEIGHT - 1,
                                    relative_x + (CARD_LENGTH - requirementsLine.length()) / 2)
                            .bgRgb(RGB_COLOR_CORNER[0], RGB_COLOR_CORNER[1], RGB_COLOR_CORNER[2])
                            .a(requirementsLine.toString()));
                }
            }

            // LAST LINE
            // if the last line of the card exceeds the upper or lower limit the line is not
            // printed
            if (relative_y + CARD_HEIGHT - 1 > overFlowUp && relative_y + CARD_HEIGHT - 1 < overFlowDown) {
                // if part of the line exceeds the right limit, the excess part is cut off
                preLine = "▏" + (" ").repeat(CARD_CORNER_LENGTH - 1);
                centerLine = ("▁").repeat(CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                postLine = (" ").repeat(CARD_CORNER_LENGTH - 1) + "▕";
                if (relative_x + CARD_LENGTH > overFlowRight) {
                    if (overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH) > 0) {
                        postLine = postLine.substring(0,
                                overFlowRight - relative_x - (CARD_LENGTH - CARD_CORNER_LENGTH));
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(postLine));
                    } else if (relative_x + CARD_CORNER_LENGTH >= overFlowRight) {
                        preLine = preLine.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine));
                    } else {
                        centerLine = centerLine.substring(0, overFlowRight - relative_x - CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset());
                    }
                } else if (overFlowLeft - relative_x >= 0) {
                    if (CARD_CORNER_LENGTH - (overFlowLeft - relative_x) - 1 > 0) {
                        preLine = preLine.substring(overFlowLeft - relative_x + 1, CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    } else if ((relative_x + CARD_LENGTH - CARD_CORNER_LENGTH) - overFlowLeft - 1 < 0) {
                        postLine = postLine.substring(
                                overFlowLeft + 1 - (relative_x + CARD_LENGTH - CARD_CORNER_LENGTH), CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    } else {
                        centerLine = centerLine.substring(overFlowLeft - relative_x + 1 - CARD_CORNER_LENGTH,
                                CARD_LENGTH - 2 * CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    }
                } else {
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                            .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                            .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                            .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine).reset()
                            .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine)
                            .reset());
                }
            }

            if (resources.size() > 4) {
                StringBuilder line = new StringBuilder();
                for (Resources resource : resources.subList(4, resources.size())) {
                    line.append(resource.getSymbol());
                }
                res.append(ansi()
                        .cursor(relative_y + CARD_HEIGHT / 2, relative_x + CARD_LENGTH / 2 - (resources.size() - 4))
                        .bgRgb(RGB_COLOR_CORNER[0], RGB_COLOR_CORNER[1], RGB_COLOR_CORNER[2])
                        .a(" " + line + " "));
            }

            res.append(ansi().reset());
            return res;
        }
        return null;
    }

    // TODO christian
    private StringBuilder print_PlaceHolder(Point point, int x, int y, int overFlowUp, int overFlowDown,
            int overFlowLeft, int overFlowRight) {
        int relative_x = x - (CARD_LENGTH - 1) / 2;
        int relative_y = y - (CARD_HEIGHT - 1) / 2;

        StringBuilder res = new StringBuilder();
        if (overFlowLeft - (relative_x + CARD_LENGTH - 1) < 0 && (overFlowRight - relative_x) > 0
                && (overFlowDown - relative_y) > 0 && overFlowUp - (relative_y + CARD_HEIGHT - 1) < 0) {
            String line;
            if (relative_y > overFlowUp && relative_y < overFlowDown) {
                line = "┌" + ("─").repeat(CARD_LENGTH - 2) + "┐";
                if (relative_x + CARD_LENGTH > overFlowRight) {
                    line = line.substring(0, overFlowRight - relative_x);
                    res.append(ansi().cursor(relative_y, relative_x).a(line));
                } else if (overFlowLeft - relative_x >= 0) {
                    line = line.substring(overFlowLeft - relative_x + 1, line.length());
                    res.append(ansi().cursor(relative_y, overFlowLeft + 1).a(line));
                } else {
                    res.append(ansi().cursor(relative_y, relative_x).a(line));
                }
            }

            for (int i = 1; i <= CARD_HEIGHT - 2; i++) {
                if (relative_y + i > overFlowUp && relative_y + i < overFlowDown) {
                    line = "│" + (" ").repeat(CARD_LENGTH - 2) + "│";
                    if (relative_x + CARD_LENGTH > overFlowRight) {
                        line = line.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y + i, relative_x).a(line));
                    } else if (overFlowLeft - relative_x >= 0) {
                        line = line.substring(overFlowLeft - relative_x + 1, CARD_LENGTH);
                        res.append(ansi().cursor(relative_y + i, overFlowLeft + 1).a(line));
                    } else {
                        res.append(ansi().cursor(relative_y + i, relative_x).a(line));
                    }
                }
            }

            if (relative_y + CARD_HEIGHT - 1 > overFlowUp && relative_y + CARD_HEIGHT - 1 < overFlowDown) {
                line = "└" + ("─").repeat(CARD_LENGTH - 2) + "┘";
                if (relative_x + CARD_LENGTH > overFlowRight) {
                    line = line.substring(0, overFlowRight - relative_x);
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x).a(line));
                } else if (overFlowLeft - relative_x >= 0) {
                    line = line.substring(overFlowLeft - relative_x + 1, CARD_LENGTH);
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, overFlowLeft + 1).a(line));
                } else {
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x).a(line));
                }
            }

            if (relative_y + CARD_HEIGHT / 2 > overFlowUp && relative_y + CARD_HEIGHT / 2 < overFlowDown) {
                if (relative_x + CARD_LENGTH / 2 + 3 < overFlowRight && relative_x + CARD_LENGTH / 2 > overFlowLeft) {
                    res.append(
                            ansi().cursor(relative_y + CARD_HEIGHT / 2, relative_x + CARD_LENGTH / 2)
                                    .a(point.x + "," + point.y));
                }
            }
        }
        return res;
    }

    /**
     * Prints a generic deck of cards
     * 
     * @param title         the name used as label for the deck
     * @param titleColor    the color of the title
     * @param firstCardDeck the card on top of the deck
     * @param card1         the second card of the deck
     * @param card2         the third card of the deck
     * @param initial_row   the row where the deck area starts
     * @param initial_col   the column where the deck area starts
     * @param end_row       the row where the deck area ends
     * @param end_col       the column where the deck area ends
     * @return
     */
    private StringBuilder print_Deck(String title, int[] titleColor, PlayableCard firstCardDeck, PlayableCard card1,
            PlayableCard card2,
            int initial_row, int initial_col, int end_row, int end_col) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(initial_row, initial_col, end_row, end_col));
        res.append(print_Borders(title, titleColor, initial_row, initial_col, end_row, end_col));
        if (firstCardDeck != null) {
            res.append(print_PlayableCard(firstCardDeck, initial_col + 1, initial_row + 1, initial_row, end_row,
                    initial_col, end_col));
            res.append(ansi().cursor(initial_row + CARD_HEIGHT + 1, initial_col + 1).a("Deck"));
        }
        if (card1 != null) {
            res.append(print_PlayableCard(card1, initial_col + 1 + (CARD_LENGTH + 1), initial_row + 1, initial_row,
                    end_row, initial_col, end_col));
            res.append(ansi().cursor(initial_row + CARD_HEIGHT + 1, initial_col + 1 + (CARD_LENGTH + 1)).a("Card 1"));
        }
        if (card2 != null) {
            res.append(print_PlayableCard(card2, initial_col + 1 + (CARD_LENGTH + 1) * 2, initial_row + 1, initial_row,
                    end_row, initial_col, end_col));
            res.append(
                    ansi().cursor(initial_row + CARD_HEIGHT + 1, initial_col + 1 + (CARD_LENGTH + 1) * 2).a("Card 2"));
        }
        return res;
    }

    /**
     * Draws the title of the game
     */
    private void print_Title() {
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()).println(ansi().fg(YELLOW).a("""
                        █▀▀▀▀█  █▀▀▀▀█  █▀▀▀▄   █▀▀▀▀▀  █    █
                        █       █    █  █    █  █        ▀▄▄▀
                        █       █    █  █    █  █▀▀▀▀▀   ▄▀▀▄
                        █▄▄▄▄█  █▄▄▄▄█  █▄▄▄▀   █▄▄▄▄▄  █    █
                            """).reset());
    }

    // TODO christian
    private List<Point> createPlaceHolder(Map<Point, PlayableCard> cards) {
        List<Point> placeHolders = new ArrayList<>();
        Point upDx;
        Point upSx;
        Point downDx;
        Point downSx;
        for (Point point : cards.keySet()) {
            if (!placeHolders.contains(point)) {
                placeHolders.remove(point);
            }
            upDx = new Point(point.x + 1, point.y + 1);
            upSx = new Point(point.x - 1, point.y + 1);
            downDx = new Point(point.x + 1, point.y - 1);
            downSx = new Point(point.x - 1, point.y - 1);
            if (!placeHolders.contains(upDx)) {
                placeHolders.add(upDx);
            }
            if (!placeHolders.contains(upSx)) {
                placeHolders.add(upSx);
            }
            if (!placeHolders.contains(downDx)) {
                placeHolders.add(downDx);
            }
            if (!placeHolders.contains(downSx)) {
                placeHolders.add(downSx);
            }
        }
        return placeHolders;
    }

    /**
     * This method is used to drag the play area to the right
     */
    protected void movePlayAreaRight() {
        if (placedCards != null) {
            OFFSET_X_PLAYAREA += CARD_X_OFFSET * 2;
            show_playArea(activePlayArea, placedCards, null);
        }
    }

    /**
     * This method is used to drag the play area to the left
     */
    protected void movePlayAreaLeft() {
        if (placedCards != null) {
            OFFSET_X_PLAYAREA -= CARD_X_OFFSET * 2;
            show_playArea(activePlayArea, placedCards, null);
        }
    }

    /**
     * This method is used to drag the play area down
     */
    protected void movePlayAreaDown() {
        if (placedCards != null) {
            OFFSET_Y_PLAYAREA += CARD_Y_OFFSET * 2;
            show_playArea(activePlayArea, placedCards, null);
        }
    }

    /**
     * This method is used to drag the play area up
     */
    protected void movePlayAreaUp() {
        if (placedCards != null) {
            OFFSET_Y_PLAYAREA -= CARD_Y_OFFSET * 2;
            show_playArea(activePlayArea, placedCards, null);
        }
    }

    // GETTERS & SETTERS

    /**
     * @return the ClientCommands client of the TUI
     */
    public ClientCommands getClient() {
        return this.client;
    }

    /**
     * Creates a new TUI object and initializes it with the given client and the
     * {@link InitState}
     * 
     * 
     * @param client
     */
    public TUI(ClientCommands client) {
        AnsiConsole.systemInstall();

        this.state = new InitState(this);
        this.client = client;
    }

    // UTILITIES

    /**
     * This method is used to move the cursor to the command line input area.
     * <p>
     * It also clears the command line input area.
     */
    protected void moveCursorToCmdLine() {
        AnsiConsole.out().print(
                Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
        AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a("> "));
    }

    /**
     * This method is used to move the cursor to the command line input area.
     * <p>
     * It also clears the command line input area and adds a prefix text to it
     * 
     * @param prefix the prefix to be printed before the cursor
     */
    protected void moveCursorToCmdLine(String prefix) {
        AnsiConsole.out().print(
                Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).fgBrightCyan().a(prefix + "> ")
                        .reset());
    }

    /**
     * This method is used to move the cursor to the chat input area.
     * <p>
     * It also clears the chat input area.
     */
    private void moveCursorToChatLine() {
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN)
                .a(" ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH)));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN).a("> "));
    }

    // /**
    // * This method is used to move the cursor to the chat input area.
    // * <p>
    // * It also clears the chat input area and update the
    // * <code>terminalAreaSelection</code> variable.
    // *
    // * @param prefix the prefix to be printed before the cursor
    // */
    // private void moveCursorToChatLine(String prefix) {
    // AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW,
    // CHAT_BOARD_INPUT_COLUMN)
    // .a(" ".repeat(CHAT_BOARD_WIDTH)));
    // AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW,
    // CHAT_BOARD_INPUT_COLUMN).a(prefix + " "));
    // }

    /**
     * This method is used to move the cursor to the right active area.
     * Should be used after a TUIarea update to bring the cursor back to the
     * selected one.
     */
    protected void resetCursor() {
        synchronized (cmdLineAreaSelectionLOCK) {
            if (!cmdLineAreaSelectionLOCK.isEmpty()) {
                moveCursorToCmdLine();
            }
        }
        synchronized (chatAreaSelectionLOCK) {
            if (!chatAreaSelectionLOCK.isEmpty()) {
                moveCursorToChatLine();
            }
        }
    }

    /**
     * This method is used to add a message to the {@link #cmdLineOutLOCK}
     * thread's queue.
     * <p>
     * It also wakes up the {@link #cmdLineOutLOCK} thread to print the message.
     * <p>
     * This method must be used by any method that needs to print a message to the
     * command line out.
     *
     * @param message
     * @param color
     */
    protected void printToCmdLineOut(String message, Ansi.Color color) {
        synchronized (cmdLineOutLOCK) {
            cmdLineOutLOCK.add(Ansi.ansi().fg(color).a(message).reset().toString());
            cmdLineOutLOCK.notifyAll();
        }
    }

    /**
     * This method is used to add a message to the <code>commandLineOut</code>
     * thread's queue.
     * <p>
     * State methods must use this method to print their messages.
     *
     * @param message
     */
    protected void printToCmdLineOut(String message) {
        synchronized (cmdLineOutLOCK) {
            cmdLineOutLOCK.add(Ansi.ansi().a(message).reset().toString());
            cmdLineOutLOCK.notifyAll();
        }
        resetCursor();
    }

    /**
     * Prints a message to the command line input area. This can be useful to
     * indicates
     * which is the selected area (different from the commandLine).
     * 
     * @param message the message to be printed
     */
    protected void printToCmdLineIn(String message) {
        moveCursorToCmdLine(message);

    }

    /**
     * Formats a message as it is printed from the TUI
     *
     * @param text
     * @return the formatted String
     */
    protected String tuiWrite(String text) {
        return DV.ANSI_BLUE + DV.TUI_TAG + DV.ANSI_RESET + text;
    }

    /**
     * Formats a message as it is printed from the TUI in green
     *
     * @param text
     * @return the formatted String
     */
    protected String tuiWriteGreen(String text) {
        return DV.ANSI_BLUE + DV.TUI_TAG + DV.ANSI_GREEN + text
                + DV.ANSI_RESET;
    }

    /**
     * Formats a message as it is printed from the TUI in purple
     *
     * @param text
     * @return the formatted String
     */
    protected String tuiWritePurple(String text) {
        return DV.ANSI_BLUE + DV.TUI_TAG + DV.ANSI_PURPLE + text
                + DV.ANSI_RESET;
    }

    /**
     * Formats a message as it is printed from the Server/Controller
     * 
     * @param text
     * @return
     */
    protected String serverWrite(String text) {
        return DV.ANSI_PURPLE + DV.SERVER_TAG + DV.ANSI_RESET + text;
    }

    // THREADS

    /**
     * State of the TUI (State Design Pattern)
     * <p>
     * This variable is used to manage the available commands in the current state
     */
    private TUIstate state;

    /**
     * Object used to synchronize the state commands execute by the
     * {@link #cmdLineProcessTHREAD} and the {@link #cmdLineReaderTHREAD}.
     * Together with the {@link #stateLockQueueLOCK} are used to manage issues
     * arising
     * from improper calls to a blocking input
     * acquisition of a {@link Scanner} object.
     * input.
     * <p>
     * This lock ensures that only one thread can perform a blocking input operation
     * on the {@link Scanner} at a time,
     * preventing potential conflicts and ensuring that the input is handled safely
     * and correctly.
     */
    protected Object stateLOCK = new Object();
    /**
     * Object used to synchronize the state commands execute by the
     * {@link #cmdLineProcessTHREAD} and the {@link #cmdLineReaderTHREAD}.
     * Together with the {@link #stateLOCK} are used to manage issues arising
     * from improper calls to a blocking input
     * acquisition of a {@link Scanner} object.
     * input.
     * <p>
     * This lock ensures that only one thread can perform a blocking input operation
     * on the {@link Scanner} at a time,
     * preventing potential conflicts and ensuring that the input is handled safely
     * and correctly.
     */
    protected Queue<Integer> stateLockQueueLOCK = new ArrayDeque<Integer>();

    /**
     * This method is used to add a new element to the {@link #stateLockQueueLOCK}
     * and
     * wakes up the {@link #cmdLineProcessTHREAD}.
     * <p>
     * Synchronized on the {@link #stateLockQueueLOCK} object.
     */
    protected void addToStateLockQueue() {
        synchronized (stateLockQueueLOCK) {
            stateLockQueueLOCK.add(0);
            stateLockQueueLOCK.notify();
        }
    }

    /**
     * If the {@link #stateLockQueueLOCK} is not empty, this method is used to
     * remove
     * the first element of the queue.
     * <p>
     * Synchronized on the {@link #stateLockQueueLOCK} object.
     * 
     */
    protected void removeFromStateLockQueue() {
        synchronized (stateLockQueueLOCK) {
            if (stateLockQueueLOCK.isEmpty())
                return;
            stateLockQueueLOCK.poll();
        }
    }

    /**
     * This variable is used to manage the TUI active area selection. Each TUI area
     * has its own
     * Scanner object to read the input. If this queue is not empty, the
     * selected area is the command line input area.
     */
    private volatile Queue<Integer> cmdLineAreaSelectionLOCK = new ArrayDeque<Integer>();

    /**
     * This method is used to add a new element to the
     * {@link #cmdLineAreaSelectionLOCK}
     * and wakes up the {@link #cmdLineReaderTHREAD}.
     * <p>
     * Synchronized on the {@link #cmdLineAreaSelectionLOCK} object.
     * 
     */
    private void addToCmdLineAreaSelection() {
        synchronized (cmdLineAreaSelectionLOCK) {
            cmdLineAreaSelectionLOCK.add(0);
            cmdLineAreaSelectionLOCK.notify();
        }
    }

    /**
     * If the {@link #cmdLineAreaSelectionLOCK} is not empty, this method is used to
     * remove the first element of the queue.
     * <p>
     * Synchronized on the {@link #cmdLineAreaSelectionLOCK} object.
     */
    private void removeFromCmdLineAreaSelection() {
        synchronized (cmdLineAreaSelectionLOCK) {
            if (cmdLineAreaSelectionLOCK.isEmpty())
                return;
            cmdLineAreaSelectionLOCK.poll();
        }
    }

    /**
     * This variable is used to manage the chat active area selection. Each TUI area
     * has its own
     * Scanner object to read the input. If this queue is not empty, the
     * selected area is the chat input area.
     */
    private volatile Queue<Integer> chatAreaSelectionLOCK = new ArrayDeque<Integer>();

    /**
     * This method is used to add a new element to the
     * {@link #chatAreaSelectionLOCK}
     * and
     * wakes up the {@link #chatReaderTHREAD}.
     * <p>
     * Synchronized on the {@link #chatAreaSelectionLOCK} object.
     */
    private void addToChatAreaSelection() {
        synchronized (chatAreaSelectionLOCK) {
            chatAreaSelectionLOCK.add(0);
            chatAreaSelectionLOCK.notify();
        }
    }

    /**
     * If the {@link #chatAreaSelectionLOCK} is not empty, this method is used to
     * remove
     * the first element of the queue.
     * <p>
     * Synchronized on the {@link #chatAreaSelectionLOCK} object.
     */
    private void removeFromChatAreaSelection() {
        synchronized (chatAreaSelectionLOCK) {
            if (chatAreaSelectionLOCK.isEmpty())
                return;
            chatAreaSelectionLOCK.poll();
        }
    }

    /**
     * This variable is used to manage the chat board avoiding to update it every
     * time. Each time a chat needs to be updated, a notify on this lock is called.
     */
    private Object chatNeedsUpdateLOCK = new Object();

    /**
     * This variable is used to inform the {@link #cmdLineReaderTHREAD} that the
     * previously active area was the chat input area. In that case the thread do
     * not wait a command to call {@link #stateNotify()} to get the input again
     * because no command that needs input is executing at the moment.
     */
    private volatile boolean comingFromChat = false;

    /**
     * This variable is used to manage the chat messages. The server object
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage},
     * depending on the
     * way the {@link it.polimi.ingsw.gc31.controller.GameController} constructs it,
     * calls the TUI method
     * {@link #show_chatMessage(String, String)} that adds a
     * formatted String (username: message), or
     * {@link #show_privateChatMessage(String, String, String)} that adds only for
     * the specified client's tui a
     * formatted String ("[From " username"]": message) to this queue.
     * 
     * <p>
     * The {@link #chatBoardThread} access this queue to print the messages.
     */
    private Queue<String> chatMessages = new ArrayDeque<String>();
    /**
     * This variable is used to manage the command line output messages.
     * <p>
     * Instead of printing the messages directly to the system output, each message
     * to be printed by the tui
     * must be added to this queue.
     * <p>
     * The {@link #cmdLineOutTHREAD} thread
     * will print
     * them to the right position of the console.
     */
    private final Queue<String> cmdLineOutLOCK = new ArrayDeque<String>();
    /**
     * This variable is used to manage the command line input messages.
     * <p>
     * The {@link #cmdLineReaderTHREAD} reads the input from the system
     * input and adds
     * it to this queue.
     * <p>
     * The {@link #cmdLineProcessTHREAD} reads the messages from this
     * queue and processes them.
     */
    protected final Queue<String> cmdLineMessagesLOCK = new ArrayDeque<String>();
    /**
     * This queue is used to manage the play area update.
     * <p>
     * The {@link #playViewUpdateTHREAD} take the play view updates to print from
     * this queue.
     */
    private final Queue<StringBuilder> playViewUpdateLOCK = new ArrayDeque<StringBuilder>();

    /**
     * This thread is used to process the commands in the
     * {@link #cmdLineMessagesLOCK} queue. This threads waits on the
     * {@link #cmdLineMessagesLOCK} queue if contains no elements.
     * Each new elements added to the queue wakes up the thread.
     * 
     * <p>
     * The commands are processed by the {@link #execute_command(String)} method.
     * <p>
     * The thread is started by the {@link #cmdLineReaderTHREAD}.
     */
    Thread cmdLineProcessTHREAD = new Thread(() -> {
        commandToProcess(TUIstateCommands.SET_USERNAME, false);
        while (true) {
            String cmd = null;
            synchronized (cmdLineMessagesLOCK) {
                while (cmdLineMessagesLOCK.isEmpty()) {
                    try {
                        cmdLineMessagesLOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                cmd = cmdLineMessagesLOCK.poll();
                if (cmd != null) {
                    execute_command(cmd);
                }
            }
        }
    });

    /**
     * This method searches for the command in the state's commands map and executes
     * it.
     * <p>
     * Corner cases:
     * <ul>
     * <li>If the command is wrong, it execute
     * {@link TUIstate#command_invalidCommand()}
     * <li>If the command is empty, it execute
     * {@link TUIstate#command_showCommandsInfo()} and
     * {@link TUIstate#stateNotify()}
     * <li>If the command is {@link TUIstateCommands#NOTIFY}, it execute
     * {@link TUIstate#stateNotify()}
     * <li>If the command is {@link TUIstateCommands#RECONNECT}, it execute
     * {@link TUIstate#reconnect()}
     * </ul>
     * 
     * @param command
     */
    private void execute_command(String command) {
        if (command.isEmpty()) {
            state.command_showCommandsInfo();
            state.stateNotify();
        } else if (state.commandsMap.containsKey(command)) {
            state.commandsMap.get(command).run();
        } else if (command.equals(TUIstateCommands.SET_USERNAME.toString()) && state.stateName.equals("Init State")) {
            state.setUsername();
        } else if (command.equals(TUIstateCommands.NOTIFY.toString())) {
            state.stateNotify();
        } else if (command.equals(TUIstateCommands.RECONNECT.toString()) && state.stateName.equals("Init State")) {
            state.reconnect();
            // } else if (command.equals(TUIstateCommands.REFRESH.toString()) &&
            // state.stateName.equals("Init State")) {
            // state.command_refresh();
            // state.stateNotify();
            // } else if (command.equals(TUIcommands.ANOTHERMATCH.toString()) &&
            // state.stateName.equals("Playing State")) {
            // state.reMatch();
        } else {
            state.commandsMap.get(TUIstateCommands.INVALID.toString()).run();
        }
    }

    /**
     * Sends the corresponding TUIcommand to be executed to the ProcessThread and
     * notify the commands queue. It sends the commands by adding them to the
     * {@link #cmdLineMessagesLOCK} queue.
     *
     * @param cmd         The TUI command to process.
     * @param stateNotify A boolean indicating whether to call
     *                    {@link TUIstate#stateNotify()} or not.
     */
    protected void commandToProcess(TUIstateCommands cmd, boolean stateNotify) {
        synchronized (cmdLineMessagesLOCK) {
            if (!stateNotify) {
                cmdLineMessagesLOCK.add(cmd.toString());
                cmdLineMessagesLOCK.notify();
            } else {
                cmdLineMessagesLOCK.add(cmd.toString());
                cmdLineMessagesLOCK.add(TUIstateCommands.NOTIFY.toString());
                cmdLineMessagesLOCK.notify();
            }
        }
    }

    /**
     * Indicates if the {@link #statusBarTHREAD} has already printed the chat
     * notification
     */
    private volatile boolean chatNotification = false;
    /**
     * Indicates if there is a new chat message
     */
    private volatile boolean newChatMessage = false;
    /**
     * Indicates if the heart beat is still being received
     */
    private volatile boolean heartBeatReceived = false;
    /**
     * Indicates if the {@link #statusBarTHREAD} has already printed the heart beat
     */
    private volatile boolean HBprinted = false;

    /**
     * This object is used to synchronize the status bar thread and the main thread.
     * 
     */
    private final Object statusBarLOCK = new Object();
    /**
     * This thread is used to print the heart beat status and the chat notification.
     * <p>
     * When it starts, it prints the heart beat disconnected status and waits for
     * the
     * {@link #statusBarLOCK} object.
     * <p>
     * If the heart beat is not received, it prints the heart beat disconnected.
     * The heart beat is received by the {@link #show_heartBeat()} method that sets
     * the {@link #heartBeatReceived} boolean to true.
     * {@link #show_heartBeat()} also prints the status only if the heart beat is
     * not already printed checking the {@link #HBprinted} boolean.
     * <p>
     * The same happens for the chat notification. The chat notification is received
     * by the {@link #show_chatMessage(String, String)} or
     * {@link #show_privateChatMessage(String, String, String)} methods that sets
     * the {@link #newChatMessage} boolean to true. The chat notification is printed
     * only if the {@link #chatNotification} is false.
     * 
     */
    Thread statusBarTHREAD = new Thread(() -> {
        StringBuilder heart = new StringBuilder();
        heart.append(Ansi.ansi().cursor(1, 17).a("💔"));
        while (true) {
            synchronized (statusBarLOCK) {
                try {
                    statusBarLOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!chatNotification & newChatMessage) {
                    StringBuilder chatNotify = new StringBuilder();
                    chatNotify.append(Ansi.ansi().cursor(1, 20).a("🔵-> New Chat Messages").toString());
                    System.out.println(chatNotify);
                    chatNotification = true;
                    resetCursor();

                }
                if (!heartBeatReceived) {
                    heart = new StringBuilder();
                    heart.append(Ansi.ansi().cursor(1, 17).a("💔"));
                    synchronized (statusBarLOCK) {
                        System.out.println(heart);
                    }
                    HBprinted = false;
                    resetCursor();
                }

            }
        }

    });

    /**
     * This thread is used to update the status bar every {@link DV#clientHBUpdate}
     */
    Thread timerTHREAD = new Thread(() -> {
        Timer timer = new Timer();
        TimerTask updateStatusBar = new TimerTask() {
            @Override
            public void run() {
                if (!HBprinted) {
                    heartBeatReceived = false;
                    synchronized (statusBarLOCK) {
                        statusBarLOCK.notify();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(updateStatusBar, 0, DV.clientHBUpdate);
    });

    /**
     * This method is called each time the server receives the heart beat from the
     * client.
     * <p>
     * If the heart beat is not received and not already printed, it prints the
     * heart beat received status and updates the {@link #HBprinted} boolean.
     * Then it updates the {@link #heartBeatReceived} boolean to true.
     */
    @Override
    public void show_heartBeat() {

        if (!heartBeatReceived && !HBprinted) {
            StringBuilder heart = new StringBuilder();
            heart.append(Ansi.ansi().cursor(1, 17).a("💚"));
            HBprinted = true;
            System.out.println(heart);
            resetCursor();
        }
        heartBeatReceived = true;
    }

    /**
     * Thread responsible for reading user-executable {@link TUIstateCommands} typed
     * by
     * the user.
     * <ul>
     * <li>It starts the {@link TUI#cmdLineProcessTHREAD}</li>
     * </ul>
     * 
     * Sends the input to the {@link TUI#cmdLineProcessTHREAD} and waits for the
     * command to be executed to get the input again.
     * 
     * <p>
     * Locks description:
     * <ul>
     * <li>{@link TUI#cmdLineAreaSelectionLOCK} blocks the current thread if the TUI
     * cursor is not in the cmdLineArea
     * <li>{@link TUI#stateLOCK} and {@link TUI#stateLockQueueLOCK} blocks the
     * current
     * thread if {@link TUIstate#stateNotify()} has not been called. Two locks are
     * used to ensure that the stateNotify() terminate its execution unblocking the
     * cmdLineReader only when stateLockQueue in not empty meaning it's waiting.
     * </ul>
     * 
     * This Thread also catches the "chat" input to switch to the chat input area.
     * Switching to the chat input area it erase the chat notification and updates
     * the booleans {@link TUI#chatNotification} and {@link TUI#newChatMessage} to
     * false.
     * 
     */
    Thread cmdLineReaderTHREAD = new Thread(() -> {
        cmdLineProcessTHREAD.start();
        ;
        Scanner cmdScanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cmdScanner.close();
        }));
        while (true) {
            synchronized (cmdLineAreaSelectionLOCK) {
                if (cmdLineAreaSelectionLOCK.isEmpty()) {
                    try {
                        cmdLineAreaSelectionLOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (comingFromChat == false) {
                // if a command is running it waits for the command to be finished
                // This is necessary to let each command get its input
                synchronized (stateLOCK) {
                    addToStateLockQueue();
                    try {
                        stateLOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            comingFromChat = false;
            String input = null;
            moveCursorToCmdLine();
            input = cmdScanner.nextLine();
            // Sends the input to the command line process thread and waits for the command
            // to be executed
            if (input != null) {
                if (input.equals("chat")) {
                    // printToCmdLineOut("comando " + input + " eseguito", Ansi.Color.CYAN);
                    printToCmdLineIn("comando chat");

                    /////
                    StringBuilder chatNotify = new StringBuilder();
                    chatNotify.append(
                            Ansi.ansi().cursor(1, 20).a(" ".repeat("🔵-> New Chat Messages".length())).toString());
                    System.out.println(chatNotify);
                    chatNotification = false;
                    newChatMessage = false;

                    ////
                    removeFromCmdLineAreaSelection();
                    comingFromChat = true;
                    addToChatAreaSelection();
                    synchronized (chatNeedsUpdateLOCK) {
                        chatNeedsUpdateLOCK.notify();
                    }
                } else {
                    synchronized (cmdLineMessagesLOCK) {
                        cmdLineMessagesLOCK.add(input.trim());
                        if (input.trim().equals(TUIstateCommands.SHOW_COMMAND_INFO.toString())) {
                            cmdLineMessagesLOCK.add(TUIstateCommands.NOTIFY.toString());
                        }
                        cmdLineMessagesLOCK.notify();
                    }
                }
            }
        }

    });
    /**
     * This thread is used to print the command line output messages the right way
     * and in the right position.
     * It starts the {@link #cmdLineReaderTHREAD} to get the input.
     * It synchronizes on the {@link #cmdLineOutLOCK} object to wait for the
     * messages
     * to be updated.
     */
    Thread cmdLineOutTHREAD = new Thread(() -> {
        while (true) {
            synchronized (cmdLineOutLOCK) {
                if (cmdLineOutLOCK.isEmpty()) {
                    try {
                        print_CmdLineBorders();
                        cmdLineAreaSelectionLOCK.add(0);
                        cmdLineReaderTHREAD.start();
                        cmdLineOutLOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    print_CmdLineBorders();
                    updateCmdLineOut();
                }
            }
        }
    });

    /**
     * This method is used to update the command line output messages.
     * <p>
     * It prints the messages in the right position of the console.
     * <p>
     * It also manages the queue of the messages.
     */
    private void updateCmdLineOut() {
        synchronized (cmdLineOutLOCK) {
            // printa i messaggi
            for (int i = 0; i < cmdLineOutLOCK.size() && i < CMD_LINE_OUT_LINES; i++) {
                AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW - 1 - i, CMD_LINE_INPUT_COLUMN)
                        .a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
                AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW - 1 - i, CMD_LINE_INPUT_COLUMN + 1)
                        .a(cmdLineOutLOCK.toArray()[cmdLineOutLOCK.size() - 1 - i]));
            }
            // rimuove i messaggi più vecchi
            if (cmdLineOutLOCK.size() > CMD_LINE_OUT_LINES - 1) {
                int i = cmdLineOutLOCK.size() - CMD_LINE_OUT_LINES - 1;
                for (int j = 0; j < i; j++) {
                    cmdLineOutLOCK.poll();
                }
            }
            resetCursor();
            try {
                cmdLineOutLOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The {@link #chatBoardThread} is not active in all the states. When
     * interrupted the Thread variable is set to null.
     * <p>
     * This method is used to rebuild the chat board thread to start it again.
     * 
     * @return
     */
    private Thread chatBoardThreadBuilder() {
        return new Thread(() -> {
            // print_ChatBorders();
            moveCursorToCmdLine();
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (chatNeedsUpdateLOCK) {
                    try {
                        chatNeedsUpdateLOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                print_ChatBorders();
                updateChatBoardOut();
            }
            erase_ChatBoard();
        });

    }

    /**
     * This thread is used to print the chat board messages the right way and in the
     * right position.
     * It synchronizes on the {@link #chatNeedsUpdateLOCK} object to wait for the
     * chat messages to be updated.
     */
    Thread chatBoardThread = chatBoardThreadBuilder();

    /**
     * This method is used to update the chat board output messages.
     * <p>
     * It prints the messages in the right position of the console.
     */
    private void updateChatBoardOut() {
        for (int i = 0; i < CHAT_BOARD_OUT_LINES; i++) {
            AnsiConsole.out()
                    .print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW - 1 - i, CHAT_BOARD_INPUT_COLUMN)
                            .a(" ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH)));
        }
        for (int i = 0; i < chatMessages.size() && i < CHAT_BOARD_OUT_LINES; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW - 1 - i, CHAT_BOARD_INPUT_COLUMN)
                            .a(chatMessages.toArray()[chatMessages.size() - 1 - i]));
        }
        resetCursor();

    }

    /**
     * Update the {@link #chatMessages} queue and notify the
     * {@link #chatBoardThread} to print the messages.
     * The notify is done through the {@link #chatNeedsUpdateLOCK} object.
     * <p>
     * It also notifies the {@link #statusBarTHREAD} to print the chat notification
     * if needed.
     * <p>
     * This method is invoked by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage}
     * 
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username the username of the client that sent the message
     * @param message  the message sent
     * 
     */
    @Override
    public void show_chatMessage(String username, String message) {
        synchronized (chatNeedsUpdateLOCK) {
            chatMessages.add(username + ": " + message);

        }
        if (chatAreaSelectionLOCK.isEmpty()) {
            newChatMessage = true;
            if (!chatNotification) {
                synchronized (statusBarLOCK) {
                    statusBarLOCK.notify();
                }
            }
        } else {
            synchronized (chatNeedsUpdateLOCK) {
                chatNeedsUpdateLOCK.notifyAll();
            }
        }
    }

    /**
     * Update the {@link #chatMessages} queue with a private message only if the
     * message is for the client or from the client.
     * Notify the {@link #chatBoardThread} to print the messages.
     * The notify is done through the {@link #chatNeedsUpdateLOCK} object.
     * <p>
     * It also notifies the {@link #statusBarTHREAD} to print the chat notification
     * if needed.
     * <p>
     * This method is invoked by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage}
     * 
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username the username of the client that sent the message
     * @param message  the message sent
     */
    @Override
    public void show_privateChatMessage(String fromUsername, String toUsername, String message) {
        synchronized (chatNeedsUpdateLOCK) {
            if (fromUsername.equals(client.getUsername()))
                chatMessages.add("[To: " + toUsername + "] : " + message);
            else if (toUsername.equals(client.getUsername()))
                chatMessages.add("[From: " + fromUsername + "] : " + message);
            else
                return;
        }
        if (chatAreaSelectionLOCK.isEmpty()) {
            newChatMessage = true;
            if (!chatNotification) {
                synchronized (statusBarLOCK) {
                    statusBarLOCK.notify();
                }
            }
        } else {
            synchronized (chatNeedsUpdateLOCK) {
                chatNeedsUpdateLOCK.notifyAll();
            }
        }
    }

    /**
     * This thread handle the chat input.
     * <p>
     * It waits on the {@link #chatAreaSelectionLOCK} queue if it is empty.
     * Each new element added to the queue wakes up the thread.
     * Based on the input, it calls the corresponding client method to construct the
     * correct
     * {@link it.polimi.ingsw.gc31.client_server.queue.serverQueue.ChatMessageObj}.
     * 
     * @see it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands#sendChatMessage(String,
     *      String)
     * @see it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands#sendChatMessage(String,
     *      String, String)
     */
    Thread chatReaderTHREAD = new Thread(() -> {
        Scanner chatScanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            chatScanner.close();
        }));
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (chatAreaSelectionLOCK) {
                if (chatAreaSelectionLOCK.isEmpty()) {
                    try {
                        chatAreaSelectionLOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            moveCursorToChatLine();
            String input = chatScanner.nextLine();
            Boolean privateMessage = false;
            if (!input.isEmpty()) {
                if (input.equals("ccc")) {
                    forceRefreshTUI(false);
                    removeFromChatAreaSelection();
                    addToCmdLineAreaSelection();
                    moveCursorToCmdLine();
                } else {
                    for (String username : playersUsernames) {
                        if (input.trim().startsWith("/" + username)) {
                            try {
                                String message = input.substring(username.length() + 1).trim();
                                if (!message.isEmpty())
                                    client.sendChatMessage(getClient().getUsername(), username, message);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            privateMessage = true;
                            break;
                        }
                    }
                    if (!privateMessage) {
                        try {
                            client.sendChatMessage(getClient().getUsername(), input.trim());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    });

    /**
     * This thread is used to print the play view updates as soon as they are
     * available.
     * <p>
     * It waits on the {@link #playViewUpdateLOCK} queue if it is empty.
     * Each new element added to the queue wakes up the thread.
     * <p>
     */
    Thread playViewTHREAD = new Thread(() -> {
        while (true) {
            synchronized (playViewUpdateLOCK) {
                while (playViewUpdateLOCK.isEmpty()) {
                    try {
                        playViewUpdateLOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(playViewUpdateLOCK.poll());
                resetCursor();
            }
        }

    });

    // UTILITIES

    /**
     * Starts the TUI: prints the title of the game and then runs in this order the
     * following threads:
     * <ul>
     * <li>{@link #chatReaderTHREAD}
     * <li>{@link #playViewTHREAD}
     * <li>{@link #statusBarTHREAD}
     * <li>{@link #timerTHREAD}
     * <li>{@link #cmdLineOutTHREAD}
     * 
     */
    @Override
    public void runUI() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        AnsiConsole.systemInstall();

        print_Title();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // chatBoardThread.start();
        chatReaderTHREAD.start();
        playViewTHREAD.start();
        statusBarTHREAD.start();
        timerTHREAD.start();
        cmdLineOutTHREAD.start();

    }

    // UPDATES FIELDS & METHODS

    /**
     * Shows the available games to the client
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowGamesObj}
     * object
     * <p>
     * Unblock the tui sending the command {@link TUIstateCommands#NOTIFY} to che
     * {@link #cmdLineProcessTHREAD}
     * 
     * @param listGame the list of available games provided by the
     *                 {@link it.polimi.ingsw.gc31.controller.Controller}
     */
    @Override
    public void show_listGame(List<String> listGame) {
        printToCmdLineOut(serverWrite(">>Game List<<"));
        for (String string : listGame) {
            printToCmdLineOut(string);
        }
        commandToProcess(TUIstateCommands.NOTIFY, false);

    }

    /**
     * Receives the play areas and the achieved resources of all the players,
     * selects the correct ones checking the {@link #activePlayArea} and creates the
     * corresponding {@link StringBuilder}.
     * Then sends the {@link StringBuilder} to the {@link #playViewTHREAD} adding
     * it to the {@link #playViewUpdateLOCK} queue. It calls notify on the
     * {@link #playViewUpdateLOCK} queue to print the new play view updates.
     * Also updates the corresponding {@link #areasCache} with the new play area.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayAreaObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username          the username of the player
     * @param playArea          all the play areas of the players
     * @param achievedResources the achieved resources of each player
     * 
     */
    @Override
    public void show_playArea(String username, LinkedHashMap<Point, PlayableCard> playArea,
            Map<Resources, Integer> achievedResources) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(PLAYAREA_INITIAL_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW, PLAYAREA_END_COLUMN));
        res.append(print_Borders("Play Area: " + username, greyText, PLAYAREA_INITIAL_ROW,
                PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW,
                PLAYAREA_END_COLUMN));
        res.append(print_PlacedCards(playArea));
        placedCards = playArea;

        if (achievedResources != null) {
            res.append(clearArea(ACHIEVED_RESOURCES_INITIAL_ROW, ACHIEVED_RESOURCES_INITIAL_COLUMN,
                    ACHIEVED_RESOURCES_END_ROW, ACHIEVED_RESOURCES_END_COLUMN));
            res.append(print_Borders("", greyText, ACHIEVED_RESOURCES_INITIAL_ROW,
                    ACHIEVED_RESOURCES_INITIAL_COLUMN,
                    ACHIEVED_RESOURCES_END_ROW, ACHIEVED_RESOURCES_END_COLUMN));
            int index = 0;
            for (Map.Entry<Resources, Integer> entry : achievedResources.entrySet()) {
                res.append(ansi()
                        .cursor(ACHIEVED_RESOURCES_INITIAL_ROW + 1 + index, ACHIEVED_RESOURCES_INITIAL_COLUMN + 1)
                        .a(entry.getKey().getSymbol() + ": " + entry.getValue()));
                index++;
            }
        }

        playAreaAllPlayers.put(username, res);

        if (activePlayArea.equals(username)) {
            synchronized (playViewUpdateLOCK) {
                playViewUpdateLOCK.add(res);
                playViewUpdateLOCK.notify();
            }
            areasCache.put(TUIareas.PLAY_VIEW_AREA, res);
        }

        // }
    }

    /**
     * Receives the score of all the players, constructs the corresponding
     * {@link StringBuilder}
     * and sends it to the {@link #playViewTHREAD} adding it to the
     * {@link #playViewUpdateLOCK} queue.
     * Calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view updates.
     * updates the corresponding {@link #areasCache} with the new score.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowScorePlayerObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * 
     */
    @Override
    public void show_scorePlayer(LinkedHashMap<String, Pair<Integer, Boolean>> scores) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(PLAYERS_INFO_INITIAL_ROW, PLAYERS_INFO_INITIAL_COLUMN, PLAYERS_INFO_END_ROW,
                PLAYERS_INFO_END_COLUMN));
        res.append(print_Borders("Players info", greyText, PLAYERS_INFO_INITIAL_ROW,
                PLAYERS_INFO_INITIAL_COLUMN,
                PLAYERS_INFO_END_ROW, PLAYERS_INFO_END_COLUMN));
        int index = 1;
        for (String player : scores.keySet()) {
            String inTurn = scores.get(player).getValue() ? "\uD83D\uDFE2" : "  ";
            res.append(ansi().cursor(PLAYERS_INFO_INITIAL_ROW + index, PLAYERS_INFO_INITIAL_COLUMN + 1)
                    .a(inTurn + " " + player + ": " + scores.get(player).getKey()));
            index++;
        }

        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }
        areasCache.put(TUIareas.PLAYERS_INFO, res);

    }

    /**
     * Updates the TUIstate {@link #state} to the {@link PlayingState} and sends the
     * command {@link TUIstateCommands#SHOW_COMMAND_INFO} to the
     * {@link #cmdLineProcessTHREAD} to show the available commands.
     *
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.StartGameObj}
     * sent by the {@link it.polimi.ingsw.gc31.controller.GameController#ready()}.
     * <p>
     * The flow that triggers this method starts from the
     * {@link JoinedToGameState#command_ready()} that calls the
     * {@link TUIstate#stateNotify()} so the TUI is already unblocked.
     */
    @Override
    public void update_ToPlayingState() {
        this.state = new PlayingState(this);
        commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, false);
        // Here, state notify is not needed because it is already called by the
        // TUIcommands that triggers this update

    }

    /**
     * Receives the first card of the gold decks and the two gold cards that are
     * shown to the
     * players, constructs the corresponding {@link StringBuilder} and sends it to
     * the {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK}
     * queue.
     * Calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view updates.
     * updates the corresponding {@link #areasCache} with the new decks.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowGoldDeckObj}
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param firstCardDeck the first card of the gold deck
     * @param card1         the first gold card shown to the players
     * @param card2         the second gold card shown to the players
     */
    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        StringBuilder res = print_Deck("Gold Deck", greyText, firstCardDeck, card1, card2,
                GOLD_DECK_INITIAL_ROW,
                GOLD_DECK_INITIAL_COLUMN, GOLD_DECK_END_ROW, GOLD_DECK_END_COLUMN);
        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }
        areasCache.put(TUIareas.GOLD_DECK, res);

    }

    /**
     * Receives the first card of the resource decks and the two resource cards that
     * are shown to the
     * players, constructs the corresponding {@link StringBuilder} and sends it to
     * the {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK}
     * queue.
     * Calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view updates.
     * updates the corresponding {@link #areasCache} with the new decks.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowResourceDeckObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param firstCardDeck the first card of the resource deck
     * @param card1         the first resource card shown to the players
     * @param card2         the second resource card shown to the players
     */
    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        StringBuilder res = print_Deck("Resource Deck", greyText, firstCardDeck, card1, card2,
                RESOURCE_DECK_INITIAL_ROW,
                RESOURCE_DECK_INITIAL_COLUMN, RESOURCE_DECK_END_ROW, RESOURCE_DECK_END_COLUMN);
        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }
        areasCache.put(TUIareas.RES_DECK, res);
    }

    /**
     * Receives the username of the player, its hand and the index of the selected
     * card.
     * If the username is the same as the client's username:
     * constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue;
     * calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates;
     * updates the corresponding {@link #areasCache} with the new hand.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowHandPlayerObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username     the username of the player
     * @param hand         the hand of the player
     * @param selectedCard the index of the selected card
     * 
     * 
     */
    @Override
    public void show_handPlayer(String username, List<PlayableCard> hand, int selectedCard) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            int index = 0;
            res.append(clearArea(HAND_INITIAL_ROW, HAND_INITIAL_COLUMN, HAND_END_ROW, HAND_END_COLUMN));
            res.append(print_Borders("HAND: " + username, greyText, HAND_INITIAL_ROW,
                    HAND_INITIAL_COLUMN, HAND_END_ROW,
                    HAND_END_COLUMN));
            for (PlayableCard card : hand) {
                res.append(print_PlayableCard(card, HAND_INITIAL_COLUMN + 1 + (CARD_LENGTH + 1) * index,
                        HAND_INITIAL_ROW + 1, HAND_INITIAL_ROW, HAND_END_ROW, HAND_INITIAL_COLUMN, HAND_END_COLUMN));
                if (selectedCard == index) {
                    res.append(ansi().cursor(HAND_END_ROW - 1, HAND_INITIAL_COLUMN + 1 + (CARD_LENGTH + 1) * index)
                            .bg(GREEN).a(" ".repeat(CARD_LENGTH)).reset());
                    res.append(ansi()
                            .cursor(HAND_END_ROW - 1,
                                    HAND_INITIAL_COLUMN + 1 + CARD_LENGTH / 2 + (CARD_LENGTH + 1) * index)
                            .fg(WHITE).bg(GREEN).a(index + 1).reset());
                } else {
                    res.append(ansi().cursor(HAND_END_ROW - 1, HAND_INITIAL_COLUMN + 1 + (CARD_LENGTH + 1) * index)
                            .a(" ".repeat(CARD_LENGTH)));
                    res.append(ansi()
                            .cursor(HAND_END_ROW - 1,
                                    HAND_INITIAL_COLUMN + 1 + CARD_LENGTH / 2 + (CARD_LENGTH + 1) * index)
                            .fg(WHITE).a(index + 1));
                }
                index++;
            }
            synchronized (playViewUpdateLOCK) {
                playViewUpdateLOCK.add(res);
                playViewUpdateLOCK.notify();
            }
            areasCache.put(TUIareas.HAND, res);

        }
    }

    /**
     * Receives the username of the player and the objective card.
     * If the username is the same as the client's username:
     * constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue;
     * calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates;
     * updates the corresponding {@link #areasCache} with the new personal objective
     * card.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowSecretObjectiveCardObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username      the username of the player
     * @param objectiveCard the objective card
     */
    @Override
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(clearArea(OBJECTIVE_INITIAL_ROW + 1, OBJECTIVE_INITIAL_COLUMN, OBJECTIVE_END_ROW,
                    OBJECTIVE_END_COLUMN));
            res.append(ansi().cursor(OBJECTIVE_INITIAL_ROW, OBJECTIVE_INITIAL_COLUMN + 1).a("Your Objective Card"));
            res.append(print_ObjectiveCard(objectiveCard, OBJECTIVE_INITIAL_COLUMN + 1, OBJECTIVE_INITIAL_ROW + 1,
                    OBJECTIVE_INITIAL_ROW, OBJECTIVE_END_ROW, OBJECTIVE_INITIAL_COLUMN, OBJECTIVE_END_COLUMN));
            synchronized (playViewUpdateLOCK) {
                playViewUpdateLOCK.add(res);
                playViewUpdateLOCK.notify();
            }
            areasCache.put(TUIareas.OBJ, res);
        }
    }

    /**
     * Receives the username of the player and the two personal objective card
     * alternatives.
     * If the username is the same as the client's username: t constructs the
     * corresponding {@link StringBuilder}
     * and sends it to the {@link #playViewTHREAD} adding it to the
     * {@link #playViewUpdateLOCK} queue;
     * calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view updates;
     * updates the corresponding {@link #areasCache} with the new personal objective
     * card
     * alternatives.
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username       the username of the player
     * @param objectiveCard1 the first objective card
     * @param objectiveCard2 the second objective card
     */
    @Override
    public void show_chooseObjectiveCard(String username, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(
                    clearArea(CHOOSE_OBJECTIVE_INITIAL_ROW, CHOOSE_OBJECTIVE_INITIAL_COLUMN, CHOOSE_OBJECTIVE_END_ROW,
                            CHOOSE_OBJECTIVE_END_COLUMN));
            res.append(print_Borders("Choose Objective Card:", greyText, CHOOSE_OBJECTIVE_INITIAL_ROW,
                    CHOOSE_OBJECTIVE_INITIAL_COLUMN, CHOOSE_OBJECTIVE_END_ROW, CHOOSE_OBJECTIVE_END_COLUMN));
            res.append(print_ObjectiveCard(objectiveCard1, CHOOSE_OBJECTIVE_INITIAL_COLUMN + 1,
                    CHOOSE_OBJECTIVE_INITIAL_ROW + 1, CHOOSE_OBJECTIVE_INITIAL_ROW, CHOOSE_OBJECTIVE_END_ROW,
                    CHOOSE_OBJECTIVE_INITIAL_COLUMN, CHOOSE_OBJECTIVE_END_COLUMN));
            res.append(
                    ansi().cursor(CHOOSE_OBJECTIVE_INITIAL_ROW + 1 + CARD_HEIGHT, CHOOSE_OBJECTIVE_INITIAL_COLUMN + 2)
                            .a("Objective Card 1"));
            res.append(print_ObjectiveCard(objectiveCard2, CHOOSE_OBJECTIVE_INITIAL_COLUMN + 1,
                    CHOOSE_OBJECTIVE_INITIAL_ROW + 2 + CARD_HEIGHT, CHOOSE_OBJECTIVE_INITIAL_ROW,
                    CHOOSE_OBJECTIVE_END_ROW,
                    CHOOSE_OBJECTIVE_INITIAL_COLUMN, CHOOSE_OBJECTIVE_END_COLUMN));
            res.append(
                    ansi().cursor(CHOOSE_OBJECTIVE_INITIAL_ROW + 2 + CARD_HEIGHT * 2,
                            CHOOSE_OBJECTIVE_INITIAL_COLUMN + 2)
                            .a("Objective Card 2"));
            synchronized (playViewUpdateLOCK) {
                playViewUpdateLOCK.add(res);
                playViewUpdateLOCK.notify();
            }
            // FIXME problema per quando iniziano i turni della partita, l'area cache di
            // choose objective card non deve venire ristampata
            areasCache.put(TUIareas.CHOSE_OBJ, res);
        }
    }

    /**
     * Receives the two common objective cards.
     * Constructs the corresponding {@link StringBuilder}; sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue;
     * calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view updates; updates the corresponding {@link #areasCache} with the new
     * common objective
     * cards.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowCommonObjectiveCardObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param card1 the first common objective card
     * @param card2 the second common objective card
     * 
     */
    @Override
    public void show_commonObjectiveCard(ObjectiveCard card1, ObjectiveCard card2) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(COMMON_OBJECTIVE_INITIAL_ROW + 1, COMMON_OBJECTIVE_INITIAL_COLUMN,
                COMMON_OBJECTIVE_END_ROW, COMMON_OBJECTIVE_END_COLUMN));
        res.append(
                ansi().cursor(COMMON_OBJECTIVE_INITIAL_ROW, COMMON_OBJECTIVE_INITIAL_COLUMN + 1).a("COMMON OBJECTIVE"));
        if (card1 != null) {
            res.append(print_ObjectiveCard(card1, COMMON_OBJECTIVE_INITIAL_COLUMN + 1,
                    COMMON_OBJECTIVE_INITIAL_ROW + 1, COMMON_OBJECTIVE_INITIAL_ROW, COMMON_OBJECTIVE_END_ROW,
                    COMMON_OBJECTIVE_INITIAL_COLUMN, COMMON_OBJECTIVE_END_COLUMN));
        }
        if (card2 != null) {
            res.append(print_ObjectiveCard(card2, COMMON_OBJECTIVE_INITIAL_COLUMN + 1 + (CARD_LENGTH + 1),
                    COMMON_OBJECTIVE_INITIAL_ROW + 1, COMMON_OBJECTIVE_INITIAL_ROW, COMMON_OBJECTIVE_END_ROW,
                    COMMON_OBJECTIVE_INITIAL_COLUMN, COMMON_OBJECTIVE_END_COLUMN));
        }
        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }
        areasCache.put(TUIareas.COMMON_OBJ, res);
    }

    /**
     * Receives the username of the player and the starter card.
     * If the username is the same as the client's username:
     * constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue;
     * calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates;
     * updates the corresponding {@link #areasCache} with the new starter card.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowStarterCardObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username    the username of the player
     * @param starterCard the starter card
     * 
     */
    @Override
    public void show_starterCard(String username, PlayableCard starterCard) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(clearArea(STARTER_CARD_INITIAL_ROW, STARTER_CARD_INITIAL_COLUMN, STARTER_CARD_END_ROW,
                    STARTER_CARD_END_ROW));
            res.append(ansi().cursor(STARTER_CARD_INITIAL_ROW, STARTER_CARD_INITIAL_COLUMN + 1).a("Starter Card:"));
            res.append(print_PlayableCard(starterCard, STARTER_CARD_INITIAL_COLUMN + 1,
                    STARTER_CARD_INITIAL_ROW + 1, STARTER_CARD_INITIAL_ROW, STARTER_CARD_END_ROW,
                    STARTER_CARD_INITIAL_COLUMN, STARTER_CARD_END_COLUMN));

            synchronized (playViewUpdateLOCK) {
                playViewUpdateLOCK.add(res);
                playViewUpdateLOCK.notify();
            }
            areasCache.put(TUIareas.STARTER, res);

        }
    }

    /**
     * Receives the accepter username of the player, prints the response message to
     * the command line out and set the
     * client's {@link Client#username} with the accepted username.
     * It also sets the {@link #activePlayArea} with the accepted username.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ValidUsernameObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param username the accepted username
     */
    @Override
    public void show_validUsername(String username) {
        printToCmdLineOut(serverWrite("Username accepted"));
        printToCmdLineOut(tuiWrite("Your name is: " + username));
        client.setUsername(username);
        this.activePlayArea = username;
        commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, true);

    }

    /**
     * Receives the username of the player, prints the response message to the
     * command line out.
     * Recall the {@link TUIstate#setUsername()} to let the client insert a new
     * name.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.WrongUsernameObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param username the wrong username
     */
    @Override
    public void show_wrongUsername(String username) {
        printToCmdLineOut(serverWrite("Username " + username + " already taken, try again"));
        commandToProcess(TUIstateCommands.SET_USERNAME, false);

    }

    /**
     * Prints the game the client joined to and its id to the command line out.
     * Starts the {@link #chatBoardThread}.
     * Updates the {@link #state} to the {@link JoinedToGameState}.
     * Sends the command {@link TUIstateCommands#SHOW_COMMAND_INFO} to the
     * {@link #cmdLineProcessTHREAD} to show the available commands.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.JoinedToGameObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param id                 the id of the game the client joined to
     * @param maxNumberOfPlayers the maximum number of players of the game
     */
    @Override
    public void show_joinedToGame(int id, int maxNumberOfPlayers) {
        printToCmdLineOut(serverWrite("Joined to game: " + id));
        chatBoardThread = chatBoardThreadBuilder();
        chatBoardThread.start();
        state = new JoinedToGameState(this);
        commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, true);

    }

    /**
     * Change the {@link #state} to the {@link InitState}.
     * Interrupts the {@link #chatBoardThread}.
     * Clears the {@link #chatMessages} queue.
     * Sends the command {@link TUIstateCommands#SHOW_COMMAND_INFO} to the
     * {@link #cmdLineProcessTHREAD} to show the available commands.
     * Clears the {@link #areasCache}.
     * Sends the command {@link TUIstateCommands#REFRESH} to the
     * {@link #cmdLineProcessTHREAD} to refresh the TUI.
     * Prints the quit message to the command line out.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.QuitFromGameObj}
     */
    @Override
    public void show_quitFromGame() {
        state = new InitState(this);
        chatBoardThread.interrupt();
        chatMessages = new ArrayDeque<String>();
        commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, false);
        areasCache.clear(); // TODO da testare
        commandToProcess(TUIstateCommands.REFRESH, false);
        printToCmdLineOut(serverWrite("You quit the game"));
    }

    /**
     * Prints the message to the command line out.
     * Sends the command {@link TUIstateCommands#NOTIFY} to the
     * {@link #cmdLineProcessTHREAD} to unblock the TUI.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameIsFullObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param id the id of the game that is full
     * 
     */
    @Override
    public void show_gameIsFull(int id) {
        printToCmdLineOut(serverWrite(serverWrite("Game " + id + " is full")));
        commandToProcess(TUIstateCommands.NOTIFY, false);

    }

    /**
     * Prints the message to the command line out.
     * Starts the {@link #chatBoardThread}.
     * Updates the {@link #state} to the {@link JoinedToGameState}.
     * Sends the command {@link TUIstateCommands#SHOW_COMMAND_INFO} to the
     * {@link #cmdLineProcessTHREAD} to show the available commands.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameCreatedObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param gameID the id of the game created
     *
     */
    @Override
    public void show_gameCreated(int gameID) {
        printToCmdLineOut(serverWrite("New game created with ID: " + gameID));
        chatBoardThread = chatBoardThreadBuilder();
        chatBoardThread.start();
        state = new JoinedToGameState(this);
        commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, true);

    }

    /**
     * Unimplemented method for the TUI
     */
    @Override
    public void show_readyStatus(String username, boolean status) {
    }

    /**
     * Prints the message to the command line out.
     * Sends the command {@link TUIstateCommands#NOTIFY} to the
     * {@link #cmdLineProcessTHREAD} to unblock the TUI.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameDoesNotExistObj}
     * 
     */
    @Override
    public void show_gameDoesNotExist() {
        printToCmdLineOut(serverWrite("Game does not exist"));
        commandToProcess(TUIstateCommands.NOTIFY, false);
    }

    /**
     * Prints the message to the command line out.
     * Sends the command {@link TUIstateCommands#NOTIFY} to the
     * {@link #cmdLineProcessTHREAD} to unblock the TUI.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameAlreadyExistsObj}
     */
    @Override
    public void show_wrongGameSize() {
        printToCmdLineOut(serverWrite("Game size must be between 2 and 4"));
        commandToProcess(TUIstateCommands.NOTIFY, false);

    }

    /**
     * Receives the username of the player and the player info about is
     * {@link it.polimi.ingsw.gc31.model.player.PlayerState}
     * If the username is the same as the client's username:
     * constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue;
     * calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates;
     * updates the corresponding {@link #areasCache} with the new player state.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayerTurnObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username the username of the player
     * @param info     the player info about the
     *                 {@link it.polimi.ingsw.gc31.model.player.PlayerState}
     * 
     */
    @Override
    public void show_playerTurn(String username, String info) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(ansi().cursor(PLAYAREA_END_ROW + 1, PLAYAREA_INITIAL_COLUMN + 1)
                    .a(" ".repeat(PLAYAREA_END_COLUMN - PLAYAREA_INITIAL_COLUMN)));
            res.append(ansi().cursor(PLAYAREA_END_ROW + 1, PLAYAREA_INITIAL_COLUMN + 1).a(info));
            synchronized (playViewUpdateLOCK) {
                playViewUpdateLOCK.add(res);
                playViewUpdateLOCK.notify();
            }
            areasCache.put(TUIareas.PLAYER_STATE, res);
        }
    }

    /**
     * Receives a map with the players and their status.
     * Constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue.
     * Calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates.
     * Updates the corresponding {@link #areasCache} with the new players info.
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowInGamePlayerObj}
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param players a map with the players and their status
     *
     */
    @Override
    public void show_inGamePlayers(LinkedHashMap<String, Boolean> players) {
        playersUsernames = players.keySet().stream().toList();
        StringBuilder res = new StringBuilder();
        res.append(clearArea(PLAYERS_INFO_INITIAL_ROW, PLAYERS_INFO_INITIAL_COLUMN, PLAYERS_INFO_END_ROW,
                PLAYERS_INFO_END_COLUMN));
        res.append(print_Borders("Players info", greyText, PLAYERS_INFO_INITIAL_ROW, PLAYERS_INFO_INITIAL_COLUMN,
                PLAYERS_INFO_END_ROW, PLAYERS_INFO_END_COLUMN));
        int index = 1;
        for (String player : players.keySet()) {
            res.append(ansi().cursor(PLAYERS_INFO_INITIAL_ROW + index, PLAYERS_INFO_INITIAL_COLUMN + 1)
                    .a(player + ": " + players.get(player)));
            index++;
        }

        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }

        areasCache.put(TUIareas.PLAYERS_INFO, res);

    }

    /**
     * Receives the message to print to the command line out.
     * The message provide a short description of the invalid action performed.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.InvalidActionObj}
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param message the message to print to the command line out
     * 
     */
    @Override
    public void show_invalidAction(String message) {
        printToCmdLineOut(serverWrite(message));
    }

    /**
     * Receives the username of the player and a map with the players and their
     * score.
     * Constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue.
     * Calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameIsOverObj}
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param username     the username of the player
     * @param playersScore a map with the players and their score
     */
    @Override
    public void show_GameIsOver(String username, Map<String, Integer> playersScore) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(GAME_OVER_INITIAL_ROW, GAME_OVER_INITIAL_COLUMN, GAME_OVER_END_ROW,
                GAME_OVER_END_COLUMN));
        res.append(print_Borders("", greyText, GAME_OVER_INITIAL_ROW, GAME_OVER_INITIAL_COLUMN,
                GAME_OVER_END_ROW, GAME_OVER_END_COLUMN));

        if (client.getUsername().equals(username)) {
            res.append(ansi().cursor(GAME_OVER_INITIAL_ROW + 1, GAME_OVER_INITIAL_COLUMN + 1).a("You are the winner"));
        } else {
            res.append(ansi().cursor(GAME_OVER_INITIAL_ROW + 1, GAME_OVER_INITIAL_COLUMN + 1).a("You lost!"));
        }

        int index = 1;
        for (String player : playersScore.keySet()) {
            res.append(ansi().cursor(GAME_OVER_INITIAL_ROW + 1 + index, GAME_OVER_INITIAL_COLUMN + 1)
                    .a(player + ": " + playersScore.get(player)));
            index++;
        }

        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }
    }

    /**
     * Receives the token to set to the client and a boolean to indicates if the
     * token is temporary or must be saved.
     * Calls the {@link Client#setToken(int, boolean)} to set the token.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.SendTokenObj}
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param token     the token to set to the client
     * @param temporary a boolean to indicates if the token is temporary or must be
     *                  saved
     * 
     * 
     */
    @Override
    public void receiveToken(int token, boolean temporary) {
        client.setToken(token, temporary);
    }

    /**
     * Receives the message to print to the command line out.
     */
    @Override
    public void show_GenericClientResponse(String response) {
        printToCmdLineOut(tuiWrite(response));

    }

    /**
     * Receives the username of the player.
     * Set the client username with the received username.
     * Updates the {@link #activePlayArea} with the received username.
     * Sends the {@link TUIstateCommands#RECONNECT} command to the
     * {@link #cmdLineProcessTHREAD}.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.WantReconnectObj}
     * 
     * <p>
     * The parameter is provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param username the username of the player
     */
    @Override
    public void show_wantReconnect(String username) {
        getClient().setUsername(username);
        activePlayArea = username;
        commandToProcess(TUIstateCommands.RECONNECT, false);
    }

    /**
     * Receives the result of the rejoin request and a list with the players in the
     * game.
     * If the result is true, starts the {@link #chatBoardThread}.
     * Updates the {@link #state} to the {@link PlayingState}.
     * Sends the {@link TUIstateCommands#SHOW_COMMAND_INFO} command to the
     * {@link #cmdLineProcessTHREAD} to show the available commands.
     * If the result is false, sets the client token with the default token and
     * sends the {@link TUIstateCommands#SET_USERNAME} command to the
     * {@link #cmdLineProcessTHREAD} to allow the client to insert a new username.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.RejoinObj}
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.Controller}
     * 
     * @param result  the result of the rejoin request
     * @param players a list with the players in the game
     * 
     */
    @Override
    public void show_rejoined(boolean result, List<String> players) {
        if (result) {
            chatBoardThread = chatBoardThreadBuilder();
            chatBoardThread.start();
            state = new PlayingState(this);
            commandToProcess(TUIstateCommands.SHOW_COMMAND_INFO, true);
        } else {
            getClient().getToken().setToken(DV.defaultToken);
            commandToProcess(TUIstateCommands.SET_USERNAME, false);
        }
    }

    /**
     * Prints the message to the command line out.
     * Sets the client token with the default token.
     * Sends the {@link TUIstateCommands#SET_USERNAME} command to the
     * {@link #cmdLineProcessTHREAD} to allow the client to insert a new username.
     * This method is called when the client has a token saved locally that do not
     * correspond to a disconnected user on the server side.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.WrongUsernameObj}
     * constructed with a null username.
     * 
     */
    @Override
    public void show_unableToReconnect() {
        printToCmdLineOut(serverWrite("U were not in a game!"));
        client.getToken().setToken(DV.defaultToken);
        commandToProcess(TUIstateCommands.SET_USERNAME, false);

    }

    // @Override
    // public void show_requestAnotherMatch() {
    // printToCmdLineOut(tuiWrite("Do you want to play another match?"));
    // commandToProcess(TUIstateCommands.ANOTHERMATCH, false);
    // }

    /**
     * Receives the seconds left to the start of the game.
     * Constructs the corresponding {@link StringBuilder} and sends it to the
     * {@link #playViewTHREAD} adding it to the {@link #playViewUpdateLOCK} queue.
     * Calls notify on the {@link #playViewUpdateLOCK} queue to print the new play
     * view
     * updates.
     * 
     * <p>
     * This method is called by the
     * {@link it.polimi.ingsw.gc31.client_server.queue.clientQueue.TimerLastPlayerConnectedObj}
     * 
     * <p>
     * All the parameters are provided by the
     * {@link it.polimi.ingsw.gc31.controller.GameController}
     * 
     * @param secondsLeft the seconds left to the start of the game
     */
    @Override
    public void show_timerLastPlayerConnected(Integer secondsLeft) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(PLAYAREA_INITIAL_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW, PLAYAREA_END_COLUMN));
        res.append(print_Borders("LAST PLAYER CONNECTED", greyText, PLAYAREA_INITIAL_ROW,
                PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW, PLAYAREA_END_COLUMN));
        // res.append(print_Borders("", greyText, PLAYAREA_INITIAL_ROW + 1,
        // PLAYAREA_INITIAL_COLUMN + 1, PLAYAREA_END_ROW -1, PLAYAREA_END_COLUMN - 1));
        res.append(ansi().cursor(
                (PLAYAREA_END_ROW + PLAYAREA_INITIAL_ROW) / 2,
                (PLAYAREA_END_COLUMN + PLAYAREA_INITIAL_COLUMN) / 2)
                .a(secondsLeft));
        synchronized (playViewUpdateLOCK) {
            playViewUpdateLOCK.add(res);
            playViewUpdateLOCK.notify();
        }
    }

    // /**
    // * Unimplemented new feature
    // */
    // public void show_anotherMatch() {
    // }
}
