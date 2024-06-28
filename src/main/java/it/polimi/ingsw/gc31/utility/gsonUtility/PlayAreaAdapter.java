package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The PlayAreaAdapter class implements the JsonSerializer and JsonDeserializer interfaces
 * to serialize and deserialize a LinkedHashMap<Point, PlayableCard> object to and from JSON.
 */
public class PlayAreaAdapter
        implements JsonSerializer<LinkedHashMap<Point, PlayableCard>>,
        JsonDeserializer<LinkedHashMap<Point, PlayableCard>> {
    @Override
    public JsonElement serialize(LinkedHashMap<Point, PlayableCard> pointPlayableCardMap, Type type,
            JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<Point, PlayableCard> entry : pointPlayableCardMap.entrySet()) {
            String point = entry.getKey().x + "," + entry.getKey().y;
            jsonObject.add(point, jsonSerializationContext.serialize(entry.getValue(), PlayableCard.class));
        }
        return jsonObject;
    }

    @Override
    public LinkedHashMap<Point, PlayableCard> deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        LinkedHashMap<Point, PlayableCard> res = new LinkedHashMap<>();

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
