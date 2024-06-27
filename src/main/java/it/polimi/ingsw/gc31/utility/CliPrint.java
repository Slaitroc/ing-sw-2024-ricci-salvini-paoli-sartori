package it.polimi.ingsw.gc31.utility;

import org.fusesource.jansi.Ansi;

/**
 * Class used to color the text to printout for format and clarity reasons
 */
public class CliPrint {

    /**
     * Write in Purple color
     *
     * @param text to color
     */
    public static void coloredPrintPurple(String text) {
        System.out.println(DV.ANSI_PURPLE + text + DV.ANSI_RESET);
    }

    /**
     * Write in Red color
     *
     * @param text to color
     */
    public static void coloredPrintRed(String text) {
        System.out.println(DV.ANSI_RED + text + DV.ANSI_RESET);
    }

    /**
     * Write in Blu color
     *
     * @param text to color
     */
    public static void coloredPrintBlue(String text) {
        System.out.println(DV.ANSI_BLUE + text + DV.ANSI_RESET);
    }

    /**
     * Write in Cyan color
     *
     * @param text to color
     */
    public static void coloredPrintCyan(String text) {
        System.out.println(DV.ANSI_CYAN + text + DV.ANSI_RESET);
    }

    /**
     * Write ansi in Purple color
     *
     * @param text Ansi text to color
     */
    public static void coloredPrintPurple(Ansi text) {
        System.out.println(DV.ANSI_PURPLE + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }

    /**
     * Write ansi in Red color
     *
     * @param text Ansi text to color
     */
    public static void coloredPrintRed(Ansi text) {
        System.out.println(DV.ANSI_RED + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }

    /**
     * Write ansi in Blu color
     *
     * @param text Ansi text to color
     */
    public static void coloredPrintBlue(Ansi text) {
        System.out.println(DV.ANSI_BLUE + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }

    /**
     * Write ansi in Cyan color
     *
     * @param text Ansi text to color
     */
    public static void coloredPrintCyan(Ansi text) {
        System.out.println(DV.ANSI_CYAN + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }
}
