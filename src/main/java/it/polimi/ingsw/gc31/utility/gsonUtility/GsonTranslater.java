package it.polimi.ingsw.gc31.utility.gsonUtility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.awt.*;
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
            .registerTypeAdapter(new TypeToken<Map<Resources, Integer>>(){}.getType(), new MapRequirementsAdapter())
            .registerTypeAdapter(new TypeToken<Map<Point, PlayableCard>>(){}.getType(), new PlayAreaAdapter())
            .serializeNulls()
            .create();
}
