package it.polimi.ingsw.gc31.view;

import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class ProvaTUI {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String input;
        moveCursor.start();

        while (true) {
            input = scanner.nextLine();
            AnsiConsole.out().print(Ansi.ansi().cursor(5, 5).a(input).eraseLine());
            Thread.sleep(2000);
            AnsiConsole.out().print(Ansi.ansi().cursor(5, 5).fgBrightRed().a(input).reset());
        }
    }

    static Thread moveCursor = new Thread(() -> {
        while (true) {
            AnsiConsole.out().print(Ansi.ansi().cursor(1, 1).a("ciao"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AnsiConsole.out().print(Ansi.ansi().cursor(10, 1).a("ciao"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AnsiConsole.out().print(Ansi.ansi().cursor(10, 10).a("ciao"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AnsiConsole.out().print(Ansi.ansi().cursor(1, 10).a("ciao"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AnsiConsole.out().print(Ansi.ansi().cursor(1, 1).a("    ").eraseLine());
            AnsiConsole.out().print(Ansi.ansi().cursor(10, 1).a("    ").eraseLine());

        }
    });

}
