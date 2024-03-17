package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.Model.Exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResourceCard extends PlayableCard{
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
