package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;

/**
 * ResourceCard class represents a playable card with resource properties.
 * It extends the PlayableCard class and inherits its methods and attributes.
 */
public class ResourceCard extends PlayableCard {
    public ResourceCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
    }
}
