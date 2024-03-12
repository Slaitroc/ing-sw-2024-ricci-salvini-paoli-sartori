package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.*;

public interface PlayableCard {
    public Color getColor();

    /**
     * 0 = Alto Dx
     * 1 = Basso Dx
     * 2 = Basso Sx
     * 3 = Alto Sx
     * @param corner
     * @return true if corner != Resource.HIDDEN, false in all other cases
     */
    public boolean checkCorner(int corner);
    public void coverCorner(int corner);
    public List<Resources> getResources();
    public Map<Resources, Integer> getRequirements();
    public Objective getObjetive();

}
