package it.polimi.ingsw.gc31.view.tui.tuiObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pivots {

    public List<Integer[]> pivots;
    public Map<String, List<Integer[]>> corners;

    public Pivots(PlayArea playArea) {
        pivots = new ArrayList<>();
        corners = new HashMap<>();
    }

    private List<Integer[]> findPivots(PlayArea playArea) {
        List<Integer[]> pivots = new ArrayList<>();
        boolean isValid = true;
        List<String> pA = playArea.getPlayArea();

        // trova tutti i pivot
        for (int j = 0; j < pA.size(); j++) {
            for (int i = 0; i < pA.get(j).length(); i++) {
                if (pA.get(j).charAt(i) == '┌') {
                    for (int k = 1; k < 4; k++) {
                        try {
                            if (pA.get(j + 1).charAt(i - k) != ' ')
                                isValid = false;
                        } catch (Exception e) {
                            // se il carattere della riga sotto a sinistra non esiste allora sicuramente è
                            // valido
                        }
                    }
                    if (isValid) {
                        pivots.add(new Integer[] { i, j });
                    }
                    isValid = true;
                } else if (pA.get(j).charAt(i) == '└') {
                    for (int k = 1; k < 4; k++) {
                        try {
                            if (pA.get(j + 1).charAt(i + k) != ' ')
                                isValid = false;
                        } catch (Exception e) {
                            // se il carattere della riga sotto a destra non esiste allora sicuramente è
                            // valido
                        }
                    }
                    if (isValid) {
                        pivots.add(new Integer[] { i, j });
                    }
                    isValid = true;
                } else if (pA.get(j).charAt(i) == '┐') {
                    for (int k = 1; k < 6; k++) {
                        try {
                            if (pA.get(j + 1).charAt(i + k) != ' ')
                                isValid = false;
                        } catch (Exception e) {
                            // se il carattere della riga sotto a destra non esiste allora sicuramente è
                            // valido
                        }
                    }
                    if (isValid) {
                        pivots.add(new Integer[] { i, j });
                    }
                    isValid = true;

                } else if (playArea.getPlayArea().get(j).charAt(i) == '┘') {
                    for (int k = 1; k < 4; k++) {
                        try {
                            if (pA.get(j + 1).charAt(i - k) != ' ')
                                isValid = false;
                        } catch (Exception e) {
                            // se il carattere della riga sotto a sinistra non esiste allora sicuramente è
                            // valido
                        }
                    }
                    if (isValid) {
                        pivots.add(new Integer[] { i, j });
                    }
                    isValid = true;
                }
            }

        }
        return pivots;
    }

    private Map<String, List<Integer[]>> findCorners(List<Integer[]> pivots) {
        Map<String, List<Integer[]>> newCorners = new HashMap<>();
        List<Integer[]> top = new ArrayList<>();
        List<Integer[]> bottom = new ArrayList<>();
        List<Integer[]> left = new ArrayList<>();
        List<Integer[]> right = new ArrayList<>();

        boolean couldBeCorner;
        // trova i pivot più estremi
        for (Integer[] p : pivots) {
            // top
            couldBeCorner = true;
            for (Integer[] pp : pivots) {
                if (p[1] == pp[1])
                    continue;
                if (pp[1] <= p[1])
                    couldBeCorner = false;
            }
            if (couldBeCorner)
                top.add(p);
            // bottom
            couldBeCorner = true;
            for (Integer[] pp : pivots) {
                if (p[1] == pp[1])
                    continue;
                if (pp[1] >= p[1])
                    couldBeCorner = false;
            }
            if (couldBeCorner)
                bottom.add(p);
            // right
            couldBeCorner = true;
            for (Integer[] pp : pivots) {
                if (p[0] == pp[0])
                    continue;
                if (pp[0] >= p[0])
                    couldBeCorner = false;
            }
            if (couldBeCorner)
                right.add(p);
            // left
            couldBeCorner = true;
            for (Integer[] pp : pivots) {
                if (p[0] == pp[0])
                    continue;
                if (pp[0] <= p[0])
                    couldBeCorner = false;
            }
            if (couldBeCorner)
                left.add(p);
        }
        newCorners.put("TOP", top);
        newCorners.put("BOTTOM", bottom);
        newCorners.put("LEFT", left);
        newCorners.put("RIGHT", right);
        return newCorners;
    }

    public void findPivotAndCorners(PlayArea playArea) {
        this.pivots = findPivots(playArea);
        this.corners = findCorners(this.pivots);

    }

    public void printPivot() {
        for (int i = 0; i < pivots.size(); i++) {
            Integer[] coordinates = pivots.get(i);
            System.out.println("Pivot " + i + ":  (" + coordinates[0] + ", " + coordinates[1] + ")");
        }
        System.out.println("Pivot Count: " + pivots.size());
    }

    public void printCorners() {
        for (Map.Entry<String, List<Integer[]>> entry : corners.entrySet()) {
            String key = entry.getKey();
            List<Integer[]> value = entry.getValue();
            System.out.println(key + " corners:");
            for (int i = 0; i < value.size(); i++) {
                Integer[] coordinates = value.get(i);
                System.out.println("  (" + coordinates[0] + ", " + coordinates[1] + ")");
            }
        }
    }

    public boolean isCorner(Integer[] pivot) {
        return corners.get("TOP").contains(pivot) ||
                corners.get("BOTTOM").contains(pivot) ||
                corners.get("LEFT").contains(pivot) ||
                corners.get("RIGHT").contains(pivot);
    }

    public boolean isPivot(Integer[] pivot) {
        return pivots.contains(pivot);
    }

    public boolean isTopCorner(Integer[] pivot) {
        return corners.get("TOP").contains(pivot);
    }

    public boolean isBottomCorner(Integer[] pivot) {
        return corners.get("BOTTOM").contains(pivot);
    }

    public boolean isLeftCorner(Integer[] pivot) {
        return corners.get("LEFT").contains(pivot);
    }

    public boolean isRightCorner(Integer[] pivot) {
        return corners.get("RIGHT").contains(pivot);
    }

    public static Map<String, Integer[]> getCardCorners(CardTUI card) {
        Map<String, Integer[]> corners = new HashMap<>();
        for (int j = 0; j < card.getCardLines().size(); j++) {
            for (int i = 0; i < card.getCardLines().get(j).length(); i++) {
                if (card.getCardLines().get(j).charAt(i) == '┌') {
                    corners.put("TL", new Integer[] { i, j });
                } else if (card.getCardLines().get(j).charAt(i) == '└') {
                    corners.put("BL", new Integer[] { i, j });
                } else if (card.getCardLines().get(j).charAt(i) == '┐') {
                    corners.put("TR", new Integer[] { i, j });
                } else if (card.getCardLines().get(j).charAt(i) == '┘') {
                    corners.put("BR", new Integer[] { i, j });
                }
            }
        }
        return corners;
    }

    public static void printCorners(Map<String, List<Integer[]>> corners) {
        for (Map.Entry<String, List<Integer[]>> entry : corners.entrySet()) {
            String key = entry.getKey();
            List<Integer[]> value = entry.getValue();
            System.out.println(key + " corners:");
            for (int i = 0; i < value.size(); i++) {
                Integer[] coordinates = value.get(i);
                System.out.println("  (" + coordinates[0] + ", " + coordinates[1] + ")");
            }
        }
    }

    public static void printCardCorners(Map<String, Integer[]> corners) {
        for (Map.Entry<String, Integer[]> entry : corners.entrySet()) {
            String key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println(key + " corner:");
            System.out.println("  (" + value[0] + ", " + value[1] + ")");
        }
    }

    public static void printPivots(List<Integer[]> pivots) {
        for (int i = 0; i < pivots.size(); i++) {
            Integer[] coordinates = pivots.get(i);
            System.out.println("Pivot " + i + ":  (" + coordinates[0] + ", " + coordinates[1] + ")");
        }
    }

    public static void printPivot(Integer[] pivot) {
        System.out.println(pivotToString(pivot));
    }

    public static String pivotToString(Integer[] pivot) {
        return "Pivot: (" + pivot[0] + ", " + pivot[1] + ")";

    }
}
