package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.card.CardBack;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.serializePrivateFields;

/**
 * This class is an adapter that implements the JsonDeserializer and JsonSerializer interfaces for the {@link CardBack} class.
 * It is used to deserialize a JSON element into a CardBack object and serialize a CardBack object into a JSON element.
 * This adapter is specifically designed to handle the serialization and deserialization of CardBack objects.
 */
public class BackClassAdapter implements JsonDeserializer<CardBack>, JsonSerializer<CardBack>{
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
        if (jsonObject.get("dirImg").isJsonNull()) {
            dirImg = null;
        } else {
            dirImg = jsonObject.get("dirImg").getAsString();
        }

        return new CardBack(
                resources,
                dirImg
        );
    }

    @Override
    public JsonElement serialize(CardBack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        serializePrivateFields(src, jsonObject, context);
        return jsonObject;
    }
}
