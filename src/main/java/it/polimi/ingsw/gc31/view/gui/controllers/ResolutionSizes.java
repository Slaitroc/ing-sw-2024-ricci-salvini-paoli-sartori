package it.polimi.ingsw.gc31.view.gui.controllers;

public enum ResolutionSizes {
    FHD("FHD"),
    HD("HD"),
    SD("SD");

    private final String size;

    ResolutionSizes(String size){this.size = size;}

    public Integer getWidth(){
        if(size.equals("FHD")) return 240;
        if(size.equals("HD")) return 210;
        return 150;
    }
    public Integer getPaneWidth(){
        if(size.equals("FHD")) return 180;
        if(size.equals("HD")) return 158;
        return 113;
    }

    public Integer getHeight(){
        if(size.equals("FHD")) return 160;
        if(size.equals("HD")) return 140;
        return 100;
    }

    public Integer getPaneHeight(){
        if(size.equals("FHD")) return 96;
        if(size.equals("HD")) return 84;
        return 60;
    }
}
