package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.player.Placed;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.view.tui.TUI;
import org.fusesource.jansi.AnsiConsole;

public class Prova {
    public static void main(String[] args) throws IllegalStateOperationException, EmptyDeckException {
        Board board = new Board();
        Player player1 = new Player(PawnColor.BLUE, "sslvo", board);

        board.getDeckGold().refill();
        for (int i=0; i<37; i++) {
            board.getDeckGold().draw();
        }
        player1.setInGameState(new Placed());
        player1.drawGold(1);

        player1.setInGameState(new Placed());
        player1.drawGold(1);
        System.out.println("ok");
    }

    public static class ProvaTui extends TUI {

        public ProvaTui(ClientCommands client) {
            super(client);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            AnsiConsole.systemInstall();
        }

        public void printGold() throws EmptyDeckException {
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

        public void printStarter() throws EmptyDeckException {
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
