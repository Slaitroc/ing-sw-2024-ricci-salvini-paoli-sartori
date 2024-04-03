package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.exceptions.FullHandException;

import java.awt.*;
//import java.util.List;
import java.util.Scanner;

public abstract class PlayerState {
    public abstract void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException;

    public abstract void addToHand(PlayableCard card, Player player)
            throws IllegalStateOperationException, FullHandException;

    public abstract void moveCardInHand(Player player) throws IllegalStateOperationException;

    public abstract void play(Point point, Player player) throws IllegalStateOperationException;

    public abstract void playStarter(Player player) throws IllegalStateOperationException;

    // Notice: Intellij gives me a warning if I copy the same code 4 times
    // In this way I can execute the common code writing it here
    public void executeMoveCardInHand(Player player) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Insert position of the first card [1-3]: ");
        int cardPosition1 = myScanner.nextInt();
        PlayableCard card1 = player.hand.get(cardPosition1);

        System.out.println("Insert position of the second card [1-3]: ");
        int cardPosition2 = myScanner.nextInt();
        PlayableCard card2 = player.hand.get(cardPosition2);

        player.hand.set(cardPosition1, card2);
        player.hand.set(cardPosition2, card1);
        System.out.println("New Hand disposition: " + player.hand);
    }

    public void executeAddToHand(PlayableCard card, Player player) throws NullPointerException, FullHandException {
        if (player.hand.size() > 2) {
            throw new FullHandException();
        }
        try {
            player.hand.add(card);
        } catch (NullPointerException e) {
            System.out.println("There was a problem adding card in hand (is card null?)");
            e.getStackTrace();
        }
    }

}
