package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.exceptions.WrongNumberOfCornerException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListResourcesEnumAdapter implements JsonDeserializer<List<Resources>>  {
    @Override
    public List<Resources> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<Resources> res = new ArrayList<>();

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement el : jsonArray) {
            String elString = el.getAsString();
            Resources elResources = Resources.valueOf(elString);
            res.add(elResources);
        }

        return res;
    }
}
