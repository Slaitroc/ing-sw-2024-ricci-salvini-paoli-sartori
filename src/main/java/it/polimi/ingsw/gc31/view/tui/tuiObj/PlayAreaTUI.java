package it.polimi.ingsw.gc31.view.tui.tuiObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.view.tui.tuiObj.exceptions.IsNotPivotTUIException;

import static it.polimi.ingsw.gc31.OurScanner.scanner;

public class PlayAreaTUI {

    // debug
    boolean check = false;

    public List<String> playArea;
    public PivotsTUI pivots;
    public List<CardTUI> cards;
    private final Map<PointTUI, PointTUI> translationMap;

    public PlayAreaTUI(CardTUI card) {
        playArea = new ArrayList<>();
        pivots = new PivotsTUI(this);
        cards = new ArrayList<>();
        translationMap = new HashMap<>();
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
        translationMap.put(new PointTUI(0, 0), new PointTUI(6, 2));

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

    public void coverPivot(CardTUI card, Integer[] pivot) throws IsNotPivotTUIException {
        Integer width = playArea.get(0).length();

        Integer x = pivot[0];
        Integer y = pivot[1];

        // se non è un pivot valido lancia l'eccezione
        if (!(pivots.isPivot(pivot)))
            throw new IsNotPivotTUIException();
        // Se il pivot è in uno degli angoli più estremi della play area
        // bisogna (almeno) allargare la mappa
        // NOTE probabilmente sempre qui posso gestire il relativo corner case
        if (pivots.isCorner(pivot)) {
            // aggiunge lo spazio per una carta in alto
            if (pivots.isTopCorner(pivot)) {
                for (int i = 0; i < DefaultValues.height_shift; i++) {
                    playArea.addFirst(" ".repeat(width));
                }
                // aggiorna la posizione del pivot per posizionare la carta
                y = y + DefaultValues.height_shift;
                // aggiorna le posizioni nella translation map
                for (PointTUI i : translationMap.values()) {
                    i.y = i.y + DefaultValues.height_shift;
                }
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
                // aggiorna la posizione del pivot per posizionare la carta
                x = x + DefaultValues.width_shift;
                // aggiorna le posizioni nella translation map
                for (PointTUI i : translationMap.values()) {
                    i.x = i.x + DefaultValues.width_shift;
                }
            }

        }

        StringBuilder builder;
        Integer[] angle;
        Map<String, Integer[]> cardCorners = PivotsTUI.getCardCorners(card);
        PointTUI pivotTUICard = new PointTUI(x, y);
        PointTUI pivotModelCard = null;
        char p = playArea.get(y).charAt(x);
        switch (p) {
            case '┌':
                // posiziona la carta aggiornando la mappa
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
                // aggiorno la translation map

                // trovo il nuovo punto nella mappa per la CardTUI
                pivotTUICard.y -= 1;
                pivotTUICard.x -= 5;

                // trovo il nuovo punto nel model
                pivotModelCard = null;
                for (Map.Entry<PointTUI, PointTUI> point : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (point.getValue().equals(new PointTUI(x + 6, y + 2))) {
                        pivotModelCard = new PointTUI(point.getKey());
                    }
                }
                if (pivotModelCard != null) {
                    pivotModelCard.x -= 1;
                    pivotModelCard.y += 1;
                    // aggiorno la mappa
                    translationMap.put(pivotModelCard, pivotTUICard);
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

                // trovo il nuovo punto nella mappa per la CardTUI
                pivotTUICard.y += 1;
                pivotTUICard.x -= 5;

                // trovo il nuovo punto nel model
                for (Map.Entry<PointTUI, PointTUI> point : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (point.getValue().equals(new PointTUI(x + 6, y - 2))) {
                        pivotModelCard = new PointTUI(point.getKey());
                    }
                }
                if (pivotModelCard != null) {
                    pivotModelCard.x -= 1;
                    pivotModelCard.y -= 1;
                    // aggiorno la mappa
                    translationMap.put(pivotModelCard, pivotTUICard);
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

                // trovo il nuovo punto nella mappa per la CardTUI
                pivotTUICard.y -= 1;
                pivotTUICard.x += 4;

                // trovo il nuovo punto nel model
                for (Map.Entry<PointTUI, PointTUI> point : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (point.getValue().equals(new PointTUI(x - 7, y + 2))) {
                        pivotModelCard = new PointTUI(point.getKey());
                    }
                }
                if (pivotModelCard != null) {
                    pivotModelCard.x += 1;
                    pivotModelCard.y += 1;
                    // aggiorno la mappa
                    translationMap.put(pivotModelCard, pivotTUICard);
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

                // trovo il nuovo punto nella mappa per la CardTUI
                pivotTUICard.y += 3;
                pivotTUICard.x += 4;

                // trovo il nuovo punto nel model
                for (Map.Entry<PointTUI, PointTUI> point : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (point.getValue().equals(new PointTUI(x - 7, y - 2))) {
                        pivotModelCard = new PointTUI(point.getKey());
                    }
                }
                if (pivotModelCard != null) {
                    pivotModelCard.x += 1;
                    pivotModelCard.y -= 1;
                    // aggiorno la mappa
                    translationMap.put(pivotModelCard, pivotTUICard);
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

    public PointTUI choosePivot() {
        List<String> pivotSelectionPA = new ArrayList<>(playArea);
        StringBuilder builder;
        Map<Integer, PointTUI> pivotSelectionTUI = new HashMap<>();
        for (int j = 0; j < pivots.pivots.size(); j++) {
            builder = new StringBuilder(pivotSelectionPA.get(pivots.pivots.get(j)[1]));
            int end = String.valueOf(j).length();
            builder.replace(pivots.pivots.get(j)[0], pivots.pivots.get(j)[0] + end, String.valueOf(j));
            pivotSelectionTUI.put(j, new PointTUI(pivots.pivots.get(j)[0], pivots.pivots.get(j)[1]));
            String line = builder.toString();
            pivotSelectionPA.set(pivots.pivots.get(j)[1], line);
        }
        for (String string : pivotSelectionPA) {
            System.out.println(string);
        }
        System.out.println("Choose a pivot: ");
        int pivot = Integer.parseInt(scanner.nextLine());

        PointTUI point = pivotSelectionTUI.get(pivot);
        PointTUI modelCard = null;
        char p = playArea.get(point.y).charAt(point.x);
        switch (p) {
            case '┌':
                for (Map.Entry<PointTUI, PointTUI> h : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (h.getValue().equals(new PointTUI(point.x + 6, point.y + 2))) {
                        modelCard = new PointTUI(h.getKey());
                    }
                }
                modelCard.x -= 1;
                modelCard.y += 1;
                break;
            case '└':
                for (Map.Entry<PointTUI, PointTUI> h : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (h.getValue().equals(new PointTUI(point.x + 6, point.y - 2))) {
                        modelCard = new PointTUI(h.getKey());
                    }
                }
                modelCard.x -= 1;
                modelCard.y -= 1;
                break;
            case '┐':
                for (Map.Entry<PointTUI, PointTUI> h : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (h.getValue().equals(new PointTUI(point.x - 7, point.y + 2))) {
                        modelCard = new PointTUI(h.getKey());
                    }
                }
                modelCard.x += 1;
                modelCard.y += 1;
                break;
            case '┘':
                for (Map.Entry<PointTUI, PointTUI> h : translationMap.entrySet()) {
                    // trovo la posizione della carta model più vicina
                    if (h.getValue().equals(new PointTUI(point.x - 7, point.y - 2))) {
                        modelCard = new PointTUI(h.getKey());
                    }
                }
                modelCard.x += 1;
                modelCard.y -= 1;
                break;
        }
        // print model card
        System.out.println("Carta model in posizione (" + modelCard.x + "," + modelCard.y + ")");
        return modelCard;
    }

    private void printTranlationMap() {
        for (Map.Entry<PointTUI, PointTUI> entry : translationMap.entrySet()) {
            System.out.println("Carta model in posizione (" + entry.getKey().x + "," + entry.getKey().y
                    + ") corrisponde a carta in posizione (" + entry.getValue().x + "," + entry.getValue().y
                    + ")");
        }
    }

    public static void main(String[] args) throws IsNotPivotTUIException {
        System.out.println("cio");
        PlayAreaTUI playArea = new PlayAreaTUI(CardTUI.createStandardCard1());
        playArea.printPlayArea();
        playArea.printPivot();
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.coverPivot(CardTUI.createStandardCard2(), playArea.pivots.pivots.get(0));
        playArea.printPlayArea();
        playArea.printTranlationMap();
        playArea.choosePivot();

        // non trova il pivot nella translation map
    }
}
