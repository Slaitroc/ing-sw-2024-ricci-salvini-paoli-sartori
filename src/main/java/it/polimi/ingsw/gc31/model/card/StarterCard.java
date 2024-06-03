package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class StarterCard extends PlayableCard {
    public StarterCard(CardColor color, CardFront front, CardBack back) {
        super(color, front, back);
    }

    @Override
    public Objective getObjective() {
        return null;
    }

}
