package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.CardType;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;

import java.util.ArrayList;
import java.util.Collections;

public class ObjectiveCard extends Card{
    public ObjectiveCard(Objective ob, int score, String dirImgFront, String dirImgBack) {
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
