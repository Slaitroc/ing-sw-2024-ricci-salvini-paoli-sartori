package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.util.ArrayList;
import java.util.List;

public class CardBack {
    private final List<Resources> resources;
    private final String dirImg;

    public CardBack(List<Resources> resources, String dirImg) {
        this.resources = listDeepCopy(resources);
        this.dirImg = dirImg;
    }

    public boolean checkCorner(int corner) {
        return resources.get(corner) != Resources.HIDDEN;
    }

    public Resources coverCorner(int corner) {
        Resources ret = resources.get(corner);
        resources.set(corner, Resources.HIDDEN);
        return ret;
    }

    public List<Resources> getResources() {
        List<Resources> res = new ArrayList<>();
        for (Resources val : resources) {
            if (val != Resources.HIDDEN && val != Resources.EMPTY) {
                res.add(val);
            }
        }
        return res;
    }

    public String getImage() {
        return this.dirImg;
    }

    private List<Resources> listDeepCopy(List<Resources> listToCopy) {
        List<Resources> newList = new ArrayList<>();
        for (Resources val : listToCopy) {
            newList.add(val);
        }
        return newList;
    }

    public List<Resources> getCorners() {
        return resources;
    }
}
