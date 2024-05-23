package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.serializePrivateFields;
import java.lang.reflect.Type;

public class ClientQueueObjAdapter implements JsonSerializer<ClientQueueObject>, JsonDeserializer<ClientQueueObject> {
    @Override
    public ClientQueueObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(ClientQueueObject src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        serializePrivateFields(src, jsonObject, context);
        return jsonObject;
    }
}
