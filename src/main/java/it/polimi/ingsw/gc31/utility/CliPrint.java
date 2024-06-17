package it.polimi.ingsw.gc31.utility;

import org.fusesource.jansi.Ansi;

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

    public static void coloredPrintPurple(Ansi text) {
        System.out.println(DV.ANSI_PURPLE + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }

    public static void coloredPrintRed(Ansi text) {
        System.out.println(DV.ANSI_RED + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }

    public static void coloredPrintBlue(Ansi text) {
        System.out.println(DV.ANSI_BLUE + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }

    public static void coloredPrintCyan(Ansi text) {
        System.out.println(DV.ANSI_CYAN + Ansi.ansi().a(text) + DV.ANSI_RESET);
    }
}
