package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.exceptions.FullHandException;

public class Start extends PlayerState {

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) {
        player.setObjectiveCard(card);
    }

    @Override
    public void addToHand(PlayableCard card, Player player) throws NullPointerException, FullHandException {
        if (player.hand.size() > 3) {
            System.out.println("The player: " + player + "is full");
            throw new FullHandException();
        }
        try {
            player.hand.add(card);
        } catch (NullPointerException e) {
            System.out.println("There was a problem adding card in hand (is card null?)");
            e.getStackTrace();
        }
    }

    @Override
    public void moveCardInHand(Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void play(Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(Player player){
        player.getPlayArea().placeStarter(player.getStarterCard());
    }

}
