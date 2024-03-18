package it.polimi.ingsw.gc31.model.card;

import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class ObjectiveCard extends Card{
    public ObjectiveCard(Objective ob, int score, String dirImgFront, String dirImgBack) throws WrongNumberOfCornerException, DirImgValueMissingException {
        super(
                new CardFront(score, new ArrayList<>(), Collections.emptyMap(), dirImgFront, ob),
                new CardBack(new ArrayList<>(), dirImgBack));
    }

    /**
     *
     * @return
     */
    @Override
    public Objective getObjective() {
        return front.getObjective();
    }

}
