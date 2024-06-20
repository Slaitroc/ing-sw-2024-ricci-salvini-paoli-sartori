package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.Collections;
import java.util.Map;

public class GoldCard extends PlayableCard {
    public GoldCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
    }

    @Override
    public Objective getObjective() {
        if (side)
            return front.getObjective();
        else
            return null;
    }
    public Map<Resources, Integer> getRequirements() {
        if (side)
            return front.getRequirements();
        else
            return Collections.emptyMap();
    }
}
