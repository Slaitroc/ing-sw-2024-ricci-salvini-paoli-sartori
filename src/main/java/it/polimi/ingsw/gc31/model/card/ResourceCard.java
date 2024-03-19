package it.polimi.ingsw.gc31.model.card;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class ResourceCard extends PlayableCard{
    // TODO non sono convinto
    public ResourceCard(Color color, CardFront front, CardBack back) {
        super(color, front, back);
    }
    public ResourceCard(Color color , int score, List<Resources> resourcesFront, List<Resources> resourcesBack, String dirImgFront, String dirImgBack) throws WrongNumberOfCornerException, DirImgValueMissingException {
        super(color,
                new CardFront(score, resourcesFront, Collections.emptyMap(), dirImgFront, null),
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
