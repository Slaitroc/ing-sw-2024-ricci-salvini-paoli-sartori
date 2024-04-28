package it.polimi.ingsw.gc31.view.tui.tuiObj;

import java.util.List;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.util.ArrayList;

public class CardTUI {

    List<String> cardLines = new ArrayList<>();

    public CardTUI(PlayableCard card) {
        card.changeSide();
        List<Resources> res = card.getCorners();
        this.cardLines = constructCardLines(String.valueOf(card.getScore()), res.get(3).getSymbol(),
                res.get(0).getSymbol(),
                res.get(1).getSymbol(), res.get(2).getSymbol(), null,
                false);

    }

    public CardTUI(String rank, String topLeft, String topRight, String bottomRight, String bottomLeft,
            List<String> needs, boolean hasObjective) {
        this.cardLines = constructCardLines(rank, topLeft, topRight, bottomRight, bottomLeft, needs, hasObjective);
    }

    private List<String> constructCardLines(String rank, String topLeft, String topRight, String bottomRight,
            String bottomLeft, List<String> needs, boolean hasObjective) {
        List<String> cardLines = new ArrayList<>();
        List<String> check = new ArrayList<>();
        int size = 0;
        if (!(needs == null)) {
            check = needs;
            size = needs.size();
        }
        List<String> needed = new ArrayList<>(check) {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                for (int i = 0; i < size(); i++) {
                    if (i > 0) {
                    }
                    sb.append(get(i).toString());
                }
                return sb.toString();
            }
        };

        int totalPadding = 8 - size;
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        String obj = "─";
        if (hasObjective)
            obj = SymbolsTUI.OBJ.toString(); // FIX

        String topBottomBorder = String.format("┌─────%1s──────┐", obj);
        // String middleLine = "│ │";
        String topCorners = String.format("│%-5s%1s%6s│", topLeft, rank, topRight);
        String rankLine = String.format("│%s%s%s│", " ".repeat(rightPadding), needed, " ".repeat(leftPadding));
        String bottomCorners = String.format("│%-6s%6s│", bottomLeft, bottomRight);
        String bottomBorder = "└────────────┘";

        cardLines.add(topBottomBorder);
        cardLines.add(topCorners);
        cardLines.add(rankLine);
        cardLines.add(bottomCorners);
        cardLines.add(bottomBorder);

        return cardLines;
    }

    public List<String> getCardLines() {
        return cardLines;
    }

    public void printCard() {
        for (String line : cardLines) {
            System.out.println(line);
        }
    }

    public static void showHand(List<CardTUI> cards) {
        List<String> hand = new ArrayList<>();
        for (String line : cards.get(0).getCardLines()) {
            hand.add(line);
        }
        for (int i = 1; i < cards.size(); i++) {
            for (int j = 0; j < cards.get(i).getCardLines().size(); j++) {
                hand.set(j, hand.get(j).concat(cards.get(i).getCardLines().get(j)));
            }
        }
        for (String line : hand) {
            System.out.println(line);
        }

    }

    public static CardTUI createStandardCard1() {
        List<String> needs = new ArrayList<>();
        needs.add(SymbolsTUI.ANIMAL.toString());
        needs.add(SymbolsTUI.ANIMAL.toString());
        needs.add(SymbolsTUI.PLANT.toString());
        needs.add(SymbolsTUI.PLANT.toString());
        return new CardTUI("1", SymbolsTUI.INSECT.toString(), SymbolsTUI.INSECT.toString(), SymbolsTUI.PLANT.toString(),
                SymbolsTUI.MUSHROOM.toString(), needs, true);
    }

    public static CardTUI createStandardCard2() {
        List<String> needs = new ArrayList<>();
        needs.add(SymbolsTUI.INSECT.toString());
        needs.add(SymbolsTUI.INSECT.toString());
        needs.add(SymbolsTUI.MUSHROOM.toString());
        needs.add(SymbolsTUI.MUSHROOM.toString());
        return new CardTUI("1", SymbolsTUI.PLANT.toString(), SymbolsTUI.INSECT.toString(), SymbolsTUI.PLANT.toString(),
                SymbolsTUI.MUSHROOM.toString(), needs, false);
    }

}
