package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.card.CardBack;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// TODO implementare eccezioni, resources deve essere
public class BackClassAdapter implements JsonDeserializer<CardBack> {
    @Override
    public CardBack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // extract the list of Resources using the adapter ListResourcesEnumAdapter
        // jsonObject.get("resources") cannot be null
        List<Resources> resources;
        if (jsonObject.get("resources").isJsonNull()) {
            resources = new ArrayList<>();
        } else {
            resources = jsonDeserializationContext.deserialize(jsonObject.get("resources"), Resources.class);
        }

        // get("dirImg") can be null.
        String dirImg;
        if (jsonObject.get("dirImg") == null) {
            dirImg = jsonObject.get("dirImg").getAsString();
        } else {
            dirImg = null;
        }
        return new CardBack(
                resources,
                dirImg
        );
    }
}
