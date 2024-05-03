package it.polimi.ingsw.gc31.model.player;

import java.awt.Point;

import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.InvalidCardDraw;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

public class Start extends PlayerState {
    private boolean objectiveChosen = false;

    @Override
    public void addObjectiveCard(ObjectiveCard card, Player player) {
        player.setObjectiveCard(card);
        objectiveChosen = true;
    }

    @Override
    public void addToHand(PlayableCard card, Player player, Boolean byDeck) throws FullHandException, InvalidCardDraw {
        if (!byDeck) {
            throw new InvalidCardDraw();
        }
        if (player.hand.size() > 3) {
            throw new FullHandException();
        }
        executeAddToHand(card, player);
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
    public void playStarter(Player player) throws ObjectiveCardNotChosenException {
        if (objectiveChosen) {
            player.getPlayArea().placeStarter(player.getStarterCard());
            player.setInGameState(new NotPlaced());
            System.out.println("Player: " + player.getUsername() + " IS NOW READY TO PLAY.");
        }
        else {
            throw new ObjectiveCardNotChosenException();
        }
    }

}
