package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.awt.*;

public class NotPlaced extends PlayerState {

    @Override
    public void chooseSecretObjective(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(Deck<PlayableCard> deck, Deck<PlayableCard> substituteDeck, Player player, int index) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

//    @Override
//    public void moveCardInHand(Player player) {
//        executeMoveCardInHand(player);
//    }

    @Override
    public void play(Point point, Player player) {
        PlayableCard card = player.getSelectedCard();
        try {
            player.score += player.getPlayArea().place(card, point);
            player.hand.remove(card);
            player.setInGameState(new Placed());
        } catch (IllegalPlaceCardException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void playStarter(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public String stateInfo() {
        return "notplaced";
    }
}
