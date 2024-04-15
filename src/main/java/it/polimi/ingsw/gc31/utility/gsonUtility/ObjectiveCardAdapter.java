package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.lang.reflect.Type;

public class ObjectiveCardAdapter implements JsonDeserializer<ObjectiveCard> {
    @Override
    public ObjectiveCard deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int score = jsonObject.get("score").getAsInt();

        // TODO objective non può essere nullo
        Objective ob;
        if (jsonObject.get("objective").isJsonNull()) {
            ob = null;
        } else {
            JsonElement obElement = jsonObject.get("objective");
            ob = jsonDeserializationContext.deserialize(obElement, Objective.class);
        }

        String dirImgFront;
        if (jsonObject.get("dirImgFront").isJsonNull()) {
            dirImgFront = null;
        } else {
            dirImgFront = jsonObject.get("dirImgFront").getAsString();
        }

        String dirImgBack;
        if (jsonObject.get("dirImgBack").isJsonNull()) {
            dirImgBack = null;
        } else {
            dirImgBack = jsonObject.get("dirImgBack").getAsString();
        }

        return new ObjectiveCard(score, ob, dirImgBack, dirImgFront);
    }
}
