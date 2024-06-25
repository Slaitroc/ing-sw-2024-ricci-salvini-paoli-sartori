package it.polimi.ingsw.gc31.view.gui;

public enum ResolutionSizes {
    FHD("FHD"),
    HD("HD"),
    SD("SD");

    private final String size;

    ResolutionSizes(String size) {
        this.size = size;
    }

    /**
     * @return Width size for standard cards
     */
    public Integer getCardWidth() {
        if (size.equals("FHD")) return 240;
        if (size.equals("HD")) return 210;
        return 135;
    }

    /**
     * @return Width size for the PlayArea gridPane cell (to apply to the StackPane)
     */
    public Integer getPaneWidth() {
        if (size.equals("FHD")) return 180;
        if (size.equals("HD")) return 158;
        return 102;
    }

    /**
     * @return Height size for standard cards
     */
    public Integer getCardsHeight() {
        if (size.equals("FHD")) return 160;
        if (size.equals("HD")) return 140;
        return 90;
    }

    /**
     * @return Height size for the PlayArea gridPane cell (to apply to the StackPane)
     */
    public Integer getPaneHeight() {
        if (size.equals("FHD")) return 96;
        if (size.equals("HD")) return 84;
        return 54;
    }
}
