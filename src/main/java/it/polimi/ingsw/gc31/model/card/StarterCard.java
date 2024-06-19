package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.Collections;
import java.util.Map;

public class StarterCard extends PlayableCard {
    public StarterCard(CardColor color, CardFront front, CardBack back) {
        super(color, front, back);
    }

    @Override
    public Map<Resources, Integer> getRequirements() {
        return Collections.emptyMap();
    }

    @Override
    public Objective getObjective() {
        return null;
    }
}
