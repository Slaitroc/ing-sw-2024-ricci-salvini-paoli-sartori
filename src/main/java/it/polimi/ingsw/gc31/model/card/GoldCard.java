package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.io.Serializable;

public class GoldCard extends PlayableCard {
    public GoldCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
    }
    @Override
    public Objective getObjective() {
        if (side) return front.getObjective();
        else return null;
    }

    @Override
    public GoldCard deepCopy() {
        return new GoldCard(
                cardColor,
                front.deepCopy(),
                back.deepCopy()
        );
    }
}
