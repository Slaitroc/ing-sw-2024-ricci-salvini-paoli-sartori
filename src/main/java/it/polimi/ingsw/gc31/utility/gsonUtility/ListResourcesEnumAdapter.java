package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for deserializing a list of the type List<Resources>.
 * It can be registered to a GsonBuilder when you have to deserialize a list of
 * the type List<Resources>.
 * Use:
 * Gson gson = new GsonBuilder().registerTypeAdapter(new
 * TypeToken<List<Resources>>(){}.getType(), new
 * ListResourcesEnumAdapter()).create();
 */
public class ListResourcesEnumAdapter implements JsonDeserializer<List<Resources>> {
    /**
     * Method that deserialize a map of the type List<Resources>.
     *
     * @param jsonElement                contains the information to be extracted
     * @param type                       class of the object to be deserialized
     * @param jsonDeserializationContext context
     * @return it returns the deserialized object. It returns a list of the type
     *         List<Resources>
     * @throws JsonParseException
     */
    @Override
    public List<Resources> deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // Create an empty list
        List<Resources> res = new ArrayList<>();

        // Create an array from the jsonElement
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement el : jsonArray) {
            // Convert the extracted string from jsonArray into its corresponding Resources
            // value
            Resources elResources = Resources.valueOf(el.getAsString());
            // Add the converted element to the list
            res.add(elResources);
        }

        return res;
    }
}
