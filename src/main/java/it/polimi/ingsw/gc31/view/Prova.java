package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.view.tui.TUI;
import org.fusesource.jansi.AnsiConsole;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;
import static org.fusesource.jansi.Ansi.ansi;

public class Prova {
    public static void main(String[] args) {
//        ProvaTui provaTui = new ProvaTui(null);
//        provaTui.printStarter();
//        provaTui.printGold();
//        String blink = "\033[5m";
//        String reset = "\033[0m";
//        String text = "Questo testo lampeggia";
//        System.out.println(blink+text+reset);
    }

    public static class ProvaTui extends TUI {

        public ProvaTui(ClientCommands client) {
            super(client);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            AnsiConsole.systemInstall();
        }

        public void printGold() {
            Deck<PlayableCard> deck = new Deck<>(CardType.GOLD);

            StringBuilder res = new StringBuilder();
            int row = 1;
            int col = 1;
            for (int i = 0; i < 40; i++) {
                res.append(print_PlayableCard(
                        deck.draw(),
                        col,
                        row, 0, 300, 0, 300));
                col += 20;
                if (col >= 180) {
                    col = 1;
                    row += 8;
                }
            }

            System.out.println(res);
        }

        public void printStarter() {
            Deck<PlayableCard> deck = new Deck<>(CardType.STARTER);

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                res.append(print_PlayableCard(
                        deck.draw(),
                        1 + 20 * i,
                        1,
                        0,
                        300,
                        0,
                        300));
            }

            deck = new Deck<>(CardType.STARTER);
            for (int i = 0; i < 6; i++) {
                PlayableCard playableCard = deck.draw();
                playableCard.changeSide();
                res.append(print_PlayableCard(
                        playableCard,
                        1 + 20 * i,
                        1 + 10,
                        0,
                        300,
                        0,
                        300));
            }

            System.out.println(res);
        }
    }
}
