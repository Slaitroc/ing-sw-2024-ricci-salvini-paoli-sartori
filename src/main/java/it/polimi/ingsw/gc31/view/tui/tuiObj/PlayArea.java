package it.polimi.ingsw.gc31.view.tui.tuiObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.view.tui.tuiObj.exceptions.IsNotPivotException;

import static it.polimi.ingsw.gc31.OurScanner.scanner;

public class PlayArea {

    // debug
    boolean check = false;

    public List<String> playArea;
    public Pivots pivots;
    public List<CardTUI> cards;

    public PlayArea(CardTUI card) {
        playArea = new ArrayList<>();
        pivots = new Pivots(this);
        cards = new ArrayList<>();
        addFirstCard(card);
        findPivotAndCorners();

    }

    public List<String> getPlayArea() {
        return playArea;
    }

    public List<CardTUI> getCards() {
        return cards;
    }

    public void printPlayArea() {
        for (String line : playArea) {
            System.out.println(line);
        }
    }

    private void addFirstCard(CardTUI card) {
        cards.add(card);
        List<String> cardLines = card.getCardLines();

        if (playArea.size() == 0) {
            for (int i = 0; i < cardLines.size(); i++) {
                playArea.add(cardLines.get(i));
            }
        }
    }

    public void findPivotAndCorners() {
        pivots.findPivotAndCorners(this);
    }

    public void printPivot() {
        pivots.printPivot();
    }

    public void printCorners() {
        pivots.printCorners();
    }

    public void coverPivot(CardTUI card, Integer[] pivot) throws IsNotPivotException {
        Integer width = playArea.get(0).length();

        Integer x = pivot[0];
        Integer y = pivot[1];

        // se non è un pivot valido lancia l'eccezione
        if (!(pivots.isPivot(pivot)))
            throw new IsNotPivotException();
        // Se il pivot è in uno degli angoli più estremi della play area
        // bisogna (almeno) allargare la mappa
        // NOTE probabilmente sempre qui posso gestire il relativo corner case
        if (pivots.isCorner(pivot)) {
            // aggiunge lo spazio per una carta in alto
            if (pivots.isTopCorner(pivot)) {
                for (int i = 0; i < DefaultValues.height_shift; i++) {
                    playArea.addFirst(" ".repeat(width));
                }
                y = y + DefaultValues.height_shift;
            }
            // aggiunge lo spazio per una carta in basso
            if (pivots.isBottomCorner(pivot)) {
                for (int i = 0; i < DefaultValues.height_shift; i++) {
                    playArea.addLast(" ".repeat(width));
                }
            }
            // aggiunge lo spazio per una carta a destra
            if (pivots.isRightCorner(pivot)) {
                for (int i = 0; i < playArea.size(); i++) {
                    playArea.set(i, playArea.get(i) + " ".repeat(DefaultValues.width_shift));
                }

            }
            // aggiunge lo spazio per una carta a sinistra
            if (pivots.isLeftCorner(pivot)) {
                for (int i = 0; i < playArea.size(); i++) {
                    playArea.set(i, " ".repeat(DefaultValues.width_shift) + playArea.get(i));
                }
                x = x + DefaultValues.width_shift;
            }

        }

        StringBuilder builder;
        Integer[] angle;
        Map<String, Integer[]> cardCorners = Pivots.getCardCorners(card);
        char p = playArea.get(y).charAt(x);
        switch (p) {
            case '┌':
                angle = cardCorners.get("BR");
                for (int i = 0; i < card.cardLines.size(); i++) {
                    String line = playArea.get(y + 1 - i);
                    String cardLine = card.cardLines.get(angle[1] - i);
                    builder = new StringBuilder(line);

                    // NOTE: dovrebbe essere giusto
                    builder.replace(x - DefaultValues.width_shift, x + DefaultValues.width_intersect, cardLine);
                    String newLine = builder.toString();
                    playArea.set(y + 1 - i, newLine);
                }
                break;
            case '└':
                angle = cardCorners.get("TR");
                for (int i = 0; i < card.cardLines.size(); i++) {
                    String line = playArea.get(y - 1 + i);
                    String cardLine = card.cardLines.get(angle[1] + i);
                    builder = new StringBuilder(line);
                    builder.replace(x - DefaultValues.width_shift, x + DefaultValues.width_intersect, cardLine);
                    String newLine = builder.toString();
                    playArea.set(y - 1 + i, newLine);
                }
                break;
            case '┐':
                angle = cardCorners.get("BL");
                for (int i = 0; i < card.cardLines.size(); i++) {
                    String line = playArea.get(y + 1 - i);
                    String cardLine = card.cardLines.get(angle[1] - i);
                    builder = new StringBuilder(line);
                    builder.replace(x - DefaultValues.width_intersect + 1, x + DefaultValues.width_shift + 1, cardLine);
                    String newLine = builder.toString();
                    playArea.set(y + 1 - i, newLine);
                }
                break;
            case '┘':
                angle = cardCorners.get("TL");
                for (int i = 0; i < card.cardLines.size(); i++) {
                    String line = playArea.get(y - 1 + i);
                    String cardLine = card.cardLines.get(angle[1] + i);
                    builder = new StringBuilder(line);
                    builder.replace(x - DefaultValues.width_intersect + 1, x + DefaultValues.width_shift, cardLine);
                    String newLine = builder.toString();
                    playArea.set(y - 1 + i, newLine);
                }
                break;
        }

        // TODO
        // alla fine, se la carta viene aggiunta, bisogna aggiungerla a cards
        cards.add(card);
        trimPlayArea();
        findPivotAndCorners();

    }

    private void trimPlayArea() {
        int length = playArea.get(0).length();
        for (String line : playArea) {
            if (line.length() < length)
                length = line.length();
        }
        for (int i = 0; i < playArea.size(); i++) {
            playArea.set(i, playArea.get(i).substring(0, length));
        }

    }

    public int choosePivot() {
        List<String> pivotSelectionPA = playArea;
        StringBuilder builder;
        for (int j = 0; j < pivots.pivots.size(); j++) {
            builder = new StringBuilder(pivotSelectionPA.get(pivots.pivots.get(j)[1]));
            int end = String.valueOf(j).length();
            builder.replace(pivots.pivots.get(j)[0], pivots.pivots.get(j)[0] + end, String.valueOf(j));
            String line = builder.toString();
            pivotSelectionPA.set(pivots.pivots.get(j)[1], line);
        }
        for (String string : pivotSelectionPA) {
            System.out.println(string);
        }
        System.out.println("Choose a pivot: ");
        int pivot = Integer.parseInt(scanner.nextLine());
        return pivot;
    }
}
