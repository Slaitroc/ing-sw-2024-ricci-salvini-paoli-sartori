package it.polimi.ingsw.gc31.utility;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.view.tui.TUI;
import org.fusesource.jansi.AnsiConsole;

import static it.polimi.ingsw.gc31.utility.OurScanner.scanner;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PrintProvca extends TUI {
    public PrintProvca(ClientCommands client) {
        super(client);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        AnsiConsole.systemInstall();
        printAllObjectiveCard();
        // printAllGoldCards();
        // printAllResourceCards();
        // printAllStarterCards();
    }

    public void printAllObjectiveCard() {
        Deck<ObjectiveCard> deck = new Deck<>(CardType.OBJECTIVE);
        print_ObjectiveCard(
                deck.draw(), 65, 2, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 88, 2, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 111, 2, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 134, 2, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 65, 11, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 88, 11, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 111, 11, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 134, 11, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 65, 20, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 88, 20, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 111, 20, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 134, 20, 0, 300, 0, 300);

        print_ObjectiveCard(
                deck.draw(), 65, 30, 0, 300, 0, 300);
        print_ObjectiveCard(
                deck.draw(), 88, 30, 0, 300, 0, 300);
        print_ObjectiveCard(
                deck.draw(), 111, 30, 0, 300, 0, 300);
        print_ObjectiveCard(
                deck.draw(), 134, 30, 0, 300, 0, 300);
    }

    public void printAllGoldCards() {
        Deck<PlayableCard> deck = new Deck<>(CardType.GOLD);
        List<PlayableCard> redCard = new ArrayList<>();
        List<PlayableCard> greenCard = new ArrayList<>();
        List<PlayableCard> blueCard = new ArrayList<>();
        List<PlayableCard> purpleCard = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            PlayableCard card = deck.draw();
            card.changeSide();
            if (card.getColor() == CardColor.RED)
                redCard.add(card);
            else if (card.getColor() == CardColor.BLUE)
                blueCard.add(card);
            else if (card.getColor() == CardColor.GREEN)
                greenCard.add(card);
            else if (card.getColor() == CardColor.PURPLE)
                purpleCard.add(card);
        }

        int row = 1;
        int col = 1;

        for (PlayableCard card : redCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
        for (PlayableCard card : blueCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
        for (PlayableCard card : greenCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
        for (PlayableCard card : purpleCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
    }

    public void printAllResourceCards() {
        Deck<PlayableCard> deck = new Deck<>(CardType.RESOURCE);
        List<PlayableCard> redCard = new ArrayList<>();
        List<PlayableCard> greenCard = new ArrayList<>();
        List<PlayableCard> blueCard = new ArrayList<>();
        List<PlayableCard> purpleCard = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            PlayableCard card = deck.draw();
            card.changeSide();
            if (card.getColor() == CardColor.RED)
                redCard.add(card);
            else if (card.getColor() == CardColor.BLUE)
                blueCard.add(card);
            else if (card.getColor() == CardColor.GREEN)
                greenCard.add(card);
            else if (card.getColor() == CardColor.PURPLE)
                purpleCard.add(card);
        }

        int row = 1;
        int col = 1;

        for (PlayableCard card : redCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
        for (PlayableCard card : blueCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
        for (PlayableCard card : greenCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
        for (PlayableCard card : purpleCard) {
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );

            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
    }

    public void printAllStarterCards() {
        Deck<PlayableCard> deck = new Deck<>(CardType.STARTER);
        int row = 1;
        int col = 1;

        for (int i = 0; i < 6; i++) {
            PlayableCard card = deck.draw();
            // card.changeSide();
            print_PlayableCard(
                    card,
                    col,
                    row,
                    0, 300, 0, 300

            );
            col += 23;
            if (col > 150) {
                col = 1;
                row += 9;
            }
        }
    }

    public static void main(String[] args) throws NotBoundException, RemoteException {
        ClientCommands client = null;

        PrintProvca prova = new PrintProvca(client);
        String temp = scanner.nextLine();
        AnsiConsole.systemUninstall();
    }
}
