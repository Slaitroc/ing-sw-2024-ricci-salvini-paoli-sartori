package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.serializePrivateFields;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

//public class ServerQueueObjAdapter implements JsonSerializer<ServerQueueObject>, JsonDeserializer<ServerQueueObject> {
//    @Override
//    public ServerQueueObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//            throws JsonParseException {
//        JsonObject jsonObject = json.getAsJsonObject();
//        ServerQueueObject serverQueueObject = null;
//
//        try {
//            JsonElement className = jsonObject.get("type");
//            Class<? extends ServerQueueObject> clazz = null;
//            if (className != null) {
//                clazz = (Class<? extends ServerQueueObject>) Class.forName(className.getAsString());
//
//                serverQueueObject = createInstance(clazz, jsonObject, context);
//            }
//        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException
//                | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        return serverQueueObject;
//    }
//
//    @Override
//    public JsonElement serialize(ServerQueueObject src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject jsonObject = new JsonObject();
//        serializePrivateFields(src, jsonObject, context);
//        return jsonObject;
//    }
//
//    private <T extends ServerQueueObject> T createInstance(Class<T> clazz, JsonObject jsonObject,
//            JsonDeserializationContext jsonDeserializationContext)
//            throws InvocationTargetException, InstantiationException, IllegalAccessException {
//
//        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
//            Class<?>[] parameterTypes = constructor.getParameterTypes();
//            Object[] parameters = new Object[parameterTypes.length];
//            boolean foundMatch = true;
//
//            for (int i = 0; i < parameterTypes.length; i++) {
//                String parameterName = parameterTypes[i].getSimpleName().toLowerCase();
//
//                JsonElement jsonElement = null;
//                jsonElement = jsonObject.get(parameterName);
//                parameters[i] = jsonDeserializationContext.deserialize(jsonElement, parameterTypes[i]);
//
//                if (jsonElement == null) {
//                    foundMatch = false;
//                    break;
//                }
//            }
//            if (foundMatch) {
//                constructor.setAccessible(true);
//                return (T) constructor.newInstance(parameters);
//            }
//        }
//        throw new RuntimeException("Not found a match for the constructor");
//    }
//}
