package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class GoldCard extends PlayableCard {
    public GoldCard(Color color, CardFront front, CardBack back) {
        super(color, front, back);
    }

    @Override
    public Objective getObjective() {
        if (side)
            return front.getObjective();
        else
            return null;
    }

    @Override
    public GoldCard deepCopy() {
        return new GoldCard(
                color,
                front.deepCopy(),
                back.deepCopy());
    }
}
