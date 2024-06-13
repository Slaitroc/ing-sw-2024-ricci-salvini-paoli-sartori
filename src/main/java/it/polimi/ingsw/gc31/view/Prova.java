package it.polimi.ingsw.gc31.view;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.view.tui.TUI;
import it.polimi.ingsw.gc31.view.tui.TuiState;
import org.fusesource.jansi.AnsiConsole;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class Prova {
    public static void main(String[] args) {
//        ProvaTui provaTui = new ProvaTui(null);
//        // provaTui.printStarter();
//        provaTui.printGold();
//
//        AnsiConsole.systemUninstall();
//

        Map<Resources, Integer> achievedResources = new HashMap<>();
        achievedResources.put(Resources.ANIMAL, 0);
        achievedResources.put(Resources.MUSHROOM, 0);

        String toJson = gsonTranslater.toJson(achievedResources);
        System.out.println(toJson);

        Map<Resources, Integer> resources = gsonTranslater.fromJson(toJson, new TypeToken<Map<Resources, Integer>>(){}.getType());
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
