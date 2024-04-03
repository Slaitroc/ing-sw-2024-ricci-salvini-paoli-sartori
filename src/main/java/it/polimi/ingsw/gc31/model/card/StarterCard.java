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
    public Map<Resources, Integer> getRequirements() {
        return Collections.emptyMap();
    }

    @Override
    public Objective getObjective() {
        return null;
    }

    @Override
    public Card deepCopy() {
        return null;
    }
}
