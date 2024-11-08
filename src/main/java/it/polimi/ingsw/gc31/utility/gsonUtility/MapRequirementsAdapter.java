package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Adapter for deserializing a map of the type {@code Map<Resources, Integer>}.
 * It can be registered to a GsonBuilder when you have to deserialize a map of
 * the type {@code Map<Resources, Integer>}.
 * Use:
 * {@code Gson gson = new GsonBuilder().registerTypeAdapter(new
 * TypeToken<Map<Resources, Integer>>(){}.getType(), new
 * MapRequirementsAdapter()).create();}
 */
public class MapRequirementsAdapter implements JsonDeserializer<Map<Resources, Integer>>, JsonSerializer<Map<Resources, Integer>> {

    /**
     * Deserialize the object and return a map of type {@code Map<Resources, Integer>}.
     *
     * @param jsonElement                an object of type JsonElement containing the information to be extracted.
     * @param type                       class of the object to be deserialized.
     * @param jsonDeserializationContext context
     * @return The deserialized object. It returns a map of the type {@code Map<Resources, Integer>}.
     * @throws JsonParseException If an error occurs while parsing json
     */
    @Override
    public Map<Resources, Integer> deserialize(JsonElement jsonElement, Type type,
                                               JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<Resources, Integer> res = new HashMap<>();

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            // Extract the key. The extracted value must be converted to its corresponding
            // enumerator value
            Resources key = Resources.valueOf(entry.getKey());
            // Extract the value as an int
            Integer value = entry.getValue().getAsInt();
            res.put(key, value);
        }
        // returns the map created
        return res;
    }

    @Override
    public JsonElement serialize(Map<Resources, Integer> resourcesIntegerMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        for (Map.Entry<Resources, Integer> entry : resourcesIntegerMap.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("resource", entry.getKey().toString());
            jsonObject.addProperty("count", entry.getValue());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}