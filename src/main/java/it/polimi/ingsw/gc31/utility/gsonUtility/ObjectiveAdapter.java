package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjectiveAdapter implements JsonDeserializer<Objective> {
    @Override
    public Objective deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String typeOb = jsonObject.get("type").getAsString();

        Objective ob;

        // TODO cambiare switch?
        // Dovrebbe essere una specie di factory method
        switch(typeOb) {
            case "COUNT":
                ob = new Count(
                        jsonDeserializationContext.deserialize(jsonObject.get("resources"), Resources.class)
                );
                break;
            case "COVERCORNERSCORE":
                ob = new CoverCornerScore();
                break;
            case "LSHAPE":
                ob = new LShape();
                break;
            case "LSHAPEREVERSE":
                ob = new LShapeReverse();
                break;
            case "RESOURCESCORE":
                ob = new ResourceScore(
                      Resources.valueOf(jsonObject.get("resources").getAsString())
                );
                break;
            case "SEVEN":
                ob = new Seven();
                break;
            case "SEVENREVERSE":
                ob = new SevenReverse();
                break;
            case "STAIRDOWN":
                ob = new StairDown(
                        Color.valueOf(jsonObject.get("color1").getAsString())
                );
                break;
            case "STAIRUP":
                ob = new StairUp(
                        Color.valueOf(jsonObject.get("color1").getAsString())
                );
                break;
            default: ob = null;
        }
        return ob;
    }
}
