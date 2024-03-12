package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.*;

public class GoldCard extends Card implements PlayableCard {
    public GoldCard(int score, List<Resources> resources, Map<Resources, Integer> requirements, String dirImg, Objective ob) {
        super();
        front = new CardFront(score, resources, requirements, dirImg, ob);
        back = new CardBack(resources, dirImg);
    }

    @Override
    public Color getColor() {
        return color;
    }
    @Override
    public boolean checkCorner(int corner) {
        if (side) return front.checkCorner(corner);
        else return back.checkCorner(corner);
    }
    @Override
    public void coverCorner(int corner) {
    }

    @Override
    public List<Resources> getResources() {
        return null;
    }

    @Override
    public Map<Resources, Integer> getRequirements() {
        return null;
    }

    @Override
    public Objective getObjetive() {
        return null;
    }
}
