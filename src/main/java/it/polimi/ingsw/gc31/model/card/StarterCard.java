package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;

/**
 * This class represents a starter card that can be played in a game.
 * It extends the PlayableCard class and inherits its methods and attributes.
 */
public class StarterCard extends PlayableCard {
    public StarterCard(CardColor color, CardFront front, CardBack back) {
        super(color, front, back);
    }
}
