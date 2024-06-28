package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.lang.reflect.Type;

/**
 * This class is a JsonSerializer implementation for the Resources enum.
 */
public class ResourceEnumAdapter implements JsonSerializer<Resources> {
    @Override
    public JsonElement serialize(Resources src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
