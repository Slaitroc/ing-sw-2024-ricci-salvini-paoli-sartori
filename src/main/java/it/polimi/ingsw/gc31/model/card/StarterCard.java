package it.polimi.ingsw.gc31.model.card;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class StarterCard extends PlayableCard{
    public StarterCard(CardFront front, CardBack back) {
        super(null, front, back);
    }
    public StarterCard(List<Resources> resourcesFront, List<Resources> resourcesBack, String dirImgFront, String dirImgBack) throws WrongNumberOfCornerException, DirImgValueMissingException {
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
