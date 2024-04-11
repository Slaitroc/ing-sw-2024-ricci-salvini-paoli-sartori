package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.strategies.Objective;

public class ObjectiveCard extends Card {
    public ObjectiveCard(CardFront front, CardBack back) {
        super(front, back);
    }

    /**
     *
     * @return
     */
    // TODO da rivedere
    @Override
    public Objective getObjective() {
        return front.getObjective();
    }

    @Override
    public ObjectiveCard deepCopy() {
        return new ObjectiveCard(
                front.deepCopy(),
                back.deepCopy());
    }
}
