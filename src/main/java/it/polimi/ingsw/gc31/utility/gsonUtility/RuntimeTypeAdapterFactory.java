package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RuntimeTypeAdapterFactory<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
