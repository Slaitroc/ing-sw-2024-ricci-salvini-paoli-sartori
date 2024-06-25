package it.polimi.ingsw.gc31.view.tui;

import static it.polimi.ingsw.gc31.utility.DV.RGB_COLOR_CORNER;
import static it.polimi.ingsw.gc31.utility.DV.RGB_COLOR_RED;
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

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.UI;

@SuppressWarnings("FieldCanBeLocal")
public class TUI extends UI {

    // MODIFICABILI
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

    private final int GAME_OVER_INITIAL_ROW = 14;
    private final int GAME_OVER_INITIAL_COLUMN = 1;
    private final int GAME_OVER_END_ROW = 34;
    private final int GAME_OVER_END_COLUMN = 67;

    // CONSTANTS
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

    private final int[] RGB_COLOR_GOLD = { 181, 148, 16 };

    private List<String> playersUsernames = new ArrayList<>();

    // COLORS
    int[] greyText = null;
    // int[] greyText = new int[] { 192, 192, 192 };
    int[] goldText = new int[] { 233, 227, 38 };
    int[] blueText = new int[] { 51, 153, 255 };
    int[] violetText = new int[] { 153, 153, 255 };

    private LinkedHashMap<Point, PlayableCard> placedCards = null;
    private Map<String, StringBuilder> playAreaAllPlayers = new HashMap<>();
    private String activePlayArea = "";

    // PRINT METHODS
    Map<TUIareas, StringBuilder> areasCache = new HashMap<>();
    protected Map<TUIcommands, Boolean> commandsCache = new HashMap<>();

