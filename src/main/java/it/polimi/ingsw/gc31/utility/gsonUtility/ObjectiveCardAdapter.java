package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

import java.lang.reflect.Type;

public class ObjectiveCardAdapter implements JsonDeserializer<ObjectiveCard> {
    @Override
    public ObjectiveCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
