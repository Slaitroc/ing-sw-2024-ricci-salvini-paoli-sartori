package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.IllegalStateOperationException;

import java.awt.*;
import java.util.Scanner;

public abstract class PlayerState {

    public abstract void addObjectiveCard(ObjectiveCard card, Player player) throws IllegalStateOperationException;

    public abstract void addToHand(PlayableCard card, Player player) throws IllegalStateOperationException;

    public abstract void moveCardInHand(Player player) throws IllegalStateOperationException;

    public abstract void play(PlayableCard card, Point point, Player player) throws IllegalStateOperationException;

    public abstract PlayerState changeState();

    //Notice: Intellij gives me a warning if I copy the same code 4 times
    //        In this way I can execute the common code writing it here
    public void executeMoveCardInHand(Player player){
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

}


