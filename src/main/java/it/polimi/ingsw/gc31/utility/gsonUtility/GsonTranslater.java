package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawResObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.*;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class GsonTranslater {
    public static Gson gsonTranslater = new GsonBuilder()
            .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
            .registerTypeAdapter(GoldCard.class, new PlayableCardAdapter())
            .registerTypeAdapter(StarterCard.class, new PlayableCardAdapter())
            .registerTypeAdapter(ResourceCard.class, new PlayableCardAdapter())
            .registerTypeAdapter(ObjectiveCard.class, new ObjectiveCardAdapter())
            .registerTypeAdapter(CardFront.class, new FrontClassAdapter())
            .registerTypeAdapter(CardBack.class, new BackClassAdapter())
            .registerTypeAdapter(Resources.class, new ListResourcesEnumAdapter())
            .registerTypeAdapter(Objective.class, new ObjectiveAdapter())
            .registerTypeAdapter(ResourceScore.class, new ObjectiveAdapter())
            .registerTypeAdapter(CoverCornerScore.class, new ObjectiveAdapter())
            .registerTypeAdapter(Count.class, new ObjectiveAdapter())
            .registerTypeAdapter(LShape.class, new ObjectiveAdapter())
            .registerTypeAdapter(LShapeReverse.class, new ObjectiveAdapter())
            .registerTypeAdapter(Seven.class, new ObjectiveAdapter())
            .registerTypeAdapter(SevenReverse.class, new ObjectiveAdapter())
            .registerTypeAdapter(StairDown.class, new ObjectiveAdapter())
            .registerTypeAdapter(StairUp.class, new ObjectiveAdapter())
            .registerTypeAdapter(ServerQueueObject.class, new ServerQueueObjAdapter())
            .registerTypeAdapter(DrawResObj.class, new ServerQueueObjAdapter())
            .registerTypeAdapter(new TypeToken<Map<Resources, Integer>>() {
            }.getType(), new MapRequirementsAdapter())
            .registerTypeAdapter(new TypeToken<LinkedHashMap<Point, PlayableCard>>() {
            }.getType(), new PlayAreaAdapter())
            .serializeNulls()
            .create();

    public static void serializePrivateFields(Object obj, JsonObject jsonObject,
            JsonSerializationContext jsonSerializationContext) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            jsonObject.addProperty("type", obj.getClass().getName());
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value != null) {
                        jsonObject.add(field.getName(), jsonSerializationContext.serialize(value));
                    } else {
                        jsonObject.add(field.getName(), null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
