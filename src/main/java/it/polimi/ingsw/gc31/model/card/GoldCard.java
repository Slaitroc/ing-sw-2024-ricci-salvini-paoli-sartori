package it.polimi.ingsw.gc31.model.card;

import com.google.gson.JsonObject;

import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GoldCard extends PlayableCard {
    public GoldCard(CardColor cardColor, CardFront front, CardBack back) {
        super(cardColor, front, back);
    }
    @Override
    public Objective getObjective() {
        if (side) return front.getObjective();
        else return null;
    }

    @Override
    public GoldCard deepCopy() {
        return new GoldCard(
                cardColor,
                front.deepCopy(),
                back.deepCopy()
        );
    }
}
