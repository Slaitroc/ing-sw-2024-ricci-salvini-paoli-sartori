package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;

public abstract class Start extends PlayerState {
    @Override
    public void addObjectiveCard(ObjectiveCard card) {
        this.addObjectiveCard(objectiveCard, this);
    }


}
