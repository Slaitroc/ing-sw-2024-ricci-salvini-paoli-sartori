package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.FullHandException;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.awt.*;

public class Placed extends PlayerState {


    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void addToHand(PlayableCard card, Player player) throws FullHandException, NullPointerException{
        if (player.hand.size()>2) {
            System.out.println("The player: "+ player +"is full");
            throw new FullHandException();
        }
        try {
            player.hand.add(card);
        }
        catch (NullPointerException e) {
            System.out.println("There was a problem adding card in hand (is card null?)");
            e.getStackTrace();
        }
    }

    @Override
    public void moveCardInHand(Player player){
        executeMoveCardInHand(player);
    }

    @Override
    public void play(PlayableCard card, Point point, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }

    @Override
    public void playStarter(PlayableCard card, Player player) throws IllegalStateOperationException {
        throw new IllegalStateOperationException();
    }
}
