package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.UI;

import java.awt.*;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowPlayAreaObj extends ClientQueueObject {
    private final String username;
    private final String playArea;
    private final String achievedResources;

    public ShowPlayAreaObj(String username, String playArea, String achievedResources) {
        this.username = username;
        this.playArea = playArea;
        this.achievedResources = achievedResources;
    }

    @Override
    public void execute(UI ui) {
        ui.show_playArea(
                username,
                gsonTranslater.fromJson(playArea, new TypeToken<Map<Point, PlayableCard>>(){}.getType()),
                achievedResources
        );
    }
}
