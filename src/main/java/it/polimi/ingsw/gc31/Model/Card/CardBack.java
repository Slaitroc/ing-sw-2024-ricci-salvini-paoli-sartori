package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Resources;

import java.util.ArrayList;
import java.util.List;

public class CardBack {
    private List<Resources> resources;
    private String dirImg;
    //TODO implementare deep copy per resources
    public CardBack(List<Resources> resources, String dirImg) {
        this.resources = new ArrayList<>(resources);
        this.dirImg = dirImg;
    }

    public boolean checkCorner(int corner) {
        return true;
    }
}
