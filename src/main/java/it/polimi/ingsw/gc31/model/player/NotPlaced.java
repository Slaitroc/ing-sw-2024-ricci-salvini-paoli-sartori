package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;

public class NotPlaced extends PlayerState {

    @Override
    public void chooseSecretObjective(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(PlayableCard card, Player player, Boolean byDeck) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void moveCardInHand(Player player) {
        executeMoveCardInHand(player);
    }

    @Override
    public void play(Point point, Player player) {
        PlayableCard card = player.getSelectedCard();
        player.score += player.getPlayArea().place(card, point);
        player.hand.remove(card);
        player.setInGameState(new Placed());
        System.out.println("Player: "+ player.getUsername() + " HAS JUST PLAYED A CARD IN ("+ point.x +","+ point.y +")");
    }

    @Override
    public void playStarter(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
}
