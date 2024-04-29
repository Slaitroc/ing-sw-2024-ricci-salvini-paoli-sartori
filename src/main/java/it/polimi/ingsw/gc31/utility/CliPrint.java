package it.polimi.ingsw.gc31.utility;

import it.polimi.ingsw.gc31.DefaultValues;

public class CliPrint {

    public static void coloredPrintPurple(String text) {
        System.out.println(DefaultValues.ANSI_PURPLE + text + DefaultValues.ANSI_RESET);
    }

    public static void coloredPrintRed(String text) {
        System.out.println(DefaultValues.ANSI_RED + text + DefaultValues.ANSI_RESET);
    }

    public static void coloredPrintBlue(String text) {
        System.out.println(DefaultValues.ANSI_BLUE + text + DefaultValues.ANSI_RESET);
    }

    public static void coloredPrintCyan(String text) {
        System.out.println(DefaultValues.ANSI_CYAN + text + DefaultValues.ANSI_RESET);
    }
}
