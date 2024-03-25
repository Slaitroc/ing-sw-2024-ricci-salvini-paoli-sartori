package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.awt.*;

public abstract class PlayerState {

    public abstract void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException;

    public abstract boolean addToHand(PlayableCard card) throws IllegalStateOperationException;

    public abstract void moveCardInHand() throws IllegalStateOperationException;

    public abstract void play(PlayableCard card, Point point) throws IllegalStateOperationException;

    public abstract PlayerState changeState();
}