    protected void forceRefreshTUI(boolean stateNotify) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (playViewUpdate) {
            for (Map.Entry<TUIareas, StringBuilder> area : areasCache.entrySet()) {
                playViewUpdate.add(area.getValue());
            }
            try {

                playViewUpdate.add(areasCache.get(TUIareas.PLAY_AREA_VIEW));
            } catch (NullPointerException ignored) {

            }
            playViewUpdate.notify();
        }
        moveCursorToCmdLine();
        if (state.stateName.equals("Joined To Game State")) {
            print_ChatBorders();
        }
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, stateNotify);

    }

    private void erase_ChatBoard() {
        for (int i = -2; i < CHAT_BOARD_LINES + 1; i++) {
            AnsiConsole.out().print(
                    Ansi.ansi().cursor(CHAT_BOARD_INITIAL_ROW + 1 + i, CHAT_BOARD_INITIAL_COLUMN)
                            .a(" " + " ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH) + " "));
        }
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
        int[] cardColor = RGB_COLOR_RED;
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
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    } else if (relative_x + CARD_CORNER_LENGTH >= overFlowRight) {
                        preLine = preLine.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine));
                    } else {
                        centerLine = centerLine.substring(0, overFlowRight - relative_x - CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y, relative_x)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine));
                    }

                }
                // If part of the line exceeds the left limit, the excess part is cut off, and
                // the x-coordinate changes
                else if (overFlowLeft - relative_x >= 0) {
                    if (CARD_CORNER_LENGTH - (overFlowLeft - relative_x) - 1 > 0) {
                        preLine = preLine.substring(overFlowLeft - relative_x + 1, CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y, overFlowLeft + 1)
                                .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
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
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerUpDxColor[0], cornerUpDxColor[1], cornerUpDxColor[2]).a(postLine));
                    }

                } else {
                    res.append(ansi().cursor(relative_y, relative_x)
                            .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                            .bgRgb(cornerUpSxColor[0], cornerUpSxColor[1], cornerUpSxColor[2]).a(preLine)
                            .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
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
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(postLine));
                    } else if (relative_x + CARD_CORNER_LENGTH >= overFlowRight) {
                        preLine = preLine.substring(0, overFlowRight - relative_x);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine));
                    } else {
                        centerLine = centerLine.substring(0, overFlowRight - relative_x - CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine));
                    }
                } else if (overFlowLeft - relative_x >= 0) {
                    if (CARD_CORNER_LENGTH - (overFlowLeft - relative_x) - 1 > 0) {
                        preLine = preLine.substring(overFlowLeft - relative_x + 1, CARD_CORNER_LENGTH);
                        res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, overFlowLeft + 1)
                                .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
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
                                .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
                                .bgRgb(cornerDownDxColor[0], cornerDownDxColor[1], cornerDownDxColor[2]).a(postLine));
                    }
                } else {
                    res.append(ansi().cursor(relative_y + CARD_HEIGHT - 1, relative_x)
                            .fgRgb(borderColor[0], borderColor[1], borderColor[2])
                            .bgRgb(cornerDownSxColor[0], cornerDownSxColor[1], cornerDownSxColor[2]).a(preLine)
                            .bgRgb(cardColor[0], cardColor[1], cardColor[2]).a(centerLine)
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

    // PRINT UTILITIES
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

    // TODO temporanei
    protected void movePlayAreaRight() {
        if (placedCards != null) {
            OFFSET_X_PLAYAREA += CARD_X_OFFSET * 2;
            show_playArea(client.getUsername(), placedCards, null);
        }
    }

    protected void movePlayAreaLeft() {
        if (placedCards != null) {
            OFFSET_X_PLAYAREA -= CARD_X_OFFSET * 2;
            show_playArea(client.getUsername(), placedCards, null);
        }
    }

    protected void movePlayAreaDown() {
        if (placedCards != null) {
            OFFSET_Y_PLAYAREA += CARD_Y_OFFSET * 2;
            show_playArea(client.getUsername(), placedCards, null);
        }
    }

    protected void movePlayAreaUp() {
        if (placedCards != null) {
            OFFSET_Y_PLAYAREA -= CARD_Y_OFFSET * 2;
            show_playArea(client.getUsername(), placedCards, null);
        }
    }

    // GETTERS & SETTERS

    /**
     * @return the ClientCommands client of the TUI
     */
    public ClientCommands getClient() {
        return this.client;
    }

    public TUI(ClientCommands client) {
        AnsiConsole.systemInstall();

        this.state = new InitState(this);
        this.client = client;
    }

    // UTILITIES

    /**
     * This method is used to move the cursor to the command line input area.
     * <p>
     * It also clears the command line input area and update the
     * <code>terminalAreaSelection</code> variable.
     */
    protected void moveCursorToCmdLine() {
        AnsiConsole.out().print(
                Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
        AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW, CMD_LINE_INPUT_COLUMN).a("> "));
    }

    /**
     * This method is used to move the cursor to the command line input area.
     * <p>
     * It also clears the command line input area and update the
     * <code>terminalAreaSelection</code> variable.
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
     * It also clears the chat input area and update the
     * <code>terminalAreaSelection</code> variable.
     */
    private void moveCursorToChatLine() {
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN)
                .a(" ".repeat(CHAT_BOARD_EFFECTIVE_WIDTH)));
        AnsiConsole.out()
                .print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN).a("> "));
    }

    /**
     * This method is used to move the cursor to the chat input area.
     * <p>
     * It also clears the chat input area and update the
     * <code>terminalAreaSelection</code> variable.
     *
     * @param prefix the prefix to be printed before the cursor
     */
    private void moveCursorToChatLine(String prefix) {
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN)
                .a(" ".repeat(CHAT_BOARD_WIDTH)));
        AnsiConsole.out().print(Ansi.ansi().cursor(CHAT_BOARD_INPUT_ROW, CHAT_BOARD_INPUT_COLUMN).a(prefix + " "));
    }

    /**
     * This method is used to reset the cursor to the right area after updating
     * another area.
     */
    protected void resetCursor() {
        synchronized (cmdLineAreaSelection) {
            if (!cmdLineAreaSelection.isEmpty()) {
                moveCursorToCmdLine();
            }
        }
        synchronized (chatAreaSelection) {
            if (!chatAreaSelection.isEmpty()) {
                moveCursorToChatLine();
            }
        }
    }

    /**
     * This method is used to add a message to the <code>commandLineOut</code>
     * thread's queue.
     * <p>
     * State methods must use this method to print their messages.
     *
     * @param message
     * @param color
     */
    protected void printToCmdLineOut(String message, Ansi.Color color) {
        synchronized (cmdLineOut) {
            cmdLineOut.add(Ansi.ansi().fg(color).a(message).reset().toString());
            cmdLineOut.notifyAll();
        }
    }

    /**
     * This method is used to add a message to the <code>commandLineOut</code>
     * thread's queue.
     * <p>
     * State methods must use this method to print their messages.
     *
     * @param message
     * @param color
     */
    protected void printToCmdLineOut(String message, Ansi.Color color, boolean wakeChat) {
        synchronized (cmdLineOut) {
            cmdLineOut.add(Ansi.ansi().fg(color).a(message).reset().toString());
            cmdLineOut.notifyAll();
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
        synchronized (cmdLineOut) {
            cmdLineOut.add(Ansi.ansi().a(message).reset().toString());
            cmdLineOut.notifyAll();
        }
        resetCursor();
    }

    // probabilmente non necessario al momento ma potrebbe essere utile in futuro
    protected void printToCmdLineIn(String message) {
        moveCursorToCmdLine(message);

    }

    /**
     * Format a String as it is printed from the TUI
     *
     * @param text
     * @return the formatted String
     */
    protected String tuiWrite(String text) {
        return DV.ANSI_BLUE + DV.TUI_TAG + DV.ANSI_RESET + text;
    }

    /**
     * Format a String as it is printed from the TUI in green
     *
     * @param text
     * @return the formatted String
     */
    protected String tuiWriteGreen(String text) {
        return DV.ANSI_BLUE + DV.TUI_TAG + DV.ANSI_GREEN + text
                + DV.ANSI_RESET;
    }

    /**
     * Format a String as it is printed from the TUI in purple
     *
     * @param text
     * @return the formatted String
     */
    protected String tuiWritePurple(String text) {
        return DV.ANSI_BLUE + DV.TUI_TAG + DV.ANSI_PURPLE + text
                + DV.ANSI_RESET;
    }

    protected String serverWrite(String text) {
        return DV.ANSI_PURPLE + DV.SERVER_TAG + DV.ANSI_RESET + text;
    }

    /**
     * Remove every characters inside the playArea
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
    // THREADS

    /**
     * State of the TUI (State Design Pattern)
     * <p>
     * This variable is used to manage the available commands in the current state
     */
    private TuiState state;

    protected Object stateLock = new Object();
    protected Queue<Integer> stateLockQueue = new ArrayDeque<Integer>();

    protected void addToStateLockQueue() {
        synchronized (stateLockQueue) {
            stateLockQueue.add(0);
            stateLockQueue.notify();
        }
    }

    protected void removeFromStateLockQueue() {
        synchronized (stateLockQueue) {
            if (stateLockQueue.isEmpty())
                return;
            stateLockQueue.poll();
        }
    }

    private volatile Queue<Integer> cmdLineAreaSelection = new ArrayDeque<Integer>();

    private void addToCmdLineAreaSelection() {
        synchronized (cmdLineAreaSelection) {
            cmdLineAreaSelection.add(0);
            cmdLineAreaSelection.notify();
        }
    }

    private void removeFromCmdLineAreaSelection() {
        synchronized (cmdLineAreaSelection) {
            if (cmdLineAreaSelection.isEmpty())
                return;
            cmdLineAreaSelection.poll();
        }
    }

    private volatile Queue<Integer> chatAreaSelection = new ArrayDeque<Integer>();

    private void addToChatAreaSelection() {
        synchronized (chatAreaSelection) {
            chatAreaSelection.add(0);
            chatAreaSelection.notify();
        }
    }

    private void removeFromChatAreaSelection() {
        synchronized (chatAreaSelection) {
            if (chatAreaSelection.isEmpty())
                return;
            chatAreaSelection.poll();
        }
    }

    /**
     * This variable is used to manage the chat board avoiding to update it every
     * time
     */
    private Object chatNeedsUpdate = new Object();

    private volatile boolean comingFromChat = false;

    /**
     * This variable is used to manage the chat messages. The ChatReader thread adds
     * new client's messages to this queue.
     * <p>
     * Right now this is the simplest implementation that comes to my mind, but it
     * would be better to use a specific class for the chat messages.
     */
    private Queue<String> chatMessages = new ArrayDeque<String>();
    /**
     * This variable is used to manage the command line output messages.
     * <p>
     * Instead of printing the messages directly to the system output, each state
     * must add its messages to this queue.
     * <p>
     * The <code>commandLineOut</code> thread
     * will print
     * them to the right position of the console.
     */
    private final Queue<String> cmdLineOut = new ArrayDeque<String>();
    /**
     * This variable is used to manage the command line input messages.
     * <p>
     * The <code>commandLineReader</code> thread reads the input from the system
     * input and adds
     * it to this queue.
     * <p>
     * The <code>commandLineProcess</code> thread reads the messages from this
     * queue and processes them.
     */
    protected final Queue<String> cmdLineMessages = new ArrayDeque<String>();
    private final Queue<StringBuilder> playViewUpdate = new ArrayDeque<StringBuilder>();

    /**
     * This thread is used to process the commands in the
     * <code>cmdLineMessages</code> queue.
     * <p>
     * If the command is "chat", it moves the cursor to the chat input area.
     */
    Thread cmdLineProcessThread = new Thread(() -> {
        commandToProcess(TUIcommands.INITIAL, false);
        while (true) {
            String cmd = null;
            synchronized (cmdLineMessages) {
                while (cmdLineMessages.isEmpty()) {
                    try {
                        cmdLineMessages.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                cmd = cmdLineMessages.poll();
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
     * If the command is not found, it prints "Command not recognized".
     *
     * @param command
     */
    private void execute_command(String command) {
        if (command.isEmpty()) {
            state.command_showCommandsInfo();
            state.stateNotify();
        } else if (command.equals(TUIcommands.INITIAL.toString()) && state.stateName.equals("Init State")) {
            state.command_initial();
        } else if (state.commandsMap.containsKey(command)) {
            state.commandsMap.get(command).run();
        } else if (command.equals(TUIcommands.NOTIFY.toString())) {
            state.stateNotify();
        } else {
            state.commandsMap.get(TUIcommands.INVALID.toString()).run();
        }
    }

    /**
     * Sends the corresponding TUIcommand to be executed to the ProcessThread and
     * notify the commands queue.
     *
     * @param cmd         The TUI command to process.
     * @param stateNotify A boolean indicating whether to notify the state or not.
     */
    protected void commandToProcess(TUIcommands cmd, boolean stateNotify) {
        synchronized (cmdLineMessages) {
            if (!stateNotify) {
                cmdLineMessages.add(cmd.toString());
                cmdLineMessages.notify();
            } else {
                cmdLineMessages.add(cmd.toString());
                cmdLineMessages.add(TUIcommands.NOTIFY.toString());
                cmdLineMessages.notify();
            }
        }
    }

    private volatile boolean chatNotification = false;
    private volatile boolean newChatMessage = false;
    private volatile boolean heartBeatReceived = false;

    private final Object statusBar = new Object();
    Thread statusBarThread = new Thread(() -> {
        StringBuilder heart = new StringBuilder();
        heart.append(Ansi.ansi().cursor(1, 17).a("💚"));
        System.out.println(heart);
        while (true) {
            synchronized (statusBar) {
                try {
                    statusBar.wait();
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
                    synchronized (statusBar) {
                        System.out.println(heart);
                    }
                    resetCursor();
                }

            }
        }

    });
    Thread timerThread = new Thread(() -> {
        Timer timer = new Timer();
        TimerTask updateStatusBar = new TimerTask() {
            @Override
            public void run() {
                heartBeatReceived = false;
                synchronized (statusBar) {
                    statusBar.notify();
                }
            }
        };
        timer.scheduleAtFixedRate(updateStatusBar, 0, 6000);
    });

    @Override
    public void show_heartBeat() {

        if (!heartBeatReceived) {
            StringBuilder heart = new StringBuilder();
            heart.append(Ansi.ansi().cursor(1, 17).a("💚"));
            System.out.println(heart);
            resetCursor();

        }
        heartBeatReceived = true;
    }

    /**
     * This thread is used to read the input from the system input and add it to the
     * <code>cmdLineMessages</code> queue.
     */
    Thread cmdLineReaderThread = new Thread(() -> {
        cmdLineProcessThread.start();
        ;
        Scanner cmdScanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cmdScanner.close();
        }));
        while (true) {
            synchronized (cmdLineAreaSelection) {
                if (cmdLineAreaSelection.isEmpty()) {
                    try {
                        cmdLineAreaSelection.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (comingFromChat == false) {
                // if a command is running it waits for the command to be finished
                // This is necessary to let each command get its input
                synchronized (stateLock) {
                    addToStateLockQueue();
                    try {
                        stateLock.wait();
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
                    synchronized (chatNeedsUpdate) {
                        chatNeedsUpdate.notify();
                    }
                } else {
                    synchronized (cmdLineMessages) {
                        cmdLineMessages.add(input.trim());
                        if (input.trim().equals(TUIcommands.SHOW_COMMAND_INFO.toString())) {
                            cmdLineMessages.add(TUIcommands.NOTIFY.toString());
                        }
                        cmdLineMessages.notify();
                    }
                }
            }
        }

    });
    /**
     * This thread is used to print the command line output messages the right way
     * and in the right position.
     */
    Thread cmdLineOutThread = new Thread(() -> {
        while (true) {
            synchronized (cmdLineOut) {
                if (cmdLineOut.isEmpty()) {
                    try {
                        print_CmdLineBorders();
                        cmdLineAreaSelection.add(0);
                        cmdLineReaderThread.start();
                        cmdLineOut.wait();
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
        synchronized (cmdLineOut) {
            // printa i messaggi
            for (int i = 0; i < cmdLineOut.size() && i < CMD_LINE_OUT_LINES; i++) {
                AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW - 1 - i, CMD_LINE_INPUT_COLUMN)
                        .a(" ".repeat(CMD_LINE_EFFECTIVE_WIDTH)));
                AnsiConsole.out().print(Ansi.ansi().cursor(CMD_LINE_INPUT_ROW - 1 - i, CMD_LINE_INPUT_COLUMN + 1)
                        .a(cmdLineOut.toArray()[cmdLineOut.size() - 1 - i]));
            }
            // rimuove i messaggi più vecchi
            if (cmdLineOut.size() > CMD_LINE_OUT_LINES - 1) {
                int i = cmdLineOut.size() - CMD_LINE_OUT_LINES - 1;
                for (int j = 0; j < i; j++) {
                    cmdLineOut.poll();
                }
            }
            resetCursor();
            try {
                cmdLineOut.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Thread chatBoardThreadBuilder() {
        return new Thread(() -> {
            print_ChatBorders();
            moveCursorToCmdLine();
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (chatNeedsUpdate) {
                    try {
                        chatNeedsUpdate.wait();
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

    @Override
    public void show_chatMessage(String username, String message) {
        synchronized (chatNeedsUpdate) {
            chatMessages.add(username + ": " + message);

        }
        if (chatAreaSelection.isEmpty()) {
            newChatMessage = true;
            if (!chatNotification) {
                synchronized (statusBar) {
                    statusBar.notify();
                }
            }
        } else {
            synchronized (chatNeedsUpdate) {
                chatNeedsUpdate.notifyAll();
            }
        }
    }

    @Override
    public void show_privateChatMessage(String fromUsername, String toUsername, String message) {
        synchronized (chatNeedsUpdate) {
            if(fromUsername.equals(client.getUsername()))
                chatMessages.add("[To: " + toUsername + "] : " + message);
            else if(toUsername.equals(client.getUsername()))
                chatMessages.add("[From: " + fromUsername + "] : " + message);
            else return;
        }
        if (chatAreaSelection.isEmpty()) {
            newChatMessage = true;
            if (!chatNotification) {
                synchronized (statusBar) {
                    statusBar.notify();
                }
            }
        } else {
            synchronized (chatNeedsUpdate) {
                chatNeedsUpdate.notifyAll();
            }
        }
    }

    /**
     * This thread is used to read the input from the system input and add it to the
     * <code>chatMessages</code> queue.
     */
    Thread chatReaderThread = new Thread(() -> {
        Scanner chatScanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            chatScanner.close();
        }));
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (chatAreaSelection) {
                if (chatAreaSelection.isEmpty()) {
                    try {
                        chatAreaSelection.wait();
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
                    for(String username : playersUsernames){
                        if(input.trim().startsWith("/"+username)) {
                            try {
                                String message = input.substring(username.length()+1).trim();
                                if(!message.isEmpty())
                                    client.sendChatMessage(getClient().getUsername(), username, message);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            privateMessage = true;
                            break;
                        }
                    }
                    if(!privateMessage) {
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

    Thread playViewThread = new Thread(() -> {
        while (true) {
            synchronized (playViewUpdate) {
                while (playViewUpdate.isEmpty()) {
                    try {
                        playViewUpdate.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(playViewUpdate.poll());
                resetCursor();
            }
        }

    });

    // THREADS UTILITIES

    // UTILITIES

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
        chatReaderThread.start();
        playViewThread.start();
        statusBarThread.start();
        timerThread.start();
        cmdLineOutThread.start();

    }

    // UPDATES FIELDS & METHODS

    /**
     * JAVADOC da modificare (non prendetela seriamente)
     * <p>
     * Prints the game list: this method is triggered by the controller.
     * <p>
     * <code>TUI.command_showGames()</code>->
     * <p>
     * <code>controller.getGameList(String username)</code> ->
     * <p>
     * <code>client.shoListGame(List gameList)</code> ->
     * <p>
     * <code>ui.showListGame(List gameList)</code>
     *
     * @Slaitroc
     */
    @Override
    public void show_listGame(List<String> listGame) {
        printToCmdLineOut(serverWrite(">>Game List<<"));
        for (String string : listGame) {
            printToCmdLineOut(string);
        }
        commandToProcess(TUIcommands.NOTIFY, false);

    }

    @Override
    public void show_playArea(String username, LinkedHashMap<Point, PlayableCard> playArea,
            Map<Resources, Integer> achievedResources) {
//        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(clearArea(PLAYAREA_INITIAL_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW, PLAYAREA_END_COLUMN));
            res.append(print_Borders("Play Area: "+username, greyText, PLAYAREA_INITIAL_ROW,
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
                synchronized (playViewUpdate) {
                    playViewUpdate.add(res);
                    playViewUpdate.notify();
                }
                areasCache.put(TUIareas.PLAY_AREA_VIEW, res);
            }

//        }
    }

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
                    .a(inTurn + " "+ player + ": " + scores.get(player).getKey()));
            index++;
        }

        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }
        areasCache.put(TUIareas.PLAYERS_INFO, res);

    }

    @Override
    public void update_ToPlayingState() {
        this.state = new PlayingState(this);
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, false);
        // qui lo state notify non serve perché lo chiama già il metodo triggerato
        // dall'oggetto risposta di ready
    }

    @Override
    public void show_goldDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        StringBuilder res = print_Deck("Gold Deck", greyText, firstCardDeck, card1, card2,
                GOLD_DECK_INITIAL_ROW,
                GOLD_DECK_INITIAL_COLUMN, GOLD_DECK_END_ROW, GOLD_DECK_END_COLUMN);
        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }
        areasCache.put(TUIareas.GOLD_DECK, res);

    }

    @Override
    public void show_resourceDeck(PlayableCard firstCardDeck, PlayableCard card1, PlayableCard card2) {
        StringBuilder res = print_Deck("Resource Deck", greyText, firstCardDeck, card1, card2,
                RESOURCE_DECK_INITIAL_ROW,
                RESOURCE_DECK_INITIAL_COLUMN, RESOURCE_DECK_END_ROW, RESOURCE_DECK_END_COLUMN);
        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }
        areasCache.put(TUIareas.RES_DECK, res);
    }

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
            synchronized (playViewUpdate) {
                playViewUpdate.add(res);
                playViewUpdate.notify();
            }
            areasCache.put(TUIareas.HAND, res);

        }
    }

    @Override
    public void show_objectiveCard(String username, ObjectiveCard objectiveCard) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(clearArea(OBJECTIVE_INITIAL_ROW+1, OBJECTIVE_INITIAL_COLUMN, OBJECTIVE_END_ROW, OBJECTIVE_END_COLUMN));
            res.append(ansi().cursor(OBJECTIVE_INITIAL_ROW, OBJECTIVE_INITIAL_COLUMN + 1).a("Your Objective Card"));
            res.append(print_ObjectiveCard(objectiveCard, OBJECTIVE_INITIAL_COLUMN + 1, OBJECTIVE_INITIAL_ROW + 1,
                    OBJECTIVE_INITIAL_ROW, OBJECTIVE_END_ROW, OBJECTIVE_INITIAL_COLUMN, OBJECTIVE_END_COLUMN));
            synchronized (playViewUpdate) {
                playViewUpdate.add(res);
                playViewUpdate.notify();
            }
            areasCache.put(TUIareas.OBJ, res);
        }
    }

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
            synchronized (playViewUpdate) {
                playViewUpdate.add(res);
                playViewUpdate.notify();
            }
            // FIXME problema per quando iniziano i turni della partita, l'area cache di
            // choose objective card non deve venire ristampata
             areasCache.put(TUIareas.CHOSE_OBJ, res);
        }
    }

    @Override
    public void show_commonObjectiveCard(ObjectiveCard card1, ObjectiveCard card2) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(COMMON_OBJECTIVE_INITIAL_ROW+1, COMMON_OBJECTIVE_INITIAL_COLUMN, COMMON_OBJECTIVE_END_ROW, COMMON_OBJECTIVE_END_COLUMN));
        res.append(ansi().cursor(COMMON_OBJECTIVE_INITIAL_ROW, COMMON_OBJECTIVE_INITIAL_COLUMN + 1).a("COMMON OBJECTIVE"));
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
        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }
        areasCache.put(TUIareas.COMMON_OBJ, res);
    }

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

            synchronized (playViewUpdate) {
                playViewUpdate.add(res);
                playViewUpdate.notify();
            }
            areasCache.put(TUIareas.STARTER, res);

        }
    }

    @Override
    public void show_validUsername(String username) {
        printToCmdLineOut(serverWrite("Username accepted"));
        printToCmdLineOut(tuiWrite("Your name is: " + username));
        client.setUsernameResponse(username);
        this.activePlayArea = username;
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, true);

    }

    @Override
    public void show_wrongUsername(String username) {
        printToCmdLineOut(serverWrite("Username " + username + " already taken, try again"));
        commandToProcess(TUIcommands.INITIAL, false);

    }

    @Override
    public void show_joinedToGame(int id, int maxNumberOfPlayers) {
        printToCmdLineOut(serverWrite("Joined to game: " + id));
        chatBoardThread = chatBoardThreadBuilder();
        chatBoardThread.start();
        state = new JoinedToGameState(this);
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, true);

    }

    @Override
    public void show_quitFromGame(String username) {
        // FIXME da problemi
        state = new InitState(this);
        chatBoardThread.interrupt();
        chatMessages = new ArrayDeque<String>();

        // TODO: erase player info
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, true);
    }

    @Override
    public void show_gameIsFull(int id) {
        printToCmdLineOut(serverWrite(serverWrite("Game " + id + " is full")));
        commandToProcess(TUIcommands.NOTIFY, false);

    }

    @Override
    public void show_gameCreated(int gameID) {
        printToCmdLineOut(serverWrite("New game created with ID: " + gameID));
        chatBoardThread = chatBoardThreadBuilder();
        chatBoardThread.start();
        state = new JoinedToGameState(this);
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, true);

    }

    @Override
    public void show_readyStatus(String username, boolean status) {
        // if (client.getUsername().equals(username)) {
        // if (status) {
        // printToCmdLineOut(serverWrite("You are ready"));
        // } else {
        // printToCmdLineOut(serverWrite("You are not ready"));
        // }
        // }
        // state.stateNotify();
        // FIXME
    }

    @Override
    public void show_gameDoesNotExist() {
        printToCmdLineOut(serverWrite("Game does not exist"));
        commandToProcess(TUIcommands.NOTIFY, false);

    }

    @Override
    public void show_wrongGameSize() {
        printToCmdLineOut(serverWrite("Game size must be between 2 and 4"));
        commandToProcess(TUIcommands.NOTIFY, false);

    }

    @Override
    public void show_playerTurn(String username, String info) {
        if (client.getUsername().equals(username)) {
            StringBuilder res = new StringBuilder();
            res.append(ansi().cursor(PLAYAREA_END_ROW + 1, PLAYAREA_INITIAL_COLUMN + 1).a(" ".repeat(PLAYAREA_END_COLUMN - PLAYAREA_INITIAL_COLUMN)));
            res.append(ansi().cursor(PLAYAREA_END_ROW + 1, PLAYAREA_INITIAL_COLUMN + 1).a(info));
            synchronized (playViewUpdate) {
                playViewUpdate.add(res);
                playViewUpdate.notify();
            }
            areasCache.put(TUIareas.PLAYER_STATE, res);
        }
    }

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

        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }

        areasCache.put(TUIareas.PLAYERS_INFO, res);

    }

    @Override
    public void show_invalidAction(String message) {
        printToCmdLineOut(serverWrite(message));
    }

    @Override
    public void show_GameIsOver(String username, Map<String, Integer> playersScore) {
        // TODO aggiungere punteggio giocatori
        StringBuilder res = new StringBuilder();
        res.append(clearArea(GAME_OVER_INITIAL_ROW, GAME_OVER_INITIAL_COLUMN, GAME_OVER_END_ROW,
                GAME_OVER_END_COLUMN));
        res.append(print_Borders("", greyText, GAME_OVER_INITIAL_ROW, GAME_OVER_INITIAL_COLUMN,
                GAME_OVER_END_ROW, GAME_OVER_END_COLUMN));

        if (client.getUsername().equals(username)) {
            res.append(ansi().cursor(GOLD_DECK_INITIAL_ROW + 1, GAME_OVER_INITIAL_COLUMN + 1).a("You are the winner"));
        } else {
            res.append(ansi().cursor(GOLD_DECK_INITIAL_ROW + 1, GAME_OVER_INITIAL_COLUMN + 1).a("You lost!"));
        }

        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }
    }

    public void receiveToken(int token) {
        client.setToken(token);
    }

    @Override
    public void showGenericClientResonse(String response) {
        printToCmdLineOut(tuiWrite(response));

    }

    @Override
    public void show_wantReconnect() {
        commandToProcess(TUIcommands.RECONNECT, false);
    }

    @Override
    public void show_rejoined(boolean result) {
        // TODO cambiare stato tui
        state = new PlayingState(this);
        commandToProcess(TUIcommands.SHOW_COMMAND_INFO, true);
    }

    @Override
    public void show_timerLastPlayerConnected(Integer secondsLeft) {
        StringBuilder res = new StringBuilder();
        res.append(clearArea(PLAYAREA_INITIAL_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW, PLAYAREA_END_COLUMN));
        res.append(print_Borders("ifneoirngoirngiowrngiorwngroignroignrignr", greyText, PLAYAREA_INITIAL_ROW, PLAYAREA_INITIAL_COLUMN, PLAYAREA_END_ROW, PLAYAREA_END_COLUMN));
//        res.append(print_Borders("", greyText, PLAYAREA_INITIAL_ROW + 1, PLAYAREA_INITIAL_COLUMN + 1, PLAYAREA_END_ROW -1, PLAYAREA_END_COLUMN - 1));
        res.append(ansi().cursor(
                (PLAYAREA_END_ROW + PLAYAREA_INITIAL_ROW)/2,
                (PLAYAREA_END_COLUMN + PLAYAREA_INITIAL_COLUMN)/2)
                .a(secondsLeft));
        synchronized (playViewUpdate) {
            playViewUpdate.add(res);
            playViewUpdate.notify();
        }
    }

    public void changeActivePlayArea(String username) {
        activePlayArea = username;
        areasCache.put(TUIareas.PLAY_AREA_VIEW, playAreaAllPlayers.get(username));
    }

    /**
     * This method should ask the player if it wants
     * to play another match with the same players after the current
     * match is finished
     */
    //FIXME implementare metodo. Ho fallito miseramente
    @Override
    public void show_anotherMatch(){
        printToCmdLineOut(tuiWrite("Do you want to play another match?"));
    }

}
