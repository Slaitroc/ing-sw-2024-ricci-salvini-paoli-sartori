package it.polimi.ingsw.gc31.GsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Exceptions.WrongNumberOfCornerException;

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
