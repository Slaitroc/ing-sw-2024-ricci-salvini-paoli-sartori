package it.polimi.ingsw.gc31.model.card;

import java.util.Collections;
import java.util.Map;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class ResourceCard extends PlayableCard{
    // TODO non sono convinto
    public ResourceCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
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
