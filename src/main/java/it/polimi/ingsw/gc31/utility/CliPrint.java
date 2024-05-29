package it.polimi.ingsw.gc31.utility;

public class CliPrint {

    public static void coloredPrintPurple(String text) {
        System.out.println(DV.ANSI_PURPLE + text + DV.ANSI_RESET);
    }

    public static void coloredPrintRed(String text) {
        System.out.println(DV.ANSI_RED + text + DV.ANSI_RESET);
    }

    public static void coloredPrintBlue(String text) {
        System.out.println(DV.ANSI_BLUE + text + DV.ANSI_RESET);
    }

    public static void coloredPrintCyan(String text) {
        System.out.println(DV.ANSI_CYAN + text + DV.ANSI_RESET);
    }
}
