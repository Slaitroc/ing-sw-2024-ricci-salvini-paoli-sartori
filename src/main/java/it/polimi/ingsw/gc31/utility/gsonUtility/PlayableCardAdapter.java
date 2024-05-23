package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.serializePrivateFields;

public class PlayableCardAdapter implements JsonSerializer<PlayableCard>, JsonDeserializer<PlayableCard> {
    @Override
    public JsonElement serialize(PlayableCard playableCard, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        if (playableCard.getColor() == null) {
            jsonObject.add("color", null);
        } else {
            jsonObject.addProperty("color", playableCard.getColor().toString());
        }

        serializePrivateFields(playableCard, jsonObject, jsonSerializationContext);

        return jsonObject;
    }

    @Override
    public PlayableCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        PlayableCard card = null;
        CardColor cardColor;
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

        JsonElement jsonCardType = jsonObject.get("cardType");
        if (jsonCardType == null) {
            try {
                JsonElement className = jsonObject.get("type");
                Class<? extends PlayableCard> clazz = null;
                if (className != null) {
                    clazz = (Class<? extends PlayableCard>) Class.forName(className.getAsString());
                }
                Constructor<? extends PlayableCard> constructor = clazz.getConstructor(CardColor.class, CardFront.class, CardBack.class);

                card = constructor.newInstance(cardColor, front, back);
            } catch (InstantiationException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            CardType cardType = CardType.valueOf(jsonCardType.getAsString());
            if (cardType.equals(CardType.GOLD)) {
                card = new GoldCard(cardColor, front, back);
            } else if (cardType.equals(CardType.RESOURCE)) {
                card = new ResourceCard(cardColor, front, back);
            } else if (cardType.equals(CardType.STARTER)) {
                card = new StarterCard(cardColor, front, back);
            }
        }

        if (jsonObject.has("side") && jsonObject.get("side").getAsBoolean()) {
            card.changeSide();
        }
        return card;
    }
}
