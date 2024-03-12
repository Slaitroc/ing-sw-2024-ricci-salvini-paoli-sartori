package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardFront {
    private final int score;
    private List<Resources> resources;
    private final Map<Resources, Integer> requirements;
    private final String dirImg;
    private final Objective ob;

    public CardFront(int score, List<Resources> resources, Map<Resources, Integer> requirements, String dirImg, Objective ob) {
        this.score = score;
        //TODO implementare depp copy per resources, requirementes e ob
        this.resources = new ArrayList<>(resources);
        this.requirements = new HashMap<>(requirements);

        this.dirImg = dirImg;
        this.ob = ob;
    }

    public boolean checkCorner(int corner) {
        return true;
    }

}
