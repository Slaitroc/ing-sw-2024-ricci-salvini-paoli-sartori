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
        int size = 1;
        if (!(needs == null)) {
            check = needs;
            if (!(needs.size() > 5))
                size = needs.size() * 2;
            else
                size = 10;
        }
        List<String> needed = new ArrayList<>(check) {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                // sb.append("");
                for (int i = 0; i < size(); i++) {
                    if (i < 5)
                        sb.append(get(i).toString());
                }
                return sb.toString();
            }
        };

        int totalPadding = 12 - size;
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        String obj = "─";
        if (hasObjective)
            obj = "╳"; // FIX

        String topBottomBorder = String.format("┌─────%1s──────┐", obj);
        // String middleLine = "│ │";
        String topCorners = String.format("│%-5s%1s%6s│", topLeft.isEmpty() ? " " : topLeft, rank,
                topRight.isEmpty() ? " " : topRight);
        String neededLine = String.format("│%s%s%s│", " ".repeat(leftPadding), needed.isEmpty() ? " " : needed,
                " ".repeat(rightPadding));
        String bottomCorners = String.format("│%-5s%1s%6s│", bottomLeft.isEmpty() ? " " : bottomLeft, " ",
                bottomRight.isEmpty() ? " " : bottomRight);
        String bottomBorder = "└────────────┘";

        cardLines.add(topBottomBorder);
        cardLines.add(topCorners);
        cardLines.add(neededLine);
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
        needs.add(Resources.ANIMAL.getSymbol());
        needs.add(Resources.ANIMAL.getSymbol());
        needs.add(Resources.PLANT.getSymbol());
        needs.add(Resources.PLANT.getSymbol());
        return new CardTUI("1", Resources.INSECT.getSymbol(), Resources.INSECT.getSymbol(), Resources.PLANT.getSymbol(),
                Resources.MUSHROOM.getSymbol(), needs, true);
    }

    public static CardTUI createStandardCard2() {
        List<String> needs = new ArrayList<>();
        needs.add(Resources.INSECT.getSymbol());
        needs.add(Resources.INSECT.getSymbol());
        needs.add(Resources.MUSHROOM.getSymbol());
        needs.add(Resources.MUSHROOM.getSymbol());
        return new CardTUI("1", Resources.PLANT.getSymbol(), Resources.INSECT.getSymbol(), Resources.PLANT.getSymbol(),
                Resources.MUSHROOM.getSymbol(), needs, false);
    }

}
