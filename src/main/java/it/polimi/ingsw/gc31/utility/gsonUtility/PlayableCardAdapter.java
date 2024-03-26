package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Color;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class PlayableCardAdapter implements JsonDeserializer<PlayableCard>{
    @Override
    public PlayableCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Color color = null;

        if (jsonObject.get("color").isJsonNull()) {
            color = null;
        } else {
            JsonElement jsonElementColor = jsonObject.get("color");
            color = Color.valueOf(jsonElementColor.getAsString());
        }

        JsonElement frontElement = jsonObject.get("front");
        CardFront front = jsonDeserializationContext.deserialize(frontElement, CardFront.class);
        JsonElement backElement = jsonObject.get("back");
        CardBack back = jsonDeserializationContext.deserialize(backElement, CardBack.class);
        if (type.equals(GoldCard.class)) {
            return new GoldCard(color, front, back);
        }
        else if (type.equals(ResourceCard.class)) {
            return new ResourceCard(color, front, back);
        }
        else if (type.equals(StarterCard.class)) {
            return new StarterCard(front, back);
        }
        //TODO non sono sicuro
        else {
            return null;
        }
    }
}
