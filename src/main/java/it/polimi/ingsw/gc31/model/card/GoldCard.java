package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;

/**
 * The GoldCard class represents a playable card with gold properties.
 * It extends the PlayableCard class and inherits its methods and attributes.
 */
public class GoldCard extends PlayableCard {
    public GoldCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
    }
}
