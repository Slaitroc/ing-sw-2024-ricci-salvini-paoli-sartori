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

    public GoldCard(Color color, int score, List<Resources> resourcesFront, List<Resources> resourcesBack, Map<Resources, Integer> requirements, String dirImgFront, String dirImgBack, Objective ob) throws WrongNumberOfCornerException, DirImgValueMissingException {
        super(color,
                new CardFront(score, resourcesFront, requirements, dirImgFront, ob),
                new CardBack(resourcesBack, dirImgBack));
    }
    //TODO serve solo per la serializzazione, da togliere
    public GoldCard(Color color, CardFront front, CardBack back) {
        super(color, front, back);
    }
    @Override
    public Objective getObjective() {
        if (side) return front.getObjective();
        else return null;
    }

    @Override
    public GoldCard deepCopy() {
        return new GoldCard(
                color,
                front.deepCopy(),
                back.deepCopy()
        );
    }
}
