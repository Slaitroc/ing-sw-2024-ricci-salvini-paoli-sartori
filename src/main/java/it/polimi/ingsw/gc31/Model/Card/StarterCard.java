package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.List;
import java.util.Map;

public class StarterCard extends Card implements PlayableCard{

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean checkCorner(int corner) {
        return false;
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
