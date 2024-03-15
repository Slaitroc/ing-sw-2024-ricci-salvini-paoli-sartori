package it.polimi.ingsw.gc31.Model.Card;

import com.google.gson.JsonObject;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StarterCard extends PlayableCard{
    public StarterCard(List<Resources> resourcesFront, List<Resources> resourcesBack, String dirImgFront, String dirImgBack) {
        super(null,
                new CardFront(0, resourcesFront, Collections.emptyMap(), dirImgFront, null),
                new CardBack(resourcesBack, dirImgBack));
    }
    public Map<Resources, Integer> getRequirements() {
        return Collections.emptyMap();
    }
    @Override
    public Objective getObjective() {
        return null;
    }
}
