package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.DefaultValues;
import java.util.Scanner;

public class TUI extends UI {

    @Override
    public void runUI() {
        show_uiOptions();
        String line = DefaultValues.TUI_START_LINE_SYMBOL;
        System.out.println(line);
        String input;
        Scanner scanner = new Scanner(System.in);
        input = scanner.next();
        switch (input) {
            case "n":

                break;

            default:
                break;
        }

    }

    @Override
    protected void show_uiOptions() {
        System.out.println("__Commands List__");
        System.out.println("nick : shows your username");
    }

    @Override
    protected void show_nickname(String nick) {
        System.out.println("Username -> " + nick);
    }

}
