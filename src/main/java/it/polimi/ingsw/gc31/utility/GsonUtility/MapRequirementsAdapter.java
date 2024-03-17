package it.polimi.ingsw.gc31.utility.GsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.Model.Card.CardFront;
import it.polimi.ingsw.gc31.Model.Enum.Resources;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Adapter for deserializing the class a map of the type Map<Resources, Integer>.
 * It can be registered to a GsonBuilder when you have to deserialize a map of the type Map<Resources, Integer>.
 * Use:
 * Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<Resources, Integer>>(){}.getType(), new MapRequirementsAdapter()).create();
 */
public class MapRequirementsAdapter implements JsonDeserializer<Map<Resources, Integer>> {

    /**
     * Method that deserialize a map of the type Map<Resources, Integer>.
     * @param jsonElement an object of type JsonElement containing the information to be extracted.
     * @param type class of the object to be deserialized.
     * @param jsonDeserializationContext
     * @return it returns the deserialized object. It returns a map of the type Map<Resources, Integer>.
     * @throws JsonParseException
     */
    @Override
    public Map<Resources, Integer> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<Resources, Integer> res = new HashMap<>();

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            // Extract the key. The extracted value must be converted to its corresponding enumerator value
            Resources key = Resources.valueOf(entry.getKey());
            // Extract the value as an int
            Integer value = entry.getValue().getAsInt();
            res.put(key, value);
        }
        // returns the map created
        return res;
    }
}