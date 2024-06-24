package it.polimi.ingsw.gc31.view.gui;

public enum ResolutionSizes {
    FHD("FHD"),
    HD("HD"),
    SD("SD");

    private final String size;

    ResolutionSizes(String size){this.size = size;}

    public Integer getCardWidth(){
        if(size.equals("FHD")) return 240;
        if(size.equals("HD")) return 210;
        return 135;
    }
    public Integer getPaneWidth(){
        if(size.equals("FHD")) return 180;
        if(size.equals("HD")) return 158;
        return 102;
    }

    public Integer getCardsHeight(){
        if(size.equals("FHD")) return 160;
        if(size.equals("HD")) return 140;
        return 90;
    }

    public Integer getPaneHeight(){
        if(size.equals("FHD")) return 96;
        if(size.equals("HD")) return 84;
        return 54;
    }
}
