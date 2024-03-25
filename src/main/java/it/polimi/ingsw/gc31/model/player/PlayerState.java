package it.polimi.ingsw.gc31.model.player;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;

public interface PlayerState {
    public void addObjectiveCard(ObjectiveCard card, Player player);

    public int getScore(Player player);

    public boolean addToHand(PlayableCard card);

    public void moveCardInHand();

    public void play (PlayableCard card, Point point);

    public PlayArea getPlayArea();

    public Player getPlayer();

    public String getName();

}
