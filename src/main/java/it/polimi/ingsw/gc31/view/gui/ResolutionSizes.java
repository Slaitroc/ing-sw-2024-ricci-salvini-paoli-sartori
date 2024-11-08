package it.polimi.ingsw.gc31.view.gui;

/**
 * Enumeration for GUI Card Resolution sizes
 */
public enum ResolutionSizes {
    FHD("FHD"),
    HD("HD"),
    SD("SD");

    private final String size;

    ResolutionSizes(String size) {
        this.size = size;
    }

    /**
     * Width size for standard cards
     *
     * @return Width size for standard cards
     */
    public Integer getCardWidth() {
        if (size.equals("FHD")) return 240;
        if (size.equals("HD")) return 210;
        return 135;
    }

    /**
     * Width size for the PlayArea gridPane cell
     *
     * @return Width size for the PlayArea gridPane cell (to apply to the StackPane)
     */
    public Integer getPaneWidth() {
        if (size.equals("FHD")) return 180;
        if (size.equals("HD")) return 158;
        return 102;
    }

    /**
     * Height size for standard cards
     *
     * @return Height size for standard cards
     */
    public Integer getCardsHeight() {
        if (size.equals("FHD")) return 160;
        if (size.equals("HD")) return 140;
        return 90;
    }

    /**
     * Height size for the PlayArea gridPane cell
     *
     * @return Height size for the PlayArea gridPane cell (to apply to the StackPane)
     */
    public Integer getPaneHeight() {
        if (size.equals("FHD")) return 96;
        if (size.equals("HD")) return 84;
        return 54;
    }
}
