package it.polimi.ingsw.gc31.model.card;

import java.util.Collections;
import java.util.Map;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

/**
 * ResourceCard class represents a playable card with resource properties.
 * It extends the PlayableCard class and inherits its methods and attributes.
 */
public class ResourceCard extends PlayableCard {
    public ResourceCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
    }
}
