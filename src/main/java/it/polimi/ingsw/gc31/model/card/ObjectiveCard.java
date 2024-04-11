package it.polimi.ingsw.gc31.model.card;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;
import it.polimi.ingsw.gc31.model.strategies.Objective;

public class ObjectiveCard implements Card{
    private boolean side;
    private final int score;
    private final Objective objective;
    private final String dirImgFront;
    private final String dirImgBack;
    public ObjectiveCard(int score, Objective objective, String dirImgFront, String dirImgBack) {
        this.score = score;
        this.objective = objective;
        this.dirImgFront = dirImgFront;
        this.dirImgBack = dirImgBack;
        side = false;
    }

    @Override
    public boolean getSide() {
        return side;
    }

    @Override
    public void changeSide() {
        side = !side;
    }

    @Override
    public String getImage() {
        if (side)
            return dirImgFront;
        else return dirImgBack;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Objective getObjective() {
        return objective;
    }

    @Override
    public ObjectiveCard deepCopy() {
        return null;
    }

    @Override
    public JsonObject frontSerializeToJson() {
        return null;
    }

    @Override
    public JsonObject backSerializeToJson() {
        return null;
    }
}
