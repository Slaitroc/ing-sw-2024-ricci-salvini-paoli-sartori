package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.gc31.model.card.CardFront;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.DirImgValueMissingException;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// TODO implementare exception, dirImg non può essere nulla, resources non può essere nulla
// TODO riguardare eccezione

/**
 * Adapter for deserializing the class {@link CardFront}.
 * It can be registered to a GsonBuilder when you have to deserialize an object of the class {@link CardFront}
 * or any other object that contains a parameter of the {@link CardFront} class.
 * Gson gson = new GsonBuilder().registerTypeAdapter(CardFront.class, new FrontClassAdapter()).create();
 *
 */
public class FrontClassAdapter implements JsonDeserializer<CardFront> {

    /**
     * Method that deserialize an object of the {@link CardFront} class.
     * @param jsonElement an object of type JsonElement containing the information to be extracted.
     * @param type class of the object to be deserialized.
     * @return it returns the deserialized object. It returns an object of the {@link CardFront} class.
     * @throws JsonParseException
     */
    @Override
    public CardFront deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int score = jsonObject.get("score").getAsInt();
        // extract the list of Resources using the adapter ListResourcesEnumAdapter
        List<Resources> resources = jsonDeserializationContext.deserialize(jsonObject.get("resources"), Resources.class);
        // extract the map of requirements using the adapter MapRequirementsAdapter.
        // TypeToken<Map<Resources, Integer>>(){}.getType() used to create the Type for a map like Map<Resources, Integer>
        // get("requirements") can be null.
        Map<Resources, Integer> requirements;
        if (jsonObject.get("requirements").isJsonNull()) {
            requirements = Collections.emptyMap();
        } else {
            requirements= jsonDeserializationContext.deserialize(jsonObject.get("requirements"), new TypeToken<Map<Resources, Integer>>(){}.getType());
        }

        String dirImg;
        if (jsonObject.get("dirImg").isJsonNull()) {
            dirImg = null;
        } else {
            dirImg = jsonObject.get("dirImg").getAsString();
        }

        // Create and returns a new object of the type CardFront
        CardFront front = null;
        try {
            front = new CardFront(score, resources, requirements, dirImg, null);
        } catch (WrongNumberOfCornerException e) {
            throw new RuntimeException(e);
        } catch (DirImgValueMissingException e) {
            throw new RuntimeException(e);
        }
        return front;
    }
}