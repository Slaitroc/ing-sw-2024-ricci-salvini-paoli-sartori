package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.*;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.serializePrivateFields;

public class ObjectiveAdapter implements JsonDeserializer<Objective>, JsonSerializer<Objective> {
    @Override
    public Objective deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Objective ob = null;

        JsonElement jsonObjectiveType = jsonObject.get("typeObjective");
        if (jsonObjectiveType == null) {
            try {
                JsonElement className = jsonObject.get("type");
                Class<? extends Objective> clazz = null;
                if (className != null) {
                    clazz = (Class<? extends Objective>) Class.forName(className.getAsString());
                    ob = createInstance(clazz, jsonObject, jsonDeserializationContext);
                }

            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            String typeOb = jsonObjectiveType.getAsString();
            ob = switch (typeOb) {
                case "COUNT" -> new Count(
                        jsonDeserializationContext.deserialize(jsonObject.get("resources"), Resources.class));
                case "COVERCORNERSCORE" -> new CoverCornerScore();
                case "LSHAPE" -> new LShape();
                case "LSHAPEREVERSE" -> new LShapeReverse();
                case "RESOURCESCORE" -> new ResourceScore(
                        Resources.valueOf(jsonObject.get("resources").getAsString()));
                case "SEVEN" -> new Seven();
                case "SEVENREVERSE" -> new SevenReverse();
                case "STAIRDOWN" -> new StairDown(
                        CardColor.valueOf(jsonObject.get("color1").getAsString()));
                case "STAIRUP" -> new StairUp(
                        CardColor.valueOf(jsonObject.get("color1").getAsString()));
                default -> ob;
            };
        }

        return ob;
    }

    @Override
    public JsonElement serialize(Objective src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("type", src.getClass().getName());
        serializePrivateFields(src, jsonObject, context);

        if (jsonObject.isEmpty()) {
            return null;
        } else {
            return jsonObject;
        }
    }

    private <T extends Objective> T createInstance(Class<T> clazz, JsonObject jsonObject,
            JsonDeserializationContext jsonDeserializationContext)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        // Check the available constructors and try to find one that matches the data in
        // the JSON
        for (Constructor<?> constructor : clazz.getConstructors()) {
            // get the constructor parameters
            Class<?>[] parameterType = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterType.length];
            boolean foundMatch = true;

            for (int i = 0; i < parameterType.length; i++) {
                String parameterName = parameterType[i].getSimpleName().toLowerCase();

                // the name of the parameter of resource differs from its type
                JsonElement jsonElement = null;
                if (parameterName.equals("resources")) {
                    parameters[i] = Resources.valueOf(jsonObject.get("resource").getAsString());
                } else if (parameterName.equals("list")) {
                    parameters[i] = jsonDeserializationContext.deserialize(jsonObject.get("resources"),
                            Resources.class);
                } else if (parameterName.equals("cardcolor")) {
                    String cardColor = jsonObject.get("cardColor").getAsString();
                    parameters[i] = CardColor.valueOf(cardColor);
                } else {
                    if (jsonElement == null) {
                        foundMatch = false;
                        break;
                    }
                    parameters[i] = jsonDeserializationContext.deserialize(jsonElement, parameterType[i]); // FIX
                                                                                                           // @salvoc02
                }
            }

            if (foundMatch) {
                constructor.setAccessible(true);
                return (T) constructor.newInstance(parameters);
            }
        }
        throw new RuntimeException("Not found a match for the constructor");
    }
}