package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

import java.lang.reflect.Type;

public class PlayableCardAdapter implements JsonSerializer<PlayableCard>, JsonDeserializer<PlayableCard> {
    @Override
    public JsonElement serialize(PlayableCard playableCard, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        if (playableCard.getClass().equals(GoldCard.class)) {
            jsonObject.addProperty("cardType", "GOLD");
        }
        else if (playableCard.getClass().equals(ResourceCard.class)) {
            jsonObject.addProperty("cardType", "RESOURCE");
        }
        else if (playableCard.getClass().equals(StarterCard.class)) {
            jsonObject.addProperty("cardType", "STARTER");
        }

        if (playableCard.getColor() == null) {
            jsonObject.add("color", null);
        } else {
            jsonObject.addProperty("color", playableCard.getColor().toString());
        }
        jsonObject.add("front", playableCard.frontSerializeToJson());
        jsonObject.add("back", playableCard.backSerializeToJson());

        return jsonObject;
    }

    @Override
    public PlayableCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        CardType cardType = CardType.valueOf(jsonObject.get("cardType").getAsString());

        CardColor cardColor = null;
        if (jsonObject.get("color").isJsonNull()) {
            cardColor = null;
        } else {
            JsonElement jsonElementColor = jsonObject.get("color");
            cardColor = CardColor.valueOf(jsonElementColor.getAsString());
        }

        JsonElement frontElement = jsonObject.get("front");
        CardFront front = jsonDeserializationContext.deserialize(frontElement, CardFront.class);
        JsonElement backElement = jsonObject.get("back");
        CardBack back = jsonDeserializationContext.deserialize(backElement, CardBack.class);
        if (cardType.equals(CardType.GOLD)) {
            return new GoldCard(cardColor, front, back);
        } else if (cardType.equals(CardType.RESOURCE)) {
            return new ResourceCard(cardColor, front, back);
        } else if (cardType.equals(CardType.STARTER)) {
            return new StarterCard(cardColor, front, back);
        }
        // TODO non so se è giusto
        return null;
    }
}
