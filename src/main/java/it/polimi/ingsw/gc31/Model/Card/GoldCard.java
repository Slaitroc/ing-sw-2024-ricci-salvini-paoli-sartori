package it.polimi.ingsw.gc31.Model.Card;

import com.google.gson.JsonObject;
import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.Model.Exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

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
}
