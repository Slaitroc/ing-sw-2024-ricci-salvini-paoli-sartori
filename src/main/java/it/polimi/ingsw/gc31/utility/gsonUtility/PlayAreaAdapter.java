package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PlayAreaAdapter
        implements JsonSerializer<Map<Point, PlayableCard>>, JsonDeserializer<Map<Point, PlayableCard>> {
    @Override
    public JsonElement serialize(Map<Point, PlayableCard> pointPlayableCardMap, Type type,
            JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<Point, PlayableCard> entry : pointPlayableCardMap.entrySet()) {
            String point = entry.getKey().x + "," + entry.getKey().y;
            jsonObject.add(point, jsonSerializationContext.serialize(entry.getValue(), PlayableCard.class));
        }
        return jsonObject;
    }

    @Override
    public Map<Point, PlayableCard> deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<Point, PlayableCard> res = new HashMap<>();

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String[] coordinate = entry.getKey().split(",");
            Point point = new Point(
                    Integer.parseInt(coordinate[0]),
                    Integer.parseInt(coordinate[1]));

            PlayableCard playableCard = jsonDeserializationContext.deserialize(entry.getValue(), PlayableCard.class);

            res.put(point, playableCard);
        }
        return res;
    }
}
