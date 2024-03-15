package it.polimi.ingsw.gc31.Model.Card;

import com.google.gson.JsonObject;
import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GoldCard extends PlayableCard {

    public GoldCard(Color color, int score, List<Resources> resourcesFront, List<Resources> resourcesBack, Map<Resources, Integer> requirements, String dirImgFront, String dirImgBack, Objective ob) {
        super(color,
                new CardFront(score, resourcesFront, requirements, dirImgFront, ob),
                new CardBack(resourcesBack, dirImgBack));
    }
    @Override
    public Objective getObjective() {
        if (side) return front.getObjective();
        else return null;
    }
}
