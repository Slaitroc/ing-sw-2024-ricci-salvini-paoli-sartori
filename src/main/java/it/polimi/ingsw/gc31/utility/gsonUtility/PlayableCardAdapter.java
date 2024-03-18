package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.Card.*;
import it.polimi.ingsw.gc31.model.Enum.Color;
import it.polimi.ingsw.gc31.model.enumeration.Color;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class PlayableCardAdapter implements JsonSerializer<PlayableCard>, JsonDeserializer<PlayableCard>{
    @Override
    public JsonElement serialize(PlayableCard playableCard, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("color", playableCard.getColor().toString());
        jsonObject.add("front", playableCard.frontSerializeToJson());
        jsonObject.add("back", playableCard.backSerializeToJson());

        return jsonObject;
    }

    @Override
    public PlayableCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive("color");
        Color color = Color.valueOf(jsonPrimitive.getAsString());

        JsonElement frontElement = jsonObject.get("front");
        CardFront front = jsonDeserializationContext.deserialize(frontElement, CardFront.class);
        JsonElement backElement = jsonObject.get("back");
        CardBack back = jsonDeserializationContext.deserialize(backElement, CardBack.class);
        return new GoldCard(
                color,
                front,
                back
        );
    }
}
