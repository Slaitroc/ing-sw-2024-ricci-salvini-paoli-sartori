package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.card.CardBack;
import it.polimi.ingsw.gc31.model.card.CardFront;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

import java.lang.reflect.Type;

public class ObjectiveCardAdapter implements JsonDeserializer<ObjectiveCard> {
    @Override
    public ObjectiveCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonElement frontElement = jsonObject.get("front");
        CardFront front = jsonDeserializationContext.deserialize(frontElement, CardFront.class);
        JsonElement backElement = jsonObject.get("back");
        CardBack back = jsonDeserializationContext.deserialize(backElement, CardBack.class);

        return new ObjectiveCard(front, back);
    }
}
